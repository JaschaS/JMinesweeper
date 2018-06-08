package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class MinefieldFlagCellTest {

    @Test
    public void flagCellTest() {
        final Minefield minefield = (Minefield) GameCreator.createBeginnerGame();

        //flag cell
        final IMinefield.OpenReturn result = minefield.flagCell(0,0);
        Assert.assertEquals(IMinefield.OpenReturn.NOW_FLAGGED, result);

        //get open list
        final Set<ICell> updatedCells = minefield.getUpdateCells();
        Assert.assertEquals(1, updatedCells.size());

        //check the openlist.size should contain all cells
        final Cell c = minefield.getOriginalField().get(new CellPosition(0,0));
        Assert.assertEquals(ICell.CellState.FLAGGED, c.getCellState());

        Assert.assertEquals(1, minefield.getAmountOfFlags());
    }

    @Test
    public void removeFlagTest() {
        final Minefield minefield = (Minefield) GameCreator.createBeginnerGame();

        //flag cell
        IMinefield.OpenReturn result = minefield.flagCell(0,0);
        Assert.assertEquals(IMinefield.OpenReturn.NOW_FLAGGED, result);

        Assert.assertEquals(1, minefield.getAmountOfFlags());

        //get open list
        Set<ICell> updatedCells = minefield.getUpdateCells();
        Assert.assertEquals(1, updatedCells.size());

        //Remove Flag
        result = minefield.flagCell(0,0);
        Assert.assertEquals(IMinefield.OpenReturn.REMOVE_FLAG, result);

        //get open list
        updatedCells = minefield.getUpdateCells();
        Assert.assertEquals(1, updatedCells.size());

        //check the openlist.size should contain all cells
        final Cell c = minefield.getOriginalField().get(new CellPosition(0,0));
        Assert.assertEquals(ICell.CellState.UNDISCOVERED, c.getCellState());

        Assert.assertEquals(0, minefield.getAmountOfFlags());
    }

    @Test
    public void flagCellWhichIsOpenTest() {
        final Minefield minefield = (Minefield) GameCreator.createBeginnerGame();

        final Cell c = getCell(ICell.CellContent.ONE, minefield);
        Assert.assertFalse(c == null);

        //open Cell
        IMinefield.OpenReturn result = minefield.open(c.getPosition());
        Assert.assertEquals(IMinefield.OpenReturn.OPEN, result);

        //get open list
        Set<ICell> updatedCells = minefield.getUpdateCells();
        Assert.assertEquals(1, updatedCells.size());

        //flag cell
        result = minefield.flagCell(c.getPosition());
        Assert.assertEquals(IMinefield.OpenReturn.IS_ALREADY_OPEN, result);
        Assert.assertEquals(0, minefield.getAmountOfFlags());

        //get open list
        updatedCells = minefield.getUpdateCells();
        Assert.assertEquals(0, updatedCells.size());
    }

    @Test
    public void positionNotValidTest() {
        final Minefield minefield = (Minefield) GameCreator.createBeginnerGame();

        //open Cell
        IMinefield.OpenReturn result = minefield.flagCell(-1, -1);
        Assert.assertEquals(IMinefield.OpenReturn.NOT_VALID, result);
        Assert.assertEquals(0, minefield.getAmountOfFlags());

        //get open list
        final Set<ICell> updatedCells = minefield.getUpdateCells();
        Assert.assertEquals(0, updatedCells.size());
    }

    @Test
    public void gameWasAlreadyOverTest() {
        final Minefield minefield = (Minefield) GameCreator.createBeginnerGame();

        final Cell c = getCell(ICell.CellContent.MINE, minefield);
        Assert.assertFalse(c == null);

        //open Cell
        IMinefield.OpenReturn result = minefield.open(c.getPosition());
        Assert.assertEquals(IMinefield.OpenReturn.WAS_MINE, result);

        //check if game is over
        final boolean gameOVer = minefield.gameOver();
        Assert.assertTrue(gameOVer);

        result = minefield.flagCell(0, 0);
        Assert.assertEquals(IMinefield.OpenReturn.GAME_IS_ALREADY_OVER, result);
        Assert.assertEquals(0, minefield.getAmountOfFlags());
    }

    private Cell getCell(final ICell.CellContent content, final Minefield minefield) {

        Map<ICellPosition, Cell> field = minefield.getOriginalField();

        for(final Cell c : field.values()) {
            if(c.getContent() == content) return c;
        }

        return null;
    }

}