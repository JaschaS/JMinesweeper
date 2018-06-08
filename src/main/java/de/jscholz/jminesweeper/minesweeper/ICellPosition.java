package de.jscholz.jminesweeper.minesweeper;

/**
 * A interface of a cell position.
 */
public interface ICellPosition {
    /**
     * Returns the x position of the cell.
     * @return x component of the cell.
     */
    int getX();

    /**
     * Returns the y position of the cell.
     * @return y component of the cell.
     */
    int getY();

    boolean compare(final int x, final int y);
}
