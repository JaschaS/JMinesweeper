package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

@RunWith(Parameterized.class)
public class MinefieldNeighboursTest {
    @Parameterized.Parameter()
    public Minefield minefield;

    @Parameterized.Parameter(value = 1)
    public CellPosition startPosition;

    @Parameterized.Parameter(value = 2)
    public int expectedSize;

    public HashSet<ICellPosition> cellPositions;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
                //Borders
                { GameCreator.createBeginnerGame(), new CellPosition(0, 1), 5 },   //left first
                { GameCreator.createBeginnerGame(), new CellPosition(0, 6), 5 },   //left last
                { GameCreator.createBeginnerGame(), new CellPosition(7, 1), 5 },   //right first
                { GameCreator.createBeginnerGame(), new CellPosition(7, 6), 5 },   //right last
                { GameCreator.createBeginnerGame(), new CellPosition(1, 0), 5 },   //top first
                { GameCreator.createBeginnerGame(), new CellPosition(6, 0), 5 },   //top last
                { GameCreator.createBeginnerGame(), new CellPosition(1, 7), 5 },   //down first
                { GameCreator.createBeginnerGame(), new CellPosition(6, 7), 5 },   //down last

                //Corners
                { GameCreator.createBeginnerGame(), new CellPosition(0, 0), 3 },
                { GameCreator.createBeginnerGame(), new CellPosition(7, 0), 3 },
                { GameCreator.createBeginnerGame(), new CellPosition(7, 7), 3 },
                { GameCreator.createBeginnerGame(), new CellPosition(0, 7), 3 },

                //center-corners
                { GameCreator.createBeginnerGame(), new CellPosition(1, 1), 8 },
                { GameCreator.createBeginnerGame(), new CellPosition(6, 1), 8 },
                { GameCreator.createBeginnerGame(), new CellPosition(1, 6), 8 },
                { GameCreator.createBeginnerGame(), new CellPosition(6, 6), 8 },
        });
    }

    @Before
    public void init() {
        cellPositions = new HashSet<>();

        final int x = startPosition.getX();
        final int y = startPosition.getY();


        CellPosition p;

        //Add all possible neighbours of the cell. Use Valid-Position to create this.
        //top-left
        p = new CellPosition(x-1, y-1);
        if(minefield.validatePosition(p)) cellPositions.add(p);
        //top
        p = new CellPosition(x, y-1);
        if(minefield.validatePosition(p)) cellPositions.add(p);
        //top-right
        p = new CellPosition(x+1, y-1);
        if(minefield.validatePosition(p)) cellPositions.add(p);

        //right
        p = new CellPosition(x-1, y);
        if(minefield.validatePosition(p)) cellPositions.add(p);
        //left
         p = new CellPosition(x+1, y);
        if(minefield.validatePosition(p)) cellPositions.add(p);

        //down-left
        p = new CellPosition(x-1, y+1);
        if(minefield.validatePosition(p)) cellPositions.add(p);
        //down
        p = new CellPosition(x, y+1);
        if(minefield.validatePosition(p)) cellPositions.add(p);
        //down-right
        p = new CellPosition(x+1, y+1);
        if(minefield.validatePosition(p)) cellPositions.add(p);
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
