package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

@RunWith(Parameterized.class)
public class MinefieldValidPositionTest {
    @Parameterized.Parameter()
    public Minefield minefield;

    @Parameterized.Parameter(value = 1)
    public CellPosition position;

    @Parameterized.Parameter(value = 2)
    public boolean valid;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //Corners
                { GameCreator.createBeginnerGame(), new CellPosition(0, 0), true },
                { GameCreator.createBeginnerGame(), new CellPosition(7, 0), true },
                { GameCreator.createBeginnerGame(), new CellPosition(0, 7), true },
                { GameCreator.createBeginnerGame(), new CellPosition(7, 7), true },

                //Invalid
                { GameCreator.createBeginnerGame(), new CellPosition(-1, 0), false },
                { GameCreator.createBeginnerGame(), new CellPosition(8, 0), false },
                { GameCreator.createBeginnerGame(), new CellPosition(0, -1), false },
                { GameCreator.createBeginnerGame(), new CellPosition(0, 8), false },
                { GameCreator.createBeginnerGame(), new CellPosition(-1, -1), false },
                { GameCreator.createBeginnerGame(), new CellPosition(8, 8), false },
                { GameCreator.createBeginnerGame(), null, false }
        });
    }

    @Test
    public void neighbourTest() {
        final boolean result = minefield.validatePosition(position);
        Assert.assertEquals(valid, result);
    }
}
