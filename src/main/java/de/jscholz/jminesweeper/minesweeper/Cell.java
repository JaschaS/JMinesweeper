package de.jscholz.jminesweeper.minesweeper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The interface which represents the cell inside a minefield.
 * Each cell has different properties:
 * - A cell has one state, which represents if the cell was already discovered, opened or if the cell was flagged.
 * - Each cell contains a list with neighbour cells in the moore neighborhood.
 * - A cell contains exactly one content-type.
 */
class Cell implements ICell {

    private final Set<Cell> neighbours;
    private final ICellPosition position;
    private CellContent content;
    private CellState state;

    /**
     * Default-Ctor creates a cell with position x=0, y=0,
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
        this.position = position;
        this.neighbours = new HashSet<>();
        this.content = CellContent.EMPTY;
        this.state = CellState.UNDISCOVERED;
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
        this.neighbours = new HashSet<>();
        this.content = content;
        this.state = state;
    }

    /**
     * Copy-Ctor copies the attributes from the given ICellPosition.
     * @param cell the cell which should be copied.
     */
    public Cell(final Cell cell) {
        this.position = new CellPosition(cell.getPosition());
        this.neighbours = new HashSet<>();
        this.content = cell.getContent();
        this.state = cell.getCellState();

        neighbours.addAll(cell.getNeighbours());
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

    /**
     * If this cell contains a mines, the neighbours need to be updated about this change.
     * This method should be called when the minefield gets initialized.
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

    public void open(final Set<ICell> openedCells) throws NullPointerException {

        if(openedCells == null) throw new NullPointerException("Given Set is null");

        if(state == CellState.UNDISCOVERED) {
            openedCells.add(this);
            state = CellState.OPEN;

            //Only open when the content is empty.
            if(content == CellContent.EMPTY) {
                for (final Cell c : neighbours) {
                    c.open(openedCells);
                }
            }
        }
    }

    /**
     * Updates the content of this cell.
     * if the cell is empty it will get the content one, if the cell content is one, it will get the two. And so on.
     * if the content is mine, nothing should happen.
     */
    public void increaseNumber() {
        //We increase here the number inside the content. If the cell is a mine or a eight, do nothing.
        if(content != CellContent.MINE && content != CellContent.EIGHT) {

            //getNext points to the next number inside the enum.
            content = content.getNext();
        }
    }

    public ICellPosition getPosition() {
        return this.position;
    }

    /**
     * Adds a neighbour to the cell.
     *
     * @exception NullPointerException throws a null-pointer exception, if the given cell is null.
     * @param cell the new neighbour of the cell.
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

    public boolean addNeighbours(final List<Cell> neighbours) throws NullPointerException {
        if(neighbours == null) throw new NullPointerException("Given cell is null");

        return this.neighbours.addAll(neighbours);
    }

    /**
     * Returns the current cell state of the cell.
     * @return state of the cell.
     */
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

    public CellContent getContent() {
        return content;
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