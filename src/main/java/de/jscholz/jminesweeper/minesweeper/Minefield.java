package de.jscholz.jminesweeper.minesweeper;

import java.util.*;

/**
 * The implementation of the IMinefield. It will represent the game-board of this game. The board holds all cells and is
 * responsible for the game handling.
 */
class Minefield implements IMinefield {

    private final HashMap<ICellPosition, Cell> field;
    private final HashSet<ICell> updatedCells;
    private final HashMap<ICell.CellState, OpenCell> singleClickReturnStates;
    private final HashMap<ICell.CellState, OpenCell> flagCellReturnStates;
    private final int totalAmountOfMines;
    private final int rows;
    private final int columns;
    private int freeCellsLeft;
    private int placedFlags;
    private boolean isGameOver;

    {
        this.placedFlags = 0;
        this.isGameOver = false;
        this.field = new HashMap<>();

        this.updatedCells = new HashSet<>();
        this.singleClickReturnStates = new HashMap<>();
        this.singleClickReturnStates.put(ICell.CellState.OPEN, (final Cell cell) -> OpenReturn.IS_ALREADY_OPEN);
        this.singleClickReturnStates.put(ICell.CellState.FLAGGED, (final Cell cell) -> OpenReturn.WAS_FLAGGED);
        this.singleClickReturnStates.put(ICell.CellState.UNDISCOVERED, (final Cell cell) -> {

            //Get the current cell content. We expect that this content is not null.
            final ICell.CellContent content = cell.getContent();
            assert content != null : "Content of Cell " + cell + " is null!";

            if(content == ICell.CellContent.MINE) {
                setGameOver();
                return OpenReturn.WAS_MINE;
            }

            cell.open(this.updatedCells);

            --this.freeCellsLeft;

            if(this.freeCellsLeft > 0) {
                return OpenReturn.OPEN;
            }

            setGameOver();
            return OpenReturn.Game_CLEARED;
        });

        this.flagCellReturnStates = new HashMap<>();
        this.flagCellReturnStates.put(ICell.CellState.OPEN, (final Cell cell) -> OpenReturn.IS_ALREADY_OPEN);
        this.flagCellReturnStates.put(ICell.CellState.FLAGGED, (final Cell cell) -> {
            /*cell.setState(ICell.CellState.UNDISCOVERED);
            --this.placedFlags;

            this.updatedCells.add(cell);

            return OpenReturn.REMOVE_FLAG;*/
            return flagCell(cell, ICell.CellState.FLAGGED, OpenReturn.REMOVE_FLAG);
        });
        this.flagCellReturnStates.put(ICell.CellState.UNDISCOVERED, (final Cell cell) -> {
            /*cell.setState(ICell.CellState.FLAGGED);
            ++this.placedFlags;

            this.updatedCells.add(cell);

            return OpenReturn.NOW_FLAGGED;*/
            return flagCell(cell, ICell.CellState.UNDISCOVERED, OpenReturn.NOW_FLAGGED);
        });
    }

    /**
     * Custom-Ctor creates a minefield with the given parameter.
     * @param rows the rows of the field.
     * @param columns the columns of the field.
     * @param minesPercent the percent of mines inside the field.
     */
    public Minefield(final int rows, final int columns, final int minesPercent) {

        assert rows >= 8 && rows <= 30 : "the given rows was not within bounds. valid [8 < " + rows + " < 30]";
        assert columns >= 8 && columns <= 24 : "the given columns was not within bounds. valid [8 < " + rows + " < 24]";
        assert minesPercent >= 8 && minesPercent <= 93 : "the given minesPercent was not within bounds. valid [8 < " + rows + " < 93]";

        this.rows = rows;
        this.columns = columns;
        this.totalAmountOfMines = (rows*columns) * minesPercent / 100;

        this.freeCellsLeft = (this.rows * this.columns) - this.totalAmountOfMines;

        createEmptyMinefield(rows, columns);
        addNeighboursToCells();
        placeMines(rows, columns);
    }

    @Override
    public OpenReturn flagCell(final int x, final int y) {
        return flagCell(new CellPosition(x, y));
    }

    @Override
    public OpenReturn flagCell(final ICellPosition position) {
        return openCell(position, flagCellReturnStates);
    }

    public Map<ICellPosition, ICell> getField() {
        final HashMap<ICellPosition, ICell> copyField = new HashMap<>();

        //Create Map with same CellPositions, but with different cell for visualisation.
        for(final Map.Entry<ICellPosition, Cell> entry : field.entrySet()) {

            final ICellPosition p = entry.getKey();

            final ICell newCell = new Cell(p);
            copyField.put(p, newCell);
        }

        return copyField;
    }

    /**
     * Returns a copy a the current minefield.
     * Note that method returns a unmodifiable map.
     *
     * @return A copy of the minefield.
     */
    public Map<ICellPosition, Cell> getOriginalField() {
        return Collections.unmodifiableMap(field);
    }

    public Set<ICell> getUpdateCells() {
        return this.updatedCells;
    }

    public boolean validatePosition(final ICellPosition position) {
        if(position == null) return false;

        return this.field.containsKey(position);
    }

    public boolean gameOver() {
        return this.isGameOver;
    }

    public OpenReturn open(int x, int y) {
        return open(new CellPosition(x, y));
    }

    public OpenReturn open(final ICellPosition position) {
        return openCell(position, singleClickReturnStates);
    }

    private OpenReturn openCell(final ICellPosition position, final HashMap<ICell.CellState, OpenCell> returnStates) {

        if(gameOver()) return OpenReturn.GAME_IS_ALREADY_OVER;

        // Check if the given position is valid.
        if(!validatePosition(position)) return OpenReturn.NOT_VALID;

        //Clear the updatedCells Set because it contains all cells which where opened in the last step.
        this.updatedCells.clear();

        //Get the cell corresponding to the position. We expect that this position exists.
        final Cell cell = this.field.get(position);
        assert cell != null : "Cell " + cell + " should not be null!";

        //Get the current cell state. We expect that this state is not null.
        final ICell.CellState state = cell.getCellState();
        assert state != null : "State of Cell " + cell + " is null!";

        return returnStates.get(state).open(cell);
    }

    public int getTotalMines() {
        return this.totalAmountOfMines;
    }

    public int getAmountOfFlags() {
        return this.placedFlags;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    /**
     * Set the game over and add all cells to the open cell list.
     */
    private void setGameOver() {
        this.isGameOver = true;
        this.updatedCells.clear();

        //Add all cells to the open list.
        this.updatedCells.addAll(this.field.values());
    }

    /**
     * Creates a minefield where all cells have the empty cell character.
     * @param rows the amount of rows.
     * @param cols the amount of cols.
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

    private void addNeighboursToCells() {

        for(int y=0; y < this.columns; ++y) {

            for(int x=0; x < this.rows; ++x) {

                final CellPosition position = new CellPosition(x, y);
                final List<Cell> neighbours = getMooreNeighbourhood(position);
                final Cell c = this.field.get(position);
                c.addNeighbours(neighbours);
            }

        }

    }

    /**
     * Returns all cells of the moore-neighbourhood for the given position.
     * @param position the position you want to get the moore-neighbourhood from.
     * @return all cells in the moore-neighbourhood.
     */
    public List<Cell> getMooreNeighbourhood(final ICellPosition position) {

        final int pX = position.getX();
        final int pY = position.getY();
        final ArrayList<Cell> neighbours = new ArrayList<>();

        /**
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

            /**
             * when y is 0 we only want to check the left and the right position.
             * Therefore we need to change the step of the inner-loop.
             */
            final int step = y != 0 ? 1 : 2;

            for(int x=-1; x < 2; x += step) {

                final CellPosition moore = new CellPosition( pX + x, pY + y);
                if(validatePosition(moore)) {

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
    private void placeMines(final int rows, final int cols) {
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

            final Cell cell = this.field.get(position);
            cell.markAsMine();
        }
    }

    private OpenReturn flagCell(final Cell cell, final ICell.CellState state, final OpenReturn returnValue) {
        if(state == ICell.CellState.FLAGGED) {
            cell.setState(ICell.CellState.UNDISCOVERED);
            --this.placedFlags;
        }
        else if(state == ICell.CellState.UNDISCOVERED) {
            cell.setState(ICell.CellState.FLAGGED);
            ++this.placedFlags;
        }

        this.updatedCells.add(cell);

        return returnValue;
    }

    private interface OpenCell {
        OpenReturn open(final Cell cell);
    }

}
