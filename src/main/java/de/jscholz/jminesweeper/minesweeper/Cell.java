package de.jscholz.jminesweeper.minesweeper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>The interface which represents the cell inside a minefield.</p>
 * <p>Each cell has different properties:</p>
 * <ul>
 * <li>A cell has one state, which represents if the cell was already discovered, opened or if the cell was flagged.</li>
 * <li>Each cell contains a list of neighbor cells in the Moore neighborhood.</li>
 * <li>A cell contains exactly one content-type.</li>
 * </ul>
 */
class Cell implements ICell {

    /**
     * The list which contains all cells in the Moore neighborhood.
     */
    private final Set<Cell> neighbours;
    /**
     * The cell position.
     */
    private final ICellPosition position;
    /**
     * The content of the cell.
     */
    private CellContent content;
    /**
     * The state of the cell.
     */
    private CellState state;

    private boolean gameOver;

    {
        this.gameOver = false;
        this.neighbours = new HashSet<>();
    }

    /**
     * Default-Ctor creates a cell with no neighbours, position (x=0, y=0), content empty and cell state undiscovered.
     */
    public Cell() {
        this(new CellPosition());
    }

    /**
     * Ctor creates a cell with no neighbours, with content Empty and
     * state undiscovered.
     *
     * @param x the x position of the cell.
     * @param y the y position of the cell
     */
    public Cell(final int x, final int y) {
        this(new CellPosition(x, y));
    }

    /**
     * Ctor creates a cell with no neighbours, with content Empty and
     * state undiscovered.
     *
     * @param position the position of the cell.
     */
    public Cell(final ICellPosition position) {
        this(position, CellContent.EMPTY, CellState.UNDISCOVERED);
    }

    /**
     * Ctor creates a cell with no neighbours, with content Empty and
     * state undiscovered.
     *
     * @param position the position of the cell.
     * @param content the start content of the cell.
     * @param state the start condition.
     */
    public Cell(final ICellPosition position, final CellContent content, final CellState state) {
        this.position = position;
        this.content = content;
        this.state = state;
    }

    /**
     * Copy-Ctor copies the attributes from the given ICellPosition.
     * @param cell the cell which should be copied.
     */
    public Cell(final Cell cell) {
        this.position = new CellPosition(cell.getPosition());
        this.content = cell.getContent();
        this.state = cell.getCellState();

        this.neighbours.addAll(cell.getNeighbours());
    }

    /**
     * <p>Tries to performAction the cell. If the cell is already discovered, nothing will happen. Otherwise, the cell state will
     * be changed to <i>OPEN</i> and the cell will be added to the set <i>openedCells</i>. If the cell content is empty,
     * all cells in the neighborhood will also be opened.
     * </p>
     * <p>If the set <i>openenCells</i> is null, an null pointer exception will be raised.</p>
     * @param openedCells The set the cell should be added to.
     * @throws NullPointerException If the given set is null, an null pointer exception will be raised.
     */
    public void open(final Set<ICell> openedCells) throws NullPointerException {

        // TODO assert
        if(openedCells == null) throw new NullPointerException("Given Set is null");

        if(state == CellState.UNDISCOVERED) {
            state = CellState.OPEN;

            /*
             * Add the cell after the state change not before! This is crucial!
             * Example 1:
             * set.add(this)
             * state = Open
             *
             * set.contains(this) => will return false.
             *
             * Example 2:
             * state = Open
             * set.add(this)
             *
             * set.contains(this) => will return true.
             *
             * Why does 1 not return true, but 2 does? Because the set will calculate the hashcode of the cell when
             * calling add.
             * In 1 the cell will be modified and this will result in a different hashcode. In example 2 the hashcode
             * will be calculated after changing the cell. Therefore the set will return true.
             */
            openedCells.add(this);

            //Only performAction when the content is empty.
            if(content == CellContent.EMPTY) {
                for (final Cell c : neighbours) {
                    c.open(openedCells);
                }
            }
        }
    }

    /**
     * <p>Updates the content of this cell.</p>
     * <ul>
     * <li>if the cell is empty it will get the content one, if the cell content is one, it will get the two. And so on.</li>
     * <li>if the content is mine, nothing should happen.</li>
     * </ul>
     */
    public void increaseNumber() {
        //We increase here the number inside the content. If the cell is a mine or a eight, do nothing.
        if(content != CellContent.MINE && content != CellContent.EIGHT && content != CellContent.UNKNOWN) {

            //getNext points to the next number inside the enum.
            content = content.getNext();
        }
    }

    /**
     * Changes the state of the cell.
     * @param state the new state.
     */
    public void setState(final CellState state) {
        this.state = state;
    }

    /**
     * Changes the content of the cell.
     * @param content the new content.
     */
    public void setContent(final CellContent content) {
        this.content = content;
    }

    public void setGameOver(final boolean value) { this.gameOver = value; }

    /**
     * <p>Tries to mark the cell as mine. If the content is not already a mine, the content will be set and all
     * neighbours will be informed that this cell is now a mine. The neighbours will then increase their number.</p>
     * <p>This method should be called when the minefield gets initialized.</p>
     * @return True, if the cell content is now a mine. False, if the cell was already a mine.
     */
    public boolean markAsMine() {

        if(content != CellContent.MINE) {
            setContent(CellContent.MINE);

            assert neighbours != null : "Neigbours shouldn't be null";

            for (final Cell c : neighbours) {
                c.increaseNumber();
            }

            return true;
        }

        return false;
    }

    @Override
    public ICellPosition getPosition() {
        return this.position;
    }

    /**
     * <p>
     *     Tries to add the given cell to the neighborhood.
     * </p>
     * <p> Fails:</p>
     * <ul>
     *     <li>When the given cell is null.</li>
     *     <li>When the given cell is already in the neighborhood.</li>
     *     <li>When the given cell is equal to this.</li>
     * </ul>
     *
     * @exception NullPointerException A null-pointer exception will be raised, if the given cell is null.
     * @param cell The new neighbour of the cell.
     * @return True, if cell was added. False, otherwise.
     */
    public boolean addNeighbour(final Cell cell) throws NullPointerException {
        if(cell == null) throw new NullPointerException("Given cell is null");
        if(this.neighbours.contains(cell)) {
            System.err.println("Cell " + this + " contains already " + cell);
            return false;
        }
        if(this.equals(cell)) {
            System.err.println("You can't add the same instance to the neighbourhood.");
            return false;
        }

        return this.neighbours.add(cell);
    }

    /**
     * <p>Adds all the given cells to the neighborhood.</p>
     * <p>A null pointer exception will be raised if the list is null.</p>
     * @param neighbours The cells which should be added to the neighborhood.
     * @return True, if the cells where added. False, otherwise.
     * @throws NullPointerException A null pointer exception will be raised when the given list is null.
     */
    public boolean addNeighbours(final List<Cell> neighbours) throws NullPointerException {
        if(neighbours == null) throw new NullPointerException("Given cell is null");

        return this.neighbours.addAll(neighbours);
    }

    @Override
    public CellState getCellState() {
        return state;
    }

    /**
     * Returns all neighbours of the cell.
     * @return neighbours of the cell.
     */
    public Set<Cell> getNeighbours() {
        return neighbours;
    }

    @Override
    public CellContent getCellContent() {

        /*
         * Only return the content of the cell when the cell state is open or the game is over! Otherwise return unknown.
         */
        if(this.state == CellState.OPEN || this.gameOver) {
            return this.content;
        }

        return CellContent.UNKNOWN;
    }

    /**
     * Returns the content of the cell.
     * @return content of the cell.
     */
    CellContent getContent() {
        return this.content;
    }

    @Override
    public int hashCode() {

        final int prime = 37;
        int result = 28;

        result = prime * result + (position == null ? 0 : position.hashCode());
        result = prime * result + content.hashCode();
        result = prime * result + state.hashCode();
        result = prime * result + (neighbours == null ? 0 : neighbours.size());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj == null) return false;
        if ( !(obj instanceof ICell) ) return false;
        if ( this == obj ) return true;

        return hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return "Cell [State: " + state + ", Content: " + content + ", Position: " + position + "]";
    }
}
