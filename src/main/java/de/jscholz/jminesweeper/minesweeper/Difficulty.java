package de.jscholz.jminesweeper.minesweeper;

/**
 * The difficulty of the jminesweeper game which are possible.
 */
enum Difficulty {
    /**
     * Difficult setting for an easy game.
     */
    EASY(8,8,16),
    /**
     * Difficult setting for a normal game.
     */
    EXPERIENCED(16,16,16),
    /**
     * Difficult setting for a hard game.
     */
    EXPERT(30,16,21);

    private final int rows;
    private final int columns;
    private final int minesPercent;

    /**
     * Creates a difficult setting which can be use to create a minefield.
     * @param rows the amount of rows in the minefield.
     * @param columns the amount of columns in the minefield.
     * @param minesPercent the percentage of mines inside the minefield.
     */
    Difficulty(final int rows, final int columns, final int minesPercent) {
        this.rows = rows;
        this.columns = columns;
        this.minesPercent = minesPercent;
    }

    /**
     * Returns the column size of the minefield for this difficult setting.
     * @return the column size.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Get the mines inside the minefield in percent for this difficult setting.
     * @return the percentage of mines inside the minefield.
     */
    public int getMinesPercent() {
        return minesPercent;
    }

    /**
     * Returns the row size of the minefield for this difficult setting.
     * @return the row size.
     */
    public int getRows() {
        return rows;
    }
}
