package de.jaschas.jminesweeper.minesweeper;

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


    private static Supplier<IMinefield> currentGame;
    private static Supplier<IMinefield> createEasyGame;
    private static Supplier<IMinefield> createExperiencedGame;
    private static Supplier<IMinefield> createExpertGame;
    private static Supplier<IMinefield> createCustomGame;
    private static Difficulty currentDifficulty;

    static {
        GameCreator.createEasyGame = () -> { return new Minefield(Difficulty.EASY); };
        GameCreator.createExperiencedGame = () -> { return new Minefield(Difficulty.EXPERIENCED); };
        GameCreator.createExpertGame = () -> { return new Minefield(Difficulty.EXPERT); };
        GameCreator.createCustomGame = () -> {

            return new Minefield(Difficulty.CUSTOM);
        };

        GameCreator.currentGame = GameCreator.createEasyGame;
    }

    public static void setDifficulty(final Difficulty difficulty) {

        GameCreator.currentDifficulty = difficulty;

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
            case CUSTOM:
                GameCreator.currentGame = GameCreator.createCustomGame;
                break;
            default:
                throw new UnsupportedOperationException("This difficult is not supported by this method.");
        }

    }

    public static Difficulty getCurrentDifficulty() {
        return GameCreator.currentDifficulty;
    }

    public static IMinefield createGame() {
        return GameCreator.currentGame.get();
    }

    /**
     * Create a private custom Ctor because nobody should instantiate the game creator.
     */
    private GameCreator() {

    }

}
