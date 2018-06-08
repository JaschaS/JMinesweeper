package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CellPositionEqualTest {

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
        final CellPosition p = new CellPosition();
        return Arrays.asList(new Object[][]{
                { new CellPosition(), null, false, false},
                { new CellPosition(), new Object(), false, false},
                { new CellPosition(37,0), new CellPosition(1, 0), false, false},
                { p, p, true, true},
                { p, new CellPosition(p), true, false},
                { new CellPosition(), new CellPosition(), true, false},
                { new CellPosition(0,37), new CellPosition(1, 0), false, false},
                { new CellPosition(1,37*3), new CellPosition(37*3, 1), false, false},
                { new CellPosition(1,0), new CellPosition(0, 1), false, false}
        });
    }

    @Test
    public void hashTest() {
        Assert.assertEquals(expectedEqual, a.equals(b));
        Assert.assertEquals(expected, a == b);
    }

}
