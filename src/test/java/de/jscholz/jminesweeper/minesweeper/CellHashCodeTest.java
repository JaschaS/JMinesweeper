package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CellHashCodeTest {

    @Parameterized.Parameter()
    public Cell a;

    @Parameterized.Parameter(value = 1)
    public Cell b;

    @Parameterized.Parameter(value = 2)
    public boolean expected;

    @Parameterized.Parameters(name = "{index}: testAdd({0}+{1}) = {2}")
    public static Collection<Object[]> data() {
        Cell c = new Cell();
        return Arrays.asList(new Object[][]{
                {c, c, true},
                {new Cell(), new Cell(), true},
                {new Cell(new CellPosition(1,1), ICell.CellContent.MINE, ICell.CellState.UNDISCOVERED), new Cell(new CellPosition(1,1), ICell.CellContent.MINE, ICell.CellState.UNDISCOVERED), true},
                {new Cell(), new Cell(new CellPosition(), ICell.CellContent.EMPTY, ICell.CellState.UNDISCOVERED), true},
                {new Cell(), new Cell(new CellPosition(), ICell.CellContent.EIGHT, ICell.CellState.UNDISCOVERED), false},
                {new Cell(), new Cell(new CellPosition(1,1), ICell.CellContent.EIGHT, ICell.CellState.OPEN), false},
                {new Cell(1, 2), new Cell(3, 4), false},
                {new Cell(0, 37), new Cell(1, 0), false},
                {new Cell(1, 37*3), new Cell(37*3, 1), false},
                {new Cell(1, 0), new Cell(0, 1), false}
        });
    }

    @Test
    public void hashTest() {
        Assert.assertEquals(expected, a.hashCode() == b.hashCode());
    }
}
