package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

public class MinefieldGetFieldTest {

    @Test
    public void getFieldTest() {
        final IMinefield m = GameCreator.createBeginnerGame();

        //Get the field for visualisation.
        final Map<ICellPosition, ICell> f = m.getFieldForVisualization();

        //Get the field the game is using.
        final Map<ICellPosition, Cell> original = ((Minefield)m).getOriginalField();

        for(Map.Entry<ICellPosition, ICell> entry : f.entrySet()) {
            final ICellPosition p = entry.getKey();
            final ICell c = entry.getValue();

            //Compare all position and make sure they exist inside the visualisation-field.
            Assert.assertTrue(original.containsKey(p));

            final Cell oc = original.get(p);
            Assert.assertTrue(oc != null);

            final ICellPosition op = oc.getPosition();

            //The position should have the same hashes and be equal.
            Assert.assertTrue(p.hashCode() == op.hashCode());
            Assert.assertTrue(p.equals(op));

            //The cells should not have the same hashes and should not be equal.
            Assert.assertFalse(c.hashCode() == oc.hashCode());
            Assert.assertFalse(c.equals(oc));

            //the cells the visual-field should be empty, be state undiscovered.
            Assert.assertTrue(c.getCellState() == CellState.UNDISCOVERED);
            Assert.assertTrue(c.getCellContent() == CellContent.UNKNOWN);

            //The amount of neighbours should be the same.
            Assert.assertTrue(((Cell)c).getNeighbours().size() != oc.getNeighbours().size());
            Assert.assertEquals(0, ((Cell)c).getNeighbours().size());
        }
    }

    @Test
    public void getFieldForVisualization_CheckSorting_Pass() {
        final IMinefield m = GameCreator.createBeginnerGame();

        //Get the field for visualisation.
        final Map<ICellPosition, ICell> f = m.getFieldForVisualization();

        Assert.assertEquals(8*8, f.size());

        final ArrayList<ICellPosition> listOfPositions = new ArrayList<>(f.keySet());

        //first entry should be 0,0(x,y)
        Assert.assertEquals(new CellPosition(0,0), listOfPositions.get(0));

        //first entry should be 0,0(x,y)
        Assert.assertEquals(new CellPosition(1,0), listOfPositions.get(1));

        //first entry should be 0,0(x,y)
        Assert.assertEquals(new CellPosition(6,7), listOfPositions.get(listOfPositions.size()-2));

        //Last entry should be 7,7(x,y)
        Assert.assertEquals(new CellPosition(7,7), listOfPositions.get(listOfPositions.size()-1));
    }

}
