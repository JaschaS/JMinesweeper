package de.jscholz.jminesweeper.minesweeper;

/**
 * All predefined difficulty settings for the minesweeper game.
 */
public enum Difficulty {

    /**
     * Difficult setting for an easy game.
     */
    EASY(false, 8,8,16),
    /**
     * Difficult setting for a normal game.
     */
    EXPERIENCED(false,16,16,16),
    /**
     * Difficult setting for a hard game.
     */
    EXPERT(false,30,16,21),
    /**
     * Difficult setting for a custom game.
     */
    CUSTOM(true, 8, 8, 16);

    /**
     * The minimum amount of rows allowed for a custom game.
     */
    public static final int MIN_ROWS = 8;

    /**
     * The minimum amount of columns allowed for a custom game.
     */
    public static final int MIN_COLUMNS = 8;

    /**
     * The minimum percentage of mines allowed for a custom game.
     */
    public static final int MIN_MINES_PERCENT = 16;

    /**
     * The maximum amount of rows allowed for a custom game.
     */
    public static final int MAX_ROWS = 30;

    /**
     * The maximum amount of columns allowed for a custom game.
     */
    public static final int MAX_COLUMNS = 24;

    /**
     * The maximum percentage of mines allowed for a custom game.
     */
    public static final int MAX_MINES_PERCENT = 93;

    /**
     * Shows if the enum-value is editable. Note: Only Custom is editable.
     */
    private final boolean editable;
    /**
     * The rows of the game.
     */
    private int rows;
    /**
     * The columns of the game.
     */
    private int columns;
    /**
     * The amount of mines in the game in percentage.
     */
    private int minesPercent;

    /**
     * Creates a difficult setting which can be use to create a minefield.
     * @param editable Marks if the values can change.
     * @param rows The amount of rows in the minefield.
     * @param columns The amount of columns in the minefield.
     * @param minesPercent The percentage of mines inside the minefield.
     */
    Difficulty(final boolean editable, final int rows, final int columns, final int minesPercent) {
        this.editable = editable;
        this.rows = rows;
        this.columns = columns;
        this.minesPercent = minesPercent;
    }

    /**
     * Returns the column size of the minefield for this difficult setting.
     * @return The column size.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Get the mines inside the minefield in percent for this difficult setting.
     * @return The percentage of mines inside the minefield.
     */
    public int getMinesPercent() {
        return minesPercent;
    }

    /**
     * Returns the row size of the minefield for this difficult setting.
     * @return The row size.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the rows for the difficult setting. Only editable when the difficult level is custom!
     * @param rows
     */
    public void setRows(final int rows) {
        if(this.editable)
            this.rows = ensureRange(rows, MIN_ROWS, MAX_ROWS);
    }

    /**
     * Sets the columns for the difficult setting. Only editable when the difficult level is custom!
     * @param columns
     */
    public void setColumns(final int columns) {
        if(this.editable)
            this.columns = ensureRange(columns, MIN_COLUMNS, MAX_COLUMNS);
    }

    /**
     * Sets the amount of mines for the difficult setting. Only editable when the difficult level is custom!
     * @param minesPercent
     */
    public void setMinesPercent(final int minesPercent) {
        if(this.editable)
            this.minesPercent = ensureRange(minesPercent, MIN_MINES_PERCENT, MAX_MINES_PERCENT);
    }

    /**
     * Defines if values of the enum can be changed with setters.
     * @return true, if values can be changed. Otherwise false.
     */
    public boolean isEditable() {
        return this.editable;
    }


    /**
     * Ensure that the value is within the given range.
     * @param value The value which will be checked.
     * @param min The minimum the value can take.
     * @param max The maximum the value can take.
     * @return Value, if within range. Min, if the value is below min. Max, if the value is above max.
     */
    private static int ensureRange(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

}
