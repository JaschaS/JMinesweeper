package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CellPositionHashTest {

    @Parameterized.Parameter()
    public CellPosition a;

    @Parameterized.Parameter(value = 1)
    public CellPosition b;

    @Parameterized.Parameter(value = 2)
    public boolean expected;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        final CellPosition p = new CellPosition();
        return Arrays.asList(new Object[][]{
                {p, p ,true},
                {new CellPosition(0, 0), new CellPosition(0, 0), true}, //same
                {new CellPosition(1, 2), new CellPosition(3, 4), false}, //completely different
                {new CellPosition(0, 37), new CellPosition(1, 0), false}, //if hash is wrong, is true
                {new CellPosition(1, 37*3), new CellPosition(37*3, 1), false}, //if hash is wrong, is true
                {new CellPosition(1, 0), new CellPosition(0, 1), false}, //if hash is wrong, is true
        });
    }

    @Test
    public void hashTest() {
        Assert.assertEquals(expected, a.hashCode() == b.hashCode());
    }

}
