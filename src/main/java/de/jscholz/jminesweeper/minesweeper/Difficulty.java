package de.jscholz.jminesweeper.minesweeper;

/**
 * The difficulty of the jminesweeper games which are possible.
 */
public enum Difficulty {
    EASY(8,8,16),
    NORMAL(16,16,16),
    HARD(30,16,21),
    CUSTOM(30,24,93);

    private final int rows;
    private final int columns;
    private final int minesPercent;

    Difficulty(final int rows, final int columns, final int minesPercent) {
        this.rows = rows;
        this.columns = columns;
        this.minesPercent = minesPercent;
    }

    public int getColumns() {
        return columns;
    }

    public int getMinesPercent() {
        return minesPercent;
    }

    public int getRows() {
        return rows;
    }
}
