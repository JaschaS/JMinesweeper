package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CellEqualTest {


    @Parameterized.Parameter()
    public Object a;

    @Parameterized.Parameter(value = 1)
    public Object b;

    @Parameterized.Parameter(value = 2)
    public boolean expectedEqual;

    @Parameterized.Parameter(value = 3)
    public boolean expected;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        final Cell c = new Cell();
        c.setContent(CellContent.EIGHT);
        c.setState(CellState.FLAGGED);
        return Arrays.asList(new Object[][]{
                { new Cell(), null, false, false},
                { new Cell(), new Object(), false, false},
                { new Cell(0,0), c, false, false},
                {c, c, true, true},
                {c, new Cell(c), true, false},
                { new Cell(), new Cell(), true, false}
        });
    }

    @Test
    public void hashTest() {
        Assert.assertEquals(expectedEqual, a.equals(b));
        Assert.assertEquals(expected, a == b);
    }

}
