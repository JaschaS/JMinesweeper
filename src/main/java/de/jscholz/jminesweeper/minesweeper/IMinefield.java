package de.jscholz.jminesweeper.minesweeper;

import java.util.Map;
import java.util.Set;

/**
 * <p>The interface <i>IMinefield</i> represents the game board of the game.</p>
 */
public interface IMinefield {

    //TODO Create a sorted visualization field.
    //TODO Double Click method
    //TODO Restart game method

    /**
     * Flags the cell at the given position or removes the flag from the cell.
     * @param position The position of the cell.
     * @return <ul>
     *     <li>GAME_IS_ALREADY_OVER, if the game is already over.</li>
     *     <li>NOT_VALID, if the position was not valid.</li>
     *     <li>FAILED, if something went wrong.</li>
     *     <li>IS_ALREADY_OPEN, if the cell was already opened.</li>
     *     <li>NOW_FLAGGED, if the cell is now flagged.</li>
     *     <li>REMOVE_FLAG, if the flag was removed from the cell.</li>
     * </ul>
     */
    OpenReturn secondaryClick(final ICellPosition position);

    /**
     * Flags the cell at the given position or removes the flag from the cell.
     * @param x The x value of the position.
     * @param y The y value of the position.
     * @return <ul>
     *     <li>GAME_IS_ALREADY_OVER, if the game is already over.</li>
     *     <li>NOT_VALID, if the position was not valid.</li>
     *     <li>FAILED, if something went wrong.</li>
     *     <li>IS_ALREADY_OPEN, if the cell was already opened.</li>
     *     <li>NOW_FLAGGED, if the cell is now flagged.</li>
     *     <li>REMOVE_FLAG, if the flag was removed from the cell.</li>
     * </ul>
     */
    OpenReturn secondaryClick(final int x, final int y);

    /**
     * Returns a map with all the existing cell positions and empty cells.
     * Note: The map is sorted by the cell positions, where the first entry is x:0,y:0.
     * @return A minefield for the visualization.
     */
    Map<ICellPosition, ICell> getFieldForVisualization();

    /**
     * This method returns a list of cell positions, which where opened or flag in the last call.
     * @return A list of cells position or an empty list, if no cells where opened.
     */
    Set<ICell> getUpdateCells();

    /**
     * Validates the given minefield position.
     * @param position a given minefield position
     * @return True, if the position is inside the minefield. False, otherwise.
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
     * @param x The x value of the position.
     * @param y The y value of the position.
     * @return <ul>
     *     <li>GAME_IS_ALREADY_OVER, if the game is already over.</li>
     *     <li>NOT_VALID, if the position was not valid.</li>
     *     <li>FAILED, if something went wrong.</li>
     *     <li>IS_ALREADY_OPEN, if the cell was already opened.</li>
     *     <li>WAS_FLAGGED, if the cell is flagged.</li>
     *     <li>GAME_CLEARED, if the last cell was successfully opened and the game is now cleared.</li>
     *     <li>OPEN, if the cell was successfully opened.</li>
     * </ul>
     */
    OpenReturn singleClick(final int x, final int y);

    /**
     * Opens a cell in the field at the given position.
     *
     * @param position The position inside the field.
     * @return <ul>
     *     <li>GAME_IS_ALREADY_OVER, if the game is already over.</li>
     *     <li>NOT_VALID, if the position was not valid.</li>
     *     <li>FAILED, if something went wrong.</li>
     *     <li>IS_ALREADY_OPEN, if the cell was already opened.</li>
     *     <li>WAS_FLAGGED, if the cell is flagged.</li>
     *     <li>GAME_CLEARED, if the last cell was successfully opened and the game is now cleared.</li>
     *     <li>OPEN, if the cell was successfully opened.</li>
     * </ul>
     */
    OpenReturn singleClick(final ICellPosition position);

    /**
     * Returns the total amount of mines inside the minefield.
     * @return The amount of mines.
     */
    int getTotalMines();

    /**
     * Returns the flags which are already placed on the minefield.
     * @return The amount of flags.
     */
    int getAmountOfFlags();

    /**
     * Returns the size of the rows inside the minefield.
     * @return The amount of rows.
     */
    int getRows();

    /**
     * Returns the size of the columns inside the minefield.
     * @return The amount of columns.
     */
    int getColumns();

    /**
     * <p>
     * This enum represents the performAction return values. It can be used by the click methods to inform the user about the
     * status of clicking.
     * </p>
     */
    enum OpenReturn {
        /**
         * Informs that the click failed and the action could not be performed.
         */
        FAILED,
        /**
         * Informs the user that the given position was not valid and the action could not be performed.
         */
        NOT_VALID,
        /**
         * Informs the user that the cell was successfully opened and the action could not be performed.
         */
        OPEN,
        /**
         * Informs the user that the cell has already the state performAction and the action could not be performed.
         */
        IS_ALREADY_OPEN,
        /**
         * Informs the user that the cell is flagged and the action could not be performed.
         */
        WAS_FLAGGED,
        /**
         * Informs the user that the game is already over and the action could not be performed.
         */
        GAME_IS_ALREADY_OVER,
        /**
         * Informs the user that the cell is now flagged.
         */
        NOW_FLAGGED,
        /**
         * Informs the user that the cell, which was opened, contained a mine.
         */
        WAS_MINE,
        /**
         * Informs the user that the flag was removed from the cell.
         */
        REMOVE_FLAG,
        /**
         * Informs the user that the game was cleared and is now over.
         */
        GAME_CLEARED
    }
}
