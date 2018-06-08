package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CellIncreaseNumberTest {

    @Parameterized.Parameter()
    public ICell.CellContent content;

    @Parameterized.Parameter(value = 1)
    public ICell.CellContent expected;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { ICell.CellContent.EMPTY, ICell.CellContent.ONE },
                { ICell.CellContent.ONE, ICell.CellContent.TWO },
                { ICell.CellContent.TWO, ICell.CellContent.THREE },
                { ICell.CellContent.THREE, ICell.CellContent.FOUR },
                { ICell.CellContent.FOUR, ICell.CellContent.FIVE },
                { ICell.CellContent.FIVE, ICell.CellContent.SIX },
                { ICell.CellContent.SEVEN, ICell.CellContent.EIGHT },
                { ICell.CellContent.EIGHT, ICell.CellContent.EIGHT },
                { ICell.CellContent.MINE, ICell.CellContent.MINE }
        });
    }

    @Test
    public void increaseNumberTest() {
        final Cell c = new Cell();
        c.setContent(content);

        Assert.assertEquals(content, c.getContent());

        c.increaseNumber();
        Assert.assertEquals(expected, c.getContent());
    }

}
