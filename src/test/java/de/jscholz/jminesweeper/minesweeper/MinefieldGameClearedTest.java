package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MinefieldGameClearedTest {


    private Minefield minefield;

    @Before
    public void init() {
        minefield = (Minefield) GameCreator.createBeginnerGame();
    }

    @Test
    public void GameClearedTest() {

        //Flag all mines
        final List<Cell> mines = getAllCells(CellContent.MINE);
        for (final Cell c : mines) {
            final IMinefield.OpenReturn openReturn = minefield.secondaryClick(c.getPosition());
            Assert.assertEquals(IMinefield.OpenReturn.NOW_FLAGGED, openReturn);
        }

        Assert.assertEquals(10, minefield.getAmountOfFlags());

        //Open all cells
        Cell undiscovered = getCell(CellState.UNDISCOVERED);
        Assert.assertFalse(undiscovered == null);
        do {

            final IMinefield.OpenReturn openReturn = minefield.singleClick(undiscovered.getPosition());

            if(minefield.getFreeCellsLeft() > 0)
                Assert.assertEquals(IMinefield.OpenReturn.OPEN, openReturn);
            else Assert.assertEquals(IMinefield.OpenReturn.GAME_CLEARED, openReturn);

            undiscovered = getCell(CellState.UNDISCOVERED);

        } while(undiscovered != null);

        Assert.assertEquals(0, minefield.getFreeCellsLeft() );
        Assert.assertTrue(minefield.gameOver());
    }

    private List<Cell> getAllCells(final CellContent content) {

        final ArrayList<Cell> cells = new ArrayList<>();
        final Map<ICellPosition, Cell> field = minefield.getOriginalField();

        for(final Cell c : field.values()) {
            if(c.getContent() == content) cells.add(c);
        }

        return cells;
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
