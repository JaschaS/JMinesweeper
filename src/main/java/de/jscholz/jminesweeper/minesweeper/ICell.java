package de.jscholz.jminesweeper.minesweeper;

/**
 * <p>The interface <i>ICell</i> represents the cells inside the minefield.</p>
 */
public interface ICell {

    /**
     * Returns the position of the cell inside the minefield.
     * @return position of the cell.
     */
    ICellPosition getPosition();

    /**
     * Returns the content of the cell if the state is OPEN.
     * Otherwise the return content will be UNKNOWN.
     *
     * @return
     * <ul>
     *     <li>UNKNOWN, if the state of this cell is UNDISCOVERED or FLAGGED.</li>
     *     <li>content of the cell, if the state of this cell is OPEN.</li>
     */
    CellContent getCellContent();

    /**
     * Returns the current state of the cell.
     * @return state of the cell.
     */
    CellState getCellState();

}
