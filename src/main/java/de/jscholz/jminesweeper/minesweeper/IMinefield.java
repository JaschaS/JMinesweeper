package de.jscholz.jminesweeper.minesweeper;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The interface <i>IMinefield</i> represents the game-board of the game.
 *
 * There are different actions for a minefield needs to provide.
 */
public interface IMinefield {

    /**
     * TODO
     * @param position
     */
    OpenReturn flagCell(final ICellPosition position);

    /**
     * TODO
     */
    OpenReturn flagCell(final int x, final int y);

    /**
     * Returns a map with all the excisting cell-positions and empty cells.
     * @return A minefield for the visualization.
     */
    Map<ICellPosition, ICell> getField();

    /**
     * This method returns a list of cell positions, which where opened or flag in the last call.
     * @return A list of cells position or a empty list, if no cells where opened.
     */
    Set<ICell> getUpdateCells();

    /**
     * Validates the given minefield position.
     * @param position a given minefield position
     * @return true, if the position is inside the minefield. False, if not.
     */
    boolean validatePosition(final ICellPosition position);

    /**
     * Returns if the game is over.
     * @return True, if the game is over. False, otherwise.
     */
    boolean gameOver();

    /**
     * Opens a cell in the field at the given position.
     *
     * @param x The x position inside the field.
     * @param y The y position inside the field.
     * @return Returns the specific open return value.
     */
    OpenReturn open(final int x, final int y);

    /**
     * Opens a cell in the field at the given position.
     *
     * @param position the position inside the field.
     * @return Returns the specific open return value.
     */
    OpenReturn open(final ICellPosition position);

    /**
     * Returns the total amount of mines inside the minefield.
     * @return the amount of mines.
     */
    int getTotalMines();

    /**
     * Returns the flags which are already placed on the minefield.
     * @return the amount of flags.
     */
    int getAmountOfFlags();

    /**
     * Returns the size of the rows inside the minefield.
     * @return the amount of rows.
     */
    int getRows();

    /**
     * Returns the size of the columns inside the minefield.
     * @return the amount of columns.
     */
    int getColumns();

    enum OpenReturn {
        FAILED,
        NOT_VALID,
        OPEN,
        IS_ALREADY_OPEN,
        WAS_FLAGGED,
        GAME_IS_ALREADY_OVER,
        NOW_FLAGGED,
        WAS_MINE,
        REMOVE_FLAG,
        Game_CLEARED
    }
}
