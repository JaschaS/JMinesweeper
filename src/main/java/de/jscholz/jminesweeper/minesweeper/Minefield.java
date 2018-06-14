package de.jscholz.jminesweeper.minesweeper;

import java.util.*;

/**
 * <p>The implementation of the IMinefield. It will represent the game board of this game. The board holds all cells and
 * is responsible for the game handling.</p>
 */
class Minefield implements IMinefield {

    /**
     * This represents the minefield in the game. All valid positions and cells will be store in this map.
     */
    private final HashMap<ICellPosition, Cell> field;
    /**
     * This set will contain all cells which where updated in the last single click, double click or secondary click.
     */
    private final HashSet<ICell> updatedCells;
    /**
     * State return values for the single click.
     */
    private final HashMap<CellState, ClickReturnStates> singleClickReturnStates;
    /**
     * State return values for the secondary click.
     */
    private final HashMap<CellState, ClickReturnStates> secondaryClickReturnStates;
    /**
     * The total amount of mines within the minefield.
     */
    private final int totalAmountOfMines;
    /**
     * The amount of rows inside the minefield.
     */
    private final int rows;
    /**
     * The amount of columns inside the minefield.
     */
    private final int columns;
    /**
     * The amount of free cells inside the minefield.
     */
    private int freeCellsLeft;
    /**
     * The amount of flags inside the minefield.
     */
    private int placedFlags;
    /**
     * The flag if the game is already over.
     */
    private boolean isGameOver;

    {
        this.placedFlags = 0;
        this.isGameOver = false;
        this.field = new HashMap<>();
        this.updatedCells = new HashSet<>();

        this.singleClickReturnStates = new HashMap<>();
        this.singleClickReturnStates.put(CellState.OPEN, (final Cell cell) -> OpenReturn.IS_ALREADY_OPEN);
        this.singleClickReturnStates.put(CellState.FLAGGED, (final Cell cell) -> OpenReturn.WAS_FLAGGED);
        this.singleClickReturnStates.put(CellState.UNDISCOVERED, (final Cell cell) -> {

            //Get the current cell content. We expect that this content is not null.
            final CellContent content = cell.getContent();
            assert content != null : "Content of Cell " + cell + " is null!";

            // The cell contained a mine which ends the game now.
            if(content == CellContent.MINE) {
                setGameOver();
                return OpenReturn.WAS_MINE;
            }

            cell.open(this.updatedCells);

            --this.freeCellsLeft;

            if(this.freeCellsLeft > 0) {
                return OpenReturn.OPEN;
            }

            setGameOver();
            return OpenReturn.GAME_CLEARED;
        });

        this.secondaryClickReturnStates = new HashMap<>();
        this.secondaryClickReturnStates.put(CellState.OPEN, (final Cell cell) -> OpenReturn.IS_ALREADY_OPEN);
        this.secondaryClickReturnStates.put(CellState.FLAGGED, (final Cell cell) -> {
            flagCell(cell, CellState.FLAGGED);

            return OpenReturn.REMOVE_FLAG;
        });
        this.secondaryClickReturnStates.put(CellState.UNDISCOVERED, (final Cell cell) -> {
            flagCell(cell, CellState.UNDISCOVERED);

            return OpenReturn.NOW_FLAGGED;
        });
    }

    /**
     * Custom-Ctor creates a minefield with the given difficult setting.
     * @param setting The difficult setting for the minefield.
     */
    public Minefield(final Difficulty setting) {
        this(setting.getRows(), setting.getColumns(), setting.getMinesPercent());
    }

    /**
     * Custom-Ctor creates a minefield with the given parameter.
     * @param rows The rows of the field.
     * @param columns The columns of the field.
     * @param minesPercent The percent of mines inside the field.
     */
    public Minefield(final int rows, final int columns, final int minesPercent) {

        /*
         * The game aspect that the given values are valid because only the factory pattern can instanciate a minefield.
         */
        assert rows >= 8 && rows <= 30 : "the given rows was not within bounds. valid [8 < " + rows + " < 30]";
        assert columns >= 8 && columns <= 24 : "the given columns was not within bounds. valid [8 < " + rows + " < 24]";
        assert minesPercent >= 8 && minesPercent <= 93 : "the given minesPercent was not within bounds. valid [8 < " + rows + " < 93]";

        this.rows = rows;
        this.columns = columns;
        this.totalAmountOfMines = (rows*columns) * minesPercent / 100;

        // Calculate the amount of free cells.
        this.freeCellsLeft = (this.rows * this.columns) - this.totalAmountOfMines;

        createEmptyMinefield(rows, columns);
        addNeighboursToCells();
        placeMines();
    }

    @Override
    public Map<ICellPosition, ICell> getFieldForVisualization() {
        final TreeMap<ICellPosition, ICell> copyField = new TreeMap<>();
        /*
         * Create Map with same CellPositions, but with different cell for visualisation.
         * Note: That the cell will be replaced later with the cells inside this.field.
         */
        for(final Map.Entry<ICellPosition, Cell> entry : field.entrySet()) {

            final ICellPosition p = entry.getKey();

            final ICell newCell = new Cell(p);
            copyField.put(p, newCell);
        }

        return copyField;
    }

    /**
     * Returns a unmodifiable view to the map this.field.
     *
     * @return A copy of the minefield.
     */
    public Map<ICellPosition, Cell> getOriginalField() {
        return Collections.unmodifiableMap(field);
    }

    @Override
    public Set<ICell> getUpdateCells() {
        return this.updatedCells;
    }

    /**
     * Returns if the given position is valid.
     * @param position A given minefield position
     * @return True if position is valid. False, otherwise.
     */
    public boolean validatePosition(final ICellPosition position) {
        if(position == null) return false;

        //If the position is not inside the map, it isn't valid.
        return this.field.containsKey(position);
    }

    @Override
    public boolean gameOver() {
        return this.isGameOver;
    }

    @Override
    public OpenReturn secondaryClick(final int x, final int y) {
        return secondaryClick(new CellPosition(x, y));
    }

    @Override
    public OpenReturn secondaryClick(final ICellPosition position) {
        /*
         * There are three different states:
         * - OPEN, in this state only an performAction return value of IS_ALREADY_OPEN will be returned.
         * - FLAGGED, remove the flag by calling flagCell and return the specific return value of the method flag cell.
         * - UNDISCOVERED, set the flag by calling flagCell and return the specific return value of the method flag cell.
         */
        return modifyCell(position, secondaryClickReturnStates);
    }

    @Override
    public OpenReturn singleClick(int x, int y) {
        return singleClick(new CellPosition(x, y));
    }

    @Override
    public OpenReturn singleClick(final ICellPosition position) {
        /*
         * There are three different states:
         * - OPEN, in this state only an performAction return value of IS_ALREADY_OPEN will be returned.
         * - FLAGGED, in this state only an performAction return value of WAS_FLAGGED will be returned.
         * - UNDISCOVERED, in this state another check will be performed based specific conditions:
         *   * Content is MINE, the cell contained a mine. Therefor set the game as game over and return WAS_MINE.
         *   * Otherwise perform a cell.performAction on this cell. Then check if the are still undiscovered cells left in the game.
         *     Return OPEN if there still undiscovered cells left. GAME_CLEARED, otherwise.
         */
        return modifyCell(position, singleClickReturnStates);
    }

    /**
     * <p>Tries to modify a cell with the given position.</p>
     * <p>The requirement to modify the cell are:</p>
     * <ul>
     *     <li>The game is not over,</li>
     *     <li>The given position is valid,</li>
     *     <li>The state map is not null and contains entries for all states.</li>
     * </ul>
     * <p>
     *     Based on the state of the cell, the method performs an action and returns specific performAction return value.
     *     Please look at the methods single click, double click and secondaryClick what actions are performed.
     * </p>
     * @param position The position of the cell who gets modified.
     * @param returnStates The specific return states used to modify the cell.
     * @return The specific performAction return values based on the state map.
     */
    private OpenReturn modifyCell(final ICellPosition position, final HashMap<CellState, ClickReturnStates> returnStates) {

        assert returnStates != null : "Given state map is null!";
        assert returnStates.size() > 0 : "There are no entries inside the state map!";

        if(gameOver()) return OpenReturn.GAME_IS_ALREADY_OVER;

        // Check if the given position is valid.
        if(!validatePosition(position)) return OpenReturn.NOT_VALID;

        //Clear the updatedCells Set because it contains all cells which where opened in the last step.
        this.updatedCells.clear();

        //Get the cell corresponding to the position. We expect that this position exists.
        final Cell cell = this.field.get(position);
        assert cell != null : "Cell " + cell + " should not be null!";

        //Get the current cell state. We expect that this state is not null.
        final CellState state = cell.getCellState();
        assert state != null : "State of Cell " + cell + " is null!";

        // Return the performAction return value for the cell state. We expect that the state is inside the given state map.
        final ClickReturnStates openCell = returnStates.get(state);
        assert openCell != null : "The given performAction cell was null, which means the state was not inside the state map.";

        return openCell.performAction(cell);
    }

    @Override
    public int getTotalMines() {
        return this.totalAmountOfMines;
    }

    @Override
    public int getAmountOfFlags() {
        return this.placedFlags;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getColumns() {
        return this.columns;
    }

    /**
     * Set the game over and add all cells to the performAction cell list.
     */
    private void setGameOver() {
        this.isGameOver = true;
        this.updatedCells.clear();

        //Add all cells to the performAction list.
        //Just adding is not enough. Also set the state of the cells to open.
        for(final Cell cell : this.field.values()) {
            cell.setState(CellState.OPEN);
            this.updatedCells.add(cell);
        }
    }

    /**
     * Creates a minefield where all cells have the empty cell character.
     * @param rows The amount of rows.
     * @param cols The amount of cols.
     */
    private void createEmptyMinefield(final int rows, final int cols) {

        for (int y = 0; y < cols; ++y) {
            for(int x=0; x < rows; ++x) {
                final CellPosition position = new CellPosition(x, y);
                final Cell c = new Cell(position);

                this.field.put(position, c);
            }
        }
    }

    /**
     * Adds the neighbours to the all cells inside the minefield.
     */
    private void addNeighboursToCells() {

        // Iterate over all position in the minefield and add the neighbours to the cells.
        for(final Map.Entry<ICellPosition, Cell> entry : this.field.entrySet()) {
            /*
             * Here we are calling getMooreNeighbourhood. The moore neighbourhood would normally return 8 neighbours because
             * that is the definition of a moore neighbourhood. But around the border of the minefield the cells don't have
             * a moore neighbourhood. In those cases the method getMooreNeighbourhood don't return the full moore neighbourhood
             * instead only a small part of the neighbourhood will be returned.
             */
            //The key is the cell position.
            final List<Cell> neighbours = getMooreNeighbourhood(entry.getKey());
            assert neighbours != null : "The list of neighbours is null!";
            // The size should be always greater than 0 because all cells inside the minefield have neighbours by definition.
            assert neighbours.size() > 0 : "The amount of neighbour should be greater than 0!";

            // Get the cell to the position and add the neighbours to the cell.
            final Cell c = entry.getValue();
            c.addNeighbours(neighbours);
        }

    }

    /**
     * Returns all cells of the moore-neighbourhood for the given position.
     * @param position the position you want to get the moore-neighbourhood from.
     * @return all cells in the moore-neighbourhood.
     */
    public List<Cell> getMooreNeighbourhood(final ICellPosition position) {

        // We aspect that the given position is valid.
        assert validatePosition(position) : "The given position is not valid!";

        final int pX = position.getX();
        final int pY = position.getY();
        final ArrayList<Cell> neighbours = new ArrayList<>();

        /*
         * We calculate the moore-neighbourhood, which is:
         * tl t tr
         * l  p  r
         * bl b br
         *
         * legend:
         * p = position
         * t = top
         * l = left
         * r = right
         * b = below
         */
        for(int y=-1; y < 2; ++y) {

            /*
             * when y is 0 we only want to check the left and the right position.
             * Therefore we need to change the step of the inner-loop.
             */
            final int step = y != 0 ? 1 : 2;

            for(int x=-1; x < 2; x += step) {

                /*
                 * Calculate the neigbour cell position and check if this position is valid.
                 * Around the borders the cell may have less neighbours than the moore neighbourhood.
                 */
                final CellPosition moore = new CellPosition( pX + x, pY + y);
                if(validatePosition(moore)) {

                    // Get the neighbour and add this to the list of neighbours.
                    final Cell c = this.field.get(moore);
                    neighbours.add(c);
                }
            }

        }

        return neighbours;
    }

    /**
     * Places the correct amount of mines inside the minefield.
     */
    private void placeMines() {
        /*
         * Get all keys position of the minefield. Then shuffle those positions.
         *
         * After shuffling take n positions from all position beginning from 0.
         * When setting the cells to mines. Update the neighbours of the cell to increase the content.
         */
        final ArrayList<ICellPosition> positions = new ArrayList<>(this.field.keySet());
        Collections.shuffle(positions);

        for(int i=0; i < this.totalAmountOfMines; ++i) {
            final ICellPosition position = positions.get(i);

            // Get the cell to the position and mark the cell as mine.
            final Cell cell = this.field.get(position);
            cell.markAsMine();
        }
    }

    /**
     * Mark the given cell as flag or remove the flag and set the cell as undiscovered.
     * @param cell The cell which should be flagged or set as undiscovered.
     * @param state The state the cell should get.
     */
    private void flagCell(final Cell cell, final CellState state) {

        switch (state) {
            case FLAGGED:
                /*
                 * If the state is flag set the state to undiscovered and decrease the amount of placed flags.
                 */
                cell.setState(CellState.UNDISCOVERED);
                --this.placedFlags;
                break;
            case UNDISCOVERED:
                /*
                 * Flag the cell and increase the amount of placed flags.
                 */
                cell.setState(CellState.FLAGGED);
                ++this.placedFlags;
                break;
            default:
                // This method should only be called on state flagged or undiscovered.
                // We aspect that this will never happen.
                assert state == CellState.OPEN : "The cell has the state OPEN!";
                break;
        }

        // Add the cell to the updated cell list.
        this.updatedCells.add(cell);
    }

    /**
     * Used by the click return states maps to perform action for the inserted states.
     */
    private interface ClickReturnStates {
        /**
         * Performs an action based on the state and the given cell.
         * @param cell The cell to perform an action on.
         * @return The open return value for the state.
         */
        OpenReturn performAction(final Cell cell);
    }

}
