package de.jscholz.jminesweeper.minesweeper;

import java.util.function.Consumer;
import java.util.function.Supplier;

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

    private static Supplier<IMinefield> currentGame;
    private static Supplier<IMinefield> createEasyGame;
    private static Supplier<IMinefield> createExperiencedGame;
    private static Supplier<IMinefield> createExpertGame;
    private static Supplier<IMinefield> createCustomGame;
    private static int rows;
    private static int columns;
    private static int mines;

    static {

        GameCreator.rows = MIN_ROWS;
        GameCreator.columns = MIN_COLUMNS;
        GameCreator.mines = MIN_MINES_PERCENT;

        GameCreator.createEasyGame = () -> { return new Minefield(Difficulty.EASY); };
        GameCreator.createExperiencedGame = () -> { return new Minefield(Difficulty.EXPERIENCED); };
        GameCreator.createExpertGame = () -> { return new Minefield(Difficulty.EXPERIENCED); };
        GameCreator.createCustomGame = () -> {

            return new Minefield(GameCreator.rows, GameCreator.columns, GameCreator.mines);
        };

        GameCreator.currentGame = GameCreator.createEasyGame;
    }

    public static void setGame(final int width, final int height, final int minesPercent) {
        GameCreator.rows = ensureRange(width, MIN_ROWS, MAX_ROWS);
        GameCreator.columns = ensureRange(height, MIN_COLUMNS, MAX_COLUMNS);
        GameCreator.mines = ensureRange(minesPercent, MIN_MINES_PERCENT, MAX_MINES_PERCENT);

        GameCreator.currentGame = createCustomGame;
    }

    public static void setGame(final Difficulty difficulty) {

        switch (difficulty) {
            case EASY:
                GameCreator.currentGame = GameCreator.createEasyGame;
                break;
            case EXPERIENCED:
                GameCreator.currentGame = GameCreator.createExperiencedGame;
                break;
            case EXPERT:
                GameCreator.currentGame = GameCreator.createExpertGame;
                break;
        }

    }

    public static IMinefield createGame() {
        return GameCreator.currentGame.get();
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
