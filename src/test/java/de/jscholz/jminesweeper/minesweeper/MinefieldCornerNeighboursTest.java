package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

@RunWith(Parameterized.class)
public class MinefieldCornerNeighboursTest {

    @Parameterized.Parameter()
    public Minefield minefield;

    @Parameterized.Parameter(value = 1)
    public CellPosition startPosition;

    @Parameterized.Parameter(value = 2)
    public Set<ICellPosition> cellPositions;

    @Parameterized.Parameter(value = 3)
    public int expectedSize;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {

        final HashSet<ICellPosition> topLeft = new HashSet<>();
        topLeft.add(new CellPosition(1,0));
        topLeft.add(new CellPosition(0,1));
        topLeft.add(new CellPosition(1,1));

        final HashSet<ICellPosition> topRight = new HashSet<>();
        topRight.add(new CellPosition(6,0));
        topRight.add(new CellPosition(7,1));
        topRight.add(new CellPosition(6,1));

        final HashSet<ICellPosition> downRight = new HashSet<>();
        downRight.add(new CellPosition(6,7));
        downRight.add(new CellPosition(7,6));
        downRight.add(new CellPosition(6,6));

        final HashSet<ICellPosition> downLeft = new HashSet<>();
        downLeft.add(new CellPosition(1,7));
        downLeft.add(new CellPosition(0,6));
        downLeft.add(new CellPosition(1,6));

        return Arrays.asList(new Object[][]{
                //Corners
                //{ GameCreator.createBeginnerGame(), new CellPosition(0, 0), topLeft, 3 },
                //{ GameCreator.createBeginnerGame(), new CellPosition(7, 0), topRight, 3 },
                //{ GameCreator.createBeginnerGame(), new CellPosition(7, 7), downRight, 3 },
                //{ GameCreator.createBeginnerGame(), new CellPosition(0, 7), downLeft, 3 }
        });
    }

    @Test
    public void neighbourTest() {
        final Map<ICellPosition, Cell> map = minefield.getOriginalField();

        final Cell cell = map.get(startPosition);
        Assert.assertTrue(cell != null);
        final Set<Cell> n = cell.getNeighbours();

        //Check the expected size.
        Assert.assertEquals(expectedSize, n.size());

        for(final Cell c : n) {
            //Note we don't compare here the cells, because we don't know if the cells inside the field is empty,
            //has a number or is a mine. If we check n.contains(new Cell(1,0)) for example, sometimes the Set
            //would return true and sometimes false. The reason for this is the different content of the cell.

            //Compare the positions.
            final ICellPosition pos = c.getPosition();
            Assert.assertTrue(cellPositions.contains(pos));
        }
    }
}
