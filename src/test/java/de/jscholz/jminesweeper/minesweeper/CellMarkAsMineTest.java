package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CellMarkAsMineTest {

    @Parameterized.Parameter()
    public CellContent content;

    @Parameterized.Parameter(value = 1)
    public boolean expected;

    @Parameterized.Parameter(value = 2)
    public CellContent expectedContent;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { CellContent.EMPTY, true, CellContent.MINE},
                { CellContent.MINE, false, CellContent.MINE}
        });
    }

    @Test
    public void markAsMineTest() {
        final Cell c = new Cell();
        c.setState(CellState.OPEN);
        c.setContent(content);

        final boolean result = c.markAsMine();
        Assert.assertEquals(expected, result);
        Assert.assertEquals(expectedContent, c.getCellContent());
    }

    @Test
    public void cellAsNeighbourTest() {
        final Cell c = new Cell();
        c.setState(CellState.OPEN);
        c.setContent(CellContent.EMPTY);
        c.addNeighbour(c);

        final boolean result = c.markAsMine();
        Assert.assertEquals(true, result);
        Assert.assertEquals(CellContent.MINE, c.getCellContent());

    }

}
