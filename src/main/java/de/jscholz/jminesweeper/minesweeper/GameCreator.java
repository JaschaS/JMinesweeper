package de.jscholz.jminesweeper.minesweeper;

/**
 * A simple Static-Factory-Pattern which creates a minesweeper game.
 * You can either create a game with predefined difficult settings or a game with custom difficulty settings.
 * <p> There are predefined difficult settings are: </p>
 * <ul>
 * <li>Easy, a simple game with the size 8 x 8 (width x height) and 16 percent of the cells containing mines.</li>
 * <li>Experienced, a slightly harder game with size 16 x 16 (width x height) and 16 percent of the cells containing mines.</li>
 * <li>Expert, a hard game with size 30 x 16 (width x height) and 21 percent of the cells containing mines.</li>
 * </ul>
 * <p>
 * For a custom game, you can set the width and height of the minefield and the percentage of mines inside the minefield freely.
 * But those values are restricted to certain values.
 * </p>
 * <ul>
 * <li>The smallest minefield you can create has the width 8 and height 8.</li>
 * <li>The minimum percentage of mines is 16%.</li>
 * <li>The largest minefield you can create has the width 30 and height 24.</li>
 * <li>the maximum percentage of mines is 93%.</li>
 * </ul>
 */
public final class GameCreator {

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
     * Creates a game with the difficult setting EASY.
     * @return A beginner minesweeper game.
     */
    public static IMinefield createBeginnerGame() {
        return new Minefield(Difficulty.EASY);
    }

    /**
     * Creates a game with the difficult setting EXPERIENCED.
     * @return A experienced minesweeper game.
     */
    public static IMinefield createExperiencedGame() {
        return new Minefield(Difficulty.EXPERIENCED);
    }

    /**
     * Creates a game with the difficult setting EXPERT.
     * @return A expert minesweeper game.
     */
    public static IMinefield createExpertGame() {
        return new Minefield(Difficulty.EXPERT);
    }

    /**
     * Creates a custom game with the given properties. If the properties are not valid, the method will clamp the
     * given values.
     *
     * @return A custom minesweeper game.
     */
    public static IMinefield createCustomGame(int rows, int columns, int minesPercent) {
        rows = ensureRange(rows, MIN_ROWS, MAX_ROWS);
        columns = ensureRange(columns, MIN_COLUMNS, MAX_COLUMNS);
        minesPercent = ensureRange(minesPercent, MIN_MINES_PERCENT, MAX_MINES_PERCENT);

        return new Minefield(rows, columns, minesPercent);
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

    /**
     * Create a private custom Ctor because nobody should instantiate the game creator.
     */
    private GameCreator() {

    }

}
