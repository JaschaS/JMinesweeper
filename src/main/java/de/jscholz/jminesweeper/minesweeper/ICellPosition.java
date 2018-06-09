package de.jscholz.jminesweeper.minesweeper;

/**
 * <p>
 *     The interface <i>ICellPosition</i> represents the cell positions inside the minefield. It is used by the cells
 *     and shows where a cell is located inside the minefield.
 * </p>
 */
public interface ICellPosition {
    /**
     * Returns the x value of the position.
     * @return The value of x.
     */
    int getX();

    /**
     * Returns the y value of the position.
     * @return The value of y.
     */
    int getY();

    /**
     * Compares the cell position with the given x and y values.
     * @param x The x value will be compared to the x value of the cell position.
     * @param y The y value will be compared to the y value of the cell position.
     * @return True, if the position is equal to the given values. False, otherwise.
     */
    boolean compare(final int x, final int y);
}
