package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GameCreatorTest {

    @Parameterized.Parameter()
    public IMinefield field;

    @Parameterized.Parameter(value = 1)
    public int mines;

    @Parameterized.Parameter(value = 2)
    public int rows;

    @Parameterized.Parameter(value = 3)
    public int cols;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { GameCreator.createBeginnerGame(), 10, 8, 8},
                { GameCreator.createExperiencedGame(), 40, 16, 16},
                { GameCreator.createExpertGame(), 100, 30, 16},
                { GameCreator.createCustomGame(GameCreator.MIN_ROWS, GameCreator.MIN_COLUMNS, GameCreator.MIN_MINES_PERCENT), 10, 8, 8},
                { GameCreator.createCustomGame(-1, 8, 10), 10, 8, 8}, // below min rows
                { GameCreator.createCustomGame(7, 8, 10), 10, 8, 8}, // below min rows
                { GameCreator.createCustomGame(8, 7, 10), 10, 8, 8}, // below min cols
                { GameCreator.createCustomGame(8, -1, 10), 10, 8, 8}, // below min cols
                { GameCreator.createCustomGame(8, 8, 8), 10, 8, 8}, // below min mine
                { GameCreator.createCustomGame(8, 8, -1), 10, 8, 8}, // below min mine
                { GameCreator.createCustomGame(-1, -1, -1), 10, 8, 8}, // below min

                { GameCreator.createCustomGame(GameCreator.MAX_ROWS+1, GameCreator.MAX_COLUMNS, GameCreator.MAX_MINES_PERCENT), 669, 30, 24}, // above min rows
                { GameCreator.createCustomGame(GameCreator.MAX_ROWS, GameCreator.MAX_COLUMNS+1, GameCreator.MAX_MINES_PERCENT), 669, 30, 24}, // above min cols
                { GameCreator.createCustomGame(GameCreator.MAX_ROWS, GameCreator.MAX_COLUMNS, GameCreator.MAX_MINES_PERCENT+10), 669, 30, 24}, // above min mine
                { GameCreator.createCustomGame(GameCreator.MAX_ROWS+1, GameCreator.MAX_COLUMNS+1, GameCreator.MAX_MINES_PERCENT+10), 669, 30, 24}, // above min
                { GameCreator.createCustomGame(GameCreator.MAX_ROWS, GameCreator.MAX_COLUMNS, GameCreator.MAX_MINES_PERCENT), 669, 30, 24}
        });
    }

    @Test
    public void createTest() {
        Assert.assertEquals(mines, field.getTotalMines());
        Assert.assertEquals(rows, field.getRows());
        Assert.assertEquals(cols, field.getColumns());
    }

}
