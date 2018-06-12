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
    public CellContent content;

    @Parameterized.Parameter(value = 1)
    public CellContent expected;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { CellContent.EMPTY, CellContent.ONE },
                { CellContent.ONE, CellContent.TWO },
                { CellContent.TWO, CellContent.THREE },
                { CellContent.THREE, CellContent.FOUR },
                { CellContent.FOUR, CellContent.FIVE },
                { CellContent.FIVE, CellContent.SIX },
                { CellContent.SEVEN, CellContent.EIGHT },
                { CellContent.EIGHT, CellContent.EIGHT },
                { CellContent.MINE, CellContent.MINE }
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
