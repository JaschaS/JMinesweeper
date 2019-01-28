package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MinefieldUpdatedCellsTest {

    private Minefield minefield;

    @Before
    public void init() {
        minefield = null; //(Minefield) GameCreator.createBeginnerGame();
    }

    @Test
    public void getUpdateCells_OpenWhenContentIsNumber_Pass() {

        //Get Cell with content number
        final Cell c = getCell(CellContent.ONE);
        Assert.assertFalse(c == null);
        Assert.assertTrue(c.getContent() == CellContent.ONE);
        Assert.assertTrue(c.getCellState() == CellState.UNDISCOVERED);

        // Open cell
        final IMinefield.OpenReturn value = minefield.singleClick(c.getPosition());

        // Cell was opened
        Assert.assertTrue(value == IMinefield.OpenReturn.OPEN);

        //Check if updated cells is 1
        final Set<ICell> cellSet = minefield.getUpdateCells();
        Assert.assertEquals(1, cellSet.size());

        // The cell which was opened should now inside the set.
        Assert.assertTrue(cellSet.contains(c));

        //Make sure game is still running
        final boolean gameOver = minefield.gameOver();
        Assert.assertFalse(gameOver);
    }

    @Test
    public void getUpdateCells_OpenWhenContentIsEmpty_Pass() {

        //Get Cell with content number
        final Cell c = getCell(CellContent.EMPTY);
        Assert.assertFalse(c == null);
        Assert.assertTrue(c.getContent() == CellContent.EMPTY);
        Assert.assertTrue(c.getCellState() == CellState.UNDISCOVERED);

        // Open cell
        final IMinefield.OpenReturn value = minefield.singleClick(c.getPosition());

        // Cell was opened
        Assert.assertTrue(value == IMinefield.OpenReturn.OPEN);

        //Check if updated cells is greater than 1
        final Set<ICell> cellSet = minefield.getUpdateCells();
        Assert.assertTrue( cellSet.size() > 1);

        final List<Cell> expectedCells = getAllCells(CellState.OPEN);
        Assert.assertTrue(cellSet.containsAll(expectedCells));

        // The cell which was opened should now inside the set.
        Assert.assertTrue(cellSet.contains(c));

        //Make sure game is still running
        final boolean gameOver = minefield.gameOver();
        Assert.assertFalse(gameOver);
    }

    @Test
    public void getUpdateCells_OpenWhenStateIsOpen_Pass() {

        getUpdateCells_OpenWhenContentIsNumber_Pass();

        //Get Cell with content number
        final Cell c = getCell(CellState.OPEN);
        Assert.assertFalse(c == null);
        Assert.assertTrue(c.getCellState() == CellState.OPEN);

        // Open cell
        final IMinefield.OpenReturn value = minefield.singleClick(c.getPosition());

        // Cell was opened
        Assert.assertTrue(value == IMinefield.OpenReturn.IS_ALREADY_OPEN);

        //Check if updated cells is 0
        final Set<ICell> cellSet = minefield.getUpdateCells();
        Assert.assertEquals(0, cellSet.size());

        //Make sure game is still running
        final boolean gameOver = minefield.gameOver();
        Assert.assertFalse(gameOver);
    }

    @Test
    public void getUpdateCells_AfterFlagging_Pass() {

        //Get Cell with content number
        final Cell c = getCell(CellState.UNDISCOVERED);
        Assert.assertFalse(c == null);
        Assert.assertTrue(c.getCellState() == CellState.UNDISCOVERED);

        // Open cell
        final IMinefield.OpenReturn value = minefield.secondaryClick(c.getPosition());

        // Cell was opened
        Assert.assertTrue(value == IMinefield.OpenReturn.NOW_FLAGGED);

        //Check if updated cells is 1
        final Set<ICell> cellSet = minefield.getUpdateCells();
        Assert.assertEquals(1, cellSet.size());

        // The cell which was opened should now inside the set.
        Assert.assertTrue(cellSet.contains(c));

        //Make sure game is still running
        final boolean gameOver = minefield.gameOver();
        Assert.assertFalse(gameOver);
    }


    @Test
    public void getUpdateCells_OpenWhenStateIsFlagged_Pass() {

        minefield.secondaryClick(0, 0);

        //Get Cell with content number
        final Cell c = getCell(CellState.FLAGGED);
        Assert.assertFalse(c == null);
        Assert.assertTrue(c.getCellState() == CellState.FLAGGED);

        // Open cell
        final IMinefield.OpenReturn value = minefield.singleClick(c.getPosition());

        // Cell was opened
        Assert.assertTrue(value == IMinefield.OpenReturn.WAS_FLAGGED);

        //Check if updated cells is 0
        final Set<ICell> cellSet = minefield.getUpdateCells();
        Assert.assertEquals(0, cellSet.size());

        //Make sure game is still running
        final boolean gameOver = minefield.gameOver();
        Assert.assertFalse(gameOver);
    }

    @Test
    public void getUpdateCells_OpenCellWithMine_Pass() {

        //Get Cell with content number
        final Cell c = getCell(CellContent.MINE);
        Assert.assertFalse(c == null);
        Assert.assertTrue(c.getContent() == CellContent.MINE);
        Assert.assertTrue(c.getCellState() == CellState.UNDISCOVERED);

        // Open cell
        final IMinefield.OpenReturn value = minefield.singleClick(c.getPosition());

        // Cell was opened
        Assert.assertTrue(value == IMinefield.OpenReturn.WAS_MINE);

        //Check if updated cells is 8x8
        final Set<ICell> cellSet = minefield.getUpdateCells();
        final Map<ICellPosition, Cell> field = minefield.getOriginalField();
        Assert.assertEquals(field.size(), cellSet.size());

        Assert.assertTrue(cellSet.containsAll(field.values()));

        //Make sure game is still running
        final boolean gameOver = minefield.gameOver();
        Assert.assertTrue(gameOver);
    }

    private List<Cell> getAllCells(final CellState state) {

        final ArrayList<Cell> cells = new ArrayList<>();
        final Map<ICellPosition, Cell> field = minefield.getOriginalField();

        for(final Cell c : field.values()) {
            if(c.getCellState() == state) cells.add(c);
        }

        return cells;
    }

    private Cell getCell(final CellContent content) {
        final Map<ICellPosition, Cell> field = minefield.getOriginalField();

        for(final Cell c : field.values()) {
            if(c.getContent() == content) return c;
        }

        return null;
    }

    private Cell getCell(final CellState state) {
        Map<ICellPosition, Cell> field = minefield.getOriginalField();

        for(final Cell c : field.values()) {
            if(c.getCellState() == state) return c;
        }

        return null;
    }
}
