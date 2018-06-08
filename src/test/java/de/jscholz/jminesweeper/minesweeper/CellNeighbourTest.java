package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

public class CellNeighbourTest {

    @Test
    public void addNeighbourTest() {
        final Cell c = new Cell();

        Assert.assertEquals(0, c.getNeighbours().size());

        final Cell n = new Cell();
        boolean result = c.addNeighbour(n);

        Assert.assertFalse(result);
        Assert.assertEquals(0, c.getNeighbours().size());
    }

    @Test(expected = NullPointerException.class)
    public void addNeighbourExceptionTest() {
        final Cell c = new Cell();

        c.addNeighbour(null);
    }

    @Test(expected = NullPointerException.class)
    public void addNeighboursExceptionTest() {
        final Cell c = new Cell();

        c.addNeighbours(null);
    }

    @Test
    public void addDuplicateTest() {
        final Cell c = new Cell(1,2);

        Assert.assertEquals(0, c.getNeighbours().size());

        final Cell n = new Cell();
        boolean result = c.addNeighbour(n);
        Assert.assertTrue(result);
        Assert.assertEquals(1, c.getNeighbours().size());

        result = c.addNeighbour(n);
        Assert.assertFalse(result);
        Assert.assertEquals(1, c.getNeighbours().size());

    }

    @Test
    public void addDuplicateTestTwo() {
        final Cell c = new Cell(1,2);

        Assert.assertEquals(0, c.getNeighbours().size());

        boolean result = c.addNeighbour(new Cell());
        Assert.assertTrue(result);
        Assert.assertEquals(1, c.getNeighbours().size());

        result = c.addNeighbour(new Cell());
        Assert.assertFalse(result);
        Assert.assertEquals(1, c.getNeighbours().size());

    }

    @Test
    public void addItselfTest() {
        final Cell c = new Cell();

        Assert.assertEquals(0, c.getNeighbours().size());

        boolean result = c.addNeighbour(c);
        Assert.assertFalse(result);
        Assert.assertEquals(0, c.getNeighbours().size());

        result = c.addNeighbour(new Cell());
        Assert.assertFalse(result);
        Assert.assertEquals(0, c.getNeighbours().size());

        result = c.addNeighbour(new Cell(1,2));
        Assert.assertTrue(result);
        Assert.assertEquals(1, c.getNeighbours().size());
    }

}
