package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DoubleClickTest {

    private Minefield minefield;

    @Before
    public void init() {

        GameCreator.setGame(Difficulty.EASY);
        minefield = (Minefield) GameCreator.createGame();
    }

    @Test
    public void notValidPositionTest() {
        /**
         * If the position is not valid return not_valid
         */
        final CellPosition position = new CellPosition(-1, -1);

        final IMinefield.OpenReturn openReturn = minefield.doubleClick(position);
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.NOT_VALID);
    }

    @Test
    public void gameAlreadyOverTest() {
        /**
         * Find a mine and set game to game over.
         * try to open a cell with double click
         */

        final Cell cell = getCell(CellContent.MINE);
        Assert.assertFalse(cell == null);

        IMinefield.OpenReturn openReturn = this.minefield.singleClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.WAS_MINE);
        Assert.assertTrue(this.minefield.gameOver());

        openReturn = this.minefield.doubleClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.GAME_IS_ALREADY_OVER);
    }

    @Test
    public void openUndiscoveredCellTest () {
        /**
         * This test case is really hard to test. I want that all cells are opened, but it shouldn't return was_already_openen.
         * Double click on a cell which is undiscovered and is not a mine. And no mine and flags in the moore neighbourhood.
         * Expected out come:
         * - cell is now opened.
         * - game is not over
         * - the moore neighbourhood is now opened.
         * - the neighbourhood cells are now in the updated cell list.
         */
        // Get a cell which is not a mine. And also doesn't have mines nor flags inside the neighbourhood.
        final CellContent[] contents = new CellContent[]{CellContent.MINE};
        final Cell cell = getCell(CellState.UNDISCOVERED, contents, CellContent.MINE);
        Assert.assertFalse(cell == null);
        Assert.assertTrue(cell.getCellState() == CellState.UNDISCOVERED);
        Assert.assertFalse(cell.getCellContent() == CellContent.MINE);

        // Now double click on the cell.
        IMinefield.OpenReturn openReturn = minefield.doubleClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.OPEN);

        // Cell is now opened.
        Assert.assertTrue(cell.getCellState() == CellState.OPEN);

        // Check neighbourhood.
        final Set<Cell> neighbourhood = cell.getNeighbours();
        Assert.assertFalse(neighbourhood == null);
        for(final Cell c : neighbourhood) {
            Assert.assertTrue(c.getCellState() == CellState.OPEN);
        }

        // Game is not over.
        Assert.assertFalse(minefield.gameOver());
        final Set<ICell> updatedCells = minefield.getUpdateCells();
        //Size is now greater equal size
        Assert.assertTrue(updatedCells.size() >= neighbourhood.size());
        Assert.assertTrue(updatedCells.containsAll(neighbourhood));
    }

    @Test
    public void openFlagCellTest() {
        /**
         * Double click on a cell which was flagged. And no mine and flags in the moore neighbourhood.
         * Expected out come:
         * - cell is still flagged.
         * - game is not over
         * - the moore neighbourhood is now opened.
         * - the neighbourhood cells are now in the updated cell list.
         */
        // Get a cell which is not a mine. And also doesn't have mines nor flags inside the neighbourhood.
        final CellContent[] contents = new CellContent[]{CellContent.MINE};
        final Cell cell = getCell(CellState.UNDISCOVERED, contents, CellContent.MINE);
        Assert.assertFalse(cell == null);
        Assert.assertTrue(cell.getCellState() == CellState.UNDISCOVERED);
        Assert.assertFalse(cell.getCellContent() == CellContent.MINE);

        // Flag the cell
        IMinefield.OpenReturn openReturn = minefield.secondaryClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.NOW_FLAGGED);
        Assert.assertTrue(cell.getCellState() == CellState.FLAGGED);

        // Now double click on the cell.
        openReturn = minefield.doubleClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.OPEN);

        // Cell is still flagged.
        Assert.assertTrue(cell.getCellState() == CellState.FLAGGED);

        // Check neighbourhood.
        final Set<Cell> neighbourhood = cell.getNeighbours();
        Assert.assertFalse(neighbourhood == null);
        for(final Cell c : neighbourhood) {
            Assert.assertTrue(c.getCellState() == CellState.OPEN);
        }

        // Game is not over.
        //TODO Ist manchmal positiv manchmal negativ.
        Assert.assertFalse(minefield.gameOver());
        final Set<ICell> updatedCells = minefield.getUpdateCells();
        //Size is now greater equal size
        Assert.assertTrue(updatedCells.size() >= neighbourhood.size());
        Assert.assertTrue(updatedCells.containsAll(neighbourhood));
    }

    @Test
    public void cellIsOpenAndMinesAreFlagedTest() {
        //TODO Ist manchmal positiv manchmal negativ
        /**
         * Double click on a cell which is opened. All mines in the neighbourhood are flagged.
         * Expected out come:
         * - cell is still opened.
         * - game is not over
         * - the moore neighbourhood is now opened except of those cells who are flagged.
         * - the neighbourhood cells which are now opened are in the updated cell list.
         */
        // Get a cell which is not a mine nor empty.
        final CellContent[] contents = new CellContent[]{CellContent.MINE, CellContent.EMPTY};
        final Cell cell = getCell(CellContent.ONE);
        Assert.assertFalse(cell == null);
        Assert.assertTrue(cell.getCellState() == CellState.UNDISCOVERED);
        Assert.assertFalse(cell.getCellContent() == CellContent.MINE);

        //Open the cell
        IMinefield.OpenReturn openReturn = this.minefield.singleClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.OPEN);
        Assert.assertTrue(cell.getCellState() == CellState.OPEN);

        // Flag the mine in the moore neighbourhood.
        final Set<Cell> neighbourhood = cell.getNeighbours();
        Assert.assertFalse(neighbourhood == null);
        int numberOfMines = 0;
        for(final Cell c : neighbourhood) {
            if(c.getContent() == CellContent.MINE) {
                openReturn = this.minefield.secondaryClick(c.getPosition());
                Assert.assertTrue(openReturn == IMinefield.OpenReturn.NOW_FLAGGED);
                Assert.assertTrue(c.getCellState() == CellState.FLAGGED);
                ++numberOfMines;
            }
        }

        Assert.assertEquals(1, numberOfMines);

        // Now double click on the open cell.
        openReturn = this.minefield.doubleClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.OPEN);

        // Check if the game is not over
        //TODO Ist manchmal positiv manchmal negativ.
        Assert.assertFalse(this.minefield.gameOver());

        // Check if the neighbourhood is open and if the mines are still flagged
        // Count how many cells are now open
        int count = 0;
        for(final Cell c : neighbourhood) {
            if(c.getContent() == CellContent.MINE) {
                Assert.assertTrue(c.getCellState() == CellState.FLAGGED);
            }
            else {
                Assert.assertTrue(c.getCellState() == CellState.OPEN);
                ++count;
            }
        }

        //Check if the update list is greater equal to count
        final Set<ICell> updatedCells = minefield.getUpdateCells();
        Assert.assertTrue(updatedCells.size() >= count);
    }

    @Test
    public void allNeighboursAndCellAreFlagged() {
        /**
         * Double click on a cell which is flagged. All cells in the neighbourhood are flagged.
         * Expected out come:
         * - cell is still flagged.
         * - game is not over
         * - the moore neighbourhood is still flagged.
         * - the update list is 0.
         * - the open return value is WAS_FLAGGED.
         */
        // Get a cell which is not a mine nor empty.
        final Cell cell = getCell(CellContent.ONE);
        Assert.assertFalse(cell == null);
        Assert.assertTrue(cell.getCellState() == CellState.UNDISCOVERED);
        Assert.assertFalse(cell.getCellContent() == CellContent.MINE);

        //Open the cell
        IMinefield.OpenReturn openReturn = this.minefield.secondaryClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.NOW_FLAGGED);
        Assert.assertTrue(cell.getCellState() == CellState.FLAGGED);

        // Flag the mines in the moore neighbourhood.
        final Set<Cell> neighbourhood = cell.getNeighbours();
        Assert.assertFalse(neighbourhood == null);
        int numberOfFlaggs = 0;
        for(final Cell c : neighbourhood) {
            openReturn = this.minefield.secondaryClick(c.getPosition());
            Assert.assertTrue(openReturn == IMinefield.OpenReturn.NOW_FLAGGED);
            Assert.assertTrue(c.getCellState() == CellState.FLAGGED);
            ++numberOfFlaggs;
        }
        Assert.assertEquals(neighbourhood.size(), numberOfFlaggs);

        // Now double click on the open cell.
        openReturn = this.minefield.doubleClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.WAS_FLAGGED);

        // Check if the game is not over
        Assert.assertFalse(this.minefield.gameOver());

        // Check if the neighbourhood is open and if the mines are still flagged
        for(final Cell c : neighbourhood) {
            Assert.assertTrue(c.getCellState() == CellState.FLAGGED);
        }

        //Check if the update list is greater equal to count
        final Set<ICell> updatedCells = minefield.getUpdateCells();
        Assert.assertEquals(0, updatedCells.size());
    }

    @Test
    public void openMineInNeighbourhoodTest() {
        /**
         * Double click on a cell which is opened. All mines in the neighbourhood are not flagged.
         * Expected out come:
         * - cell is still opened.
         * - game is over
         * - the moore neighbourhood is now opened.
         */
        // Get a cell which is not a mine nor empty.
        final Cell cell = getCell(CellContent.ONE);
        Assert.assertFalse(cell == null);
        Assert.assertTrue(cell.getCellState() == CellState.UNDISCOVERED);
        Assert.assertFalse(cell.getCellContent() == CellContent.MINE);

        //Open the cell
        IMinefield.OpenReturn openReturn = this.minefield.singleClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.OPEN);
        Assert.assertTrue(cell.getCellState() == CellState.OPEN);

        // Check that the cells in the neighbourhood are still undiscovered.
        final Set<Cell> neighbourhood = cell.getNeighbours();
        Assert.assertFalse(neighbourhood == null);
        for(final Cell c : neighbourhood) {
            Assert.assertTrue(c.getCellState() == CellState.UNDISCOVERED);
        }

        // Now double click on the open cell.
        openReturn = this.minefield.doubleClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.WAS_MINE);

        // Check if the game is over
        Assert.assertTrue(this.minefield.gameOver());

        //Check if the update list is greater equal to count
        final Set<ICell> updatedCells = minefield.getUpdateCells();
        Assert.assertEquals(this.minefield.getRows()*this.minefield.getColumns(), updatedCells.size());
    }

    @Test
    public void NeighbourhoodAndCellIsAlreadyOpenTest() {
        /**
         * Double click on a cell which is opened. All cells in the neighbourhood are also opened.
         * Expected out come:
         * - cell is still open.
         * - game is not over
         * - the moore neighbourhood is still opened.
         * - the update list is 0.
         * - the open return value is IS_ALREADY_OPEN.
         */
        // Get a cell which is not a mine nor empty.
        final Cell cell = getCell(CellContent.EMPTY);
        Assert.assertFalse(cell == null);
        Assert.assertTrue(cell.getCellState() == CellState.UNDISCOVERED);
        Assert.assertFalse(cell.getCellContent() == CellContent.EMPTY);

        //Open the cell
        IMinefield.OpenReturn openReturn = this.minefield.singleClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.OPEN);
        Assert.assertTrue(cell.getCellState() == CellState.OPEN);

        // All neighbours should be now opened
        final Set<Cell> neighbourhood = cell.getNeighbours();
        Assert.assertFalse(neighbourhood == null);
        for(final Cell c : neighbourhood) {
            Assert.assertTrue(c.getCellState() == CellState.OPEN);
        }

        // Now double click on the open cell.
        openReturn = this.minefield.doubleClick(cell.getPosition());
        Assert.assertTrue(openReturn == IMinefield.OpenReturn.IS_ALREADY_OPEN);

        // Check if the game is not over
        Assert.assertFalse(this.minefield.gameOver());

        for(final Cell c : neighbourhood) {
            Assert.assertTrue(c.getCellState() == CellState.OPEN);
        }

        //Check if the update list is greater equal to count
        final Set<ICell> updatedCells = minefield.getUpdateCells();
        Assert.assertEquals(0, updatedCells.size());
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

    private Cell getCell(final CellState state, final CellContent[] notContent, final CellContent notNeighbourhoodContent) {
        final Map<ICellPosition, Cell> field = minefield.getOriginalField();

        for(final Cell c : field.values()) {
            if(c.getCellState() == state && notContent(c.getContent(), notContent)) {

                final List<Cell> neighbours = minefield.getMooreNeighbourhood(c.getPosition());

                boolean valid = true;
                for(final Cell n : neighbours) {

                    valid &= n.getContent() != notNeighbourhoodContent;

                }

                if(valid) return c;
            }
        }

        return null;
    }

    private Cell getCell(final CellContent content) {
        final Map<ICellPosition, Cell> field = minefield.getOriginalField();

        for(final Cell c : field.values()) {
            if(c.getContent() == content) return c;
        }

        return null;
    }

    private boolean notContent(final CellContent content, final CellContent... notContent) {
        boolean valid = true;

        for(final CellContent c : notContent) {
            valid &= c != content;
        }

        return valid;
    }
}
