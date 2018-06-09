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
     * Returns the content of the cell.
     * @return content of the cell.
     */
    CellContent getContent();

    /**
     * Returns the current state of the cell.
     * @return state of the cell.
     */
    CellState getCellState();

}
