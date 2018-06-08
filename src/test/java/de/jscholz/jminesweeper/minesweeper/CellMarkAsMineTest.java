package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CellMarkAsMineTest {

    @Parameterized.Parameter(value = 0)
    public ICell.CellContent content;

    @Parameterized.Parameter(value = 1)
    public boolean expected;

    @Parameterized.Parameter(value = 2)
    public ICell.CellContent expectedContent;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { ICell.CellContent.EMPTY, true, ICell.CellContent.MINE},
                { ICell.CellContent.MINE, false, ICell.CellContent.MINE}
        });
    }

    @Test
    public void markAsMineTest() {
        final Cell c = new Cell();
        c.setContent(content);

        final boolean result = c.markAsMine();
        Assert.assertEquals(expected, result);
        Assert.assertEquals(expectedContent, c.getContent());
    }

    @Test
    public void cellAsNeighbourTest() {
        final Cell c = new Cell();
        c.setContent(ICell.CellContent.EMPTY);
        c.addNeighbour(c);

        final boolean result = c.markAsMine();
        Assert.assertEquals(true, result);
        Assert.assertEquals(ICell.CellContent.MINE, c.getContent());

    }

}
