package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CellGetContentTest {

    @Parameterized.Parameter()
    public CellState state;

    @Parameterized.Parameter(value = 1)
    public CellContent expectedContent;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { CellState.UNDISCOVERED, CellContent.UNKNOWN },
                { CellState.FLAGGED, CellContent.UNKNOWN },
                { CellState.OPEN, CellContent.EMPTY }
        });
    }

    @Test
    public void openTest() {
        final Cell c = new Cell();
        c.setState(state);

        // Cell should have now the given state
        Assert.assertEquals(state, c.getCellState());

        // Check the content of the cell
        Assert.assertEquals(expectedContent, c.getCellContent());
    }
}
