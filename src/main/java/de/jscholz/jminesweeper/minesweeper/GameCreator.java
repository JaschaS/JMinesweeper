package de.jscholz.jminesweeper.minesweeper;

/**
 * A simple Static-Factory-Pattern which creates a minesweeper game.
 */
public class GameCreator {

    public static final int MIN_ROWS = 8;
    public static final int MIN_COLUMNS = 8;
    public static final int MIN_MINES_PERCENT = 16;
    public static final int MAX_ROWS = 30;
    public static final int MAX_COLUMNS = 24;
    public static final int MAX_MINES_PERCENT = 93;

    /**
     * Creates a beginner game with the difficult setting EASY.
     * @return a beginner minesweeper game.
     */
    public static IMinefield createBeginnerGame() {
        return new Minefield(Difficulty.EASY);
    }

    /**
     * Creates a experienced game with the difficult setting EXPERIENCED.
     * @return a experienced minesweeper game.
     */
    public static IMinefield createExperiencedGame() {
        return new Minefield(Difficulty.EXPERIENCED);
    }

    /**
     * Creates a expert game with the difficult setting EXPERT.
     * @return a expert minesweeper game.
     */
    public static IMinefield createExpertGame() {
        return new Minefield(Difficulty.EXPERT);
    }

    /**
     * Creates a custom game with the given properties. If the properties are not valid, the method will clamp the
     * given values.
     *
     * Min Rows : 8, Max Rows : 30
     * Min Cols : 8, Max Cols : 24
     * Min Mines: 8, Max Mines: 93
     *
     * @return a custom minesweeper game.
     */
    public static IMinefield createCustomGame(int rows, int columns, int minesPercent) {
        rows = ensureRange(rows, MIN_ROWS, MAX_ROWS);
        columns = ensureRange(columns, MIN_COLUMNS, MAX_COLUMNS);
        minesPercent = ensureRange(minesPercent, MIN_MINES_PERCENT, MAX_MINES_PERCENT);

        return new Minefield(rows, columns, minesPercent);
    }

    /**
     * Ensure that the value is within the given range.
     * @param value the value which will be checked.
     * @param min the minimum the value can take.
     * @param max the maximum the value can take.
     * @return value, if within range. Min, if the value is below min. Max, if the value is above max.
     */
    private static int ensureRange(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

}
