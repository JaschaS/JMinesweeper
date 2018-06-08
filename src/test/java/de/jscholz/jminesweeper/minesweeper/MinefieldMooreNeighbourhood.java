package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

@RunWith(Parameterized.class)
public class MinefieldMooreNeighbourhood {

    public Minefield minefield;

    @Parameterized.Parameter()
    public CellPosition startPosition;

    @Parameterized.Parameter(value = 1)
    public int expectedSize;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
                //Borders
                { new CellPosition(0, 1), 5 },   //left first
                { new CellPosition(0, 6), 5 },   //left last
                { new CellPosition(7, 1), 5 },   //right first
                { new CellPosition(7, 6), 5 },   //right last
                { new CellPosition(1, 0), 5 },   //top first
                { new CellPosition(6, 0), 5 },   //top last
                { new CellPosition(1, 7), 5 },   //down first
                { new CellPosition(6, 7), 5 },   //down last

                //Corners
                { new CellPosition(0, 0), 3 },
                { new CellPosition(7, 0), 3 },
                { new CellPosition(7, 7), 3 },
                { new CellPosition(0, 7), 3 },

                //center-corners
                { new CellPosition(1, 1), 8 },
                { new CellPosition(6, 1), 8 },
                { new CellPosition(1, 6), 8 },
                { new CellPosition(6, 6), 8 },
        });
    }

    @Before
    public void init() {
        minefield = (Minefield) GameCreator.createBeginnerGame();
    }

    @Test
    public void neighbourTest() {
        final List<Cell> neighbours = minefield.getMooreNeighbourhood(startPosition);

        Assert.assertEquals(expectedSize, neighbours.size());
    }
}
