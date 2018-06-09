package de.jscholz.jminesweeper.minesweeper;

/**
 * <p>The enum which represents the cell state.</p>
 * <p>There are only three states a cell can have:</p>
 * <ul>
 * <li>UNDISCOVERED, when a cell content is still unknown to the user.</li>
 * <li>OPEN, when a cell was opened by the user and the cell content is now known ot the user.</li>
 * <li>FLAGGED, when the user flagged this cell.</li>
 * </ul>
 */
public enum CellState {
    /**
     * Shows that this cell was opened by the user.
     */
    OPEN,
    /**
     * Shows that this cell can be opened by the user.
     */
    UNDISCOVERED,
    /**
     * Shows that this cell was flagged by the user.
     */
    FLAGGED
}
