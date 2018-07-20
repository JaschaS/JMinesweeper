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
        Assert.assertEquals(IMinefield.OpenReturn.OPEN, openReturn);

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


    // TODO game cleared when double click
    @Test
    public void clearGameWithDoubleClick() {
        /**
         * Flag all cell which are mines.
         * Then open all cells which are empty with single clicks.
         * Then open the rest of the cells with double click.
         * Expects:
         * - The game is over
         * - The amount of free cells is 0
         * - The double click returns game is already over when clicking again
         * - the last double click returns game cleared
         * - The update list of cells should contain now all cells.
         */
        // Flag all mines
        final List<Cell> allMines = getAllCells(CellContent.MINE);
        Assert.assertFalse(allMines == null);
        Assert.assertFalse(allMines.size() == 0);
        Assert.assertEquals(10, allMines.size());

        for(final Cell cell : allMines) {
            final ICellPosition position = cell.getPosition();
            final IMinefield.OpenReturn openReturn = this.minefield.secondaryClick(position);
            Assert.assertEquals(IMinefield.OpenReturn.NOW_FLAGGED, openReturn);
        }

        // Open all empty cells.
        Cell cell = getCell(CellContent.EMPTY, CellState.UNDISCOVERED);
        Assert.assertFalse(cell == null);
        while (cell != null){
            final ICellPosition position = cell.getPosition();
            final IMinefield.OpenReturn openReturn = this.minefield.singleClick(position);
            Assert.assertEquals(IMinefield.OpenReturn.OPEN, openReturn);

            cell = getCell(CellContent.EMPTY, CellState.UNDISCOVERED);
        }

        // Open the rest.
        final CellContent[] contentsNotAllowed = new CellContent[]{CellContent.MINE, CellContent.EMPTY};
        cell = getCell(contentsNotAllowed, CellState.UNDISCOVERED);
        Assert.assertFalse(cell == null);
        while (cell != null){
            final ICellPosition position = cell.getPosition();
            final IMinefield.OpenReturn openReturn = this.minefield.doubleClick(position);
            if(this.minefield.getFreeCellsLeft() == 0) {
                Assert.assertTrue(this.minefield.getFreeCellsLeft() + "", this.minefield.getFreeCellsLeft() == 0);
                Assert.assertEquals(IMinefield.OpenReturn.GAME_CLEARED, openReturn);
            }
            else {
                Assert.assertTrue(this.minefield.getFreeCellsLeft() + "", this.minefield.getFreeCellsLeft() > 0);
            }

            cell = getCell(contentsNotAllowed, CellState.UNDISCOVERED);
        }

        Assert.assertEquals(0, this.minefield.getFreeCellsLeft());
        Assert.assertTrue(this.minefield.gameOver());

        final Set<ICell> updatedCells = this.minefield.getUpdateCells();
        Assert.assertEquals(this.minefield.getRows() * this.minefield.getColumns(), updatedCells.size());

        final IMinefield.OpenReturn openReturn = this.minefield.doubleClick(new CellPosition(0, 0));
        Assert.assertEquals(IMinefield.OpenReturn.GAME_IS_ALREADY_OVER, openReturn);
    }

    @Test
    public void emptyCellOpensOthers() {
        /**
         * If a cell in the neighbourhood which has the content empty is opened by a
         * double click, all neighbours of the empty cell should opened as well.
         * Find a cell which has an empty cell as neighbour. then double click on it.
         * Flag all cells, so that the game is not over and all cells are in update cells.
         * expect:
         * - game is not over
         * - the neighbours of the empty cell are all opened.
         * - the neighbours of the empty cell are in the updated cell list.
         */
        // Flag all mines
        final List<Cell> allMines = getAllCells(CellContent.MINE);
        Assert.assertFalse(allMines == null);
        Assert.assertFalse(allMines.size() == 0);
        Assert.assertEquals(10, allMines.size());

        for(final Cell cell : allMines) {
            final ICellPosition position = cell.getPosition();
            final IMinefield.OpenReturn openReturn = this.minefield.secondaryClick(position);
            Assert.assertEquals(IMinefield.OpenReturn.NOW_FLAGGED, openReturn);
        }

        // Get Cell with neighbour empty.
        final Cell cell = getCell(CellState.UNDISCOVERED, CellContent.ONE, CellContent.EMPTY);
        Assert.assertFalse(cell == null);
        Assert.assertEquals(CellContent.ONE, cell.getContent());
        Assert.assertEquals(CellState.UNDISCOVERED, cell.getCellState());

        final Set<Cell> neighbours = cell.getNeighbours();
        Assert.assertFalse(neighbours == null);
        Assert.assertFalse( neighbours.size() == 0);
        int empty = 0;

        // Make sure that at least one neighbour is empty
        for(final Cell n : neighbours) {
            if(n.getContent() == CellContent.EMPTY) {
                ++empty;

                // Make sure that the neighbours are not open already.
                final Set<Cell> emptyCellNeighbours = n.getNeighbours();
                for(final Cell k : emptyCellNeighbours) {
                    Assert.assertEquals(CellState.UNDISCOVERED, k.getCellState());
                }
            }
        }
        Assert.assertFalse(empty == 0);

        final IMinefield.OpenReturn openReturn = this.minefield.doubleClick(cell.getPosition());
        Assert.assertNotEquals(IMinefield.OpenReturn.WAS_MINE, openReturn);
        Assert.assertEquals(IMinefield.OpenReturn.OPEN, openReturn);
        Assert.assertFalse(this.minefield.gameOver());

        final Set<ICell> updateCells = this.minefield.getUpdateCells();

        // Now check if the neighbours of the empty cell are already open.
        // And if they are contained in the update cell set.
        for(final Cell n : neighbours) {
            if(n.getContent() == CellContent.EMPTY) {
                final Set<Cell> emptyCellNeighbours = n.getNeighbours();
                for(final Cell k : emptyCellNeighbours) {
                    Assert.assertEquals(CellState.OPEN, k.getCellState());
                    Assert.assertTrue(updateCells.contains(k));
                }
                Assert.assertTrue(updateCells.contains(n));
            }
        }
    }

    // TODO All cell sind offen außer einer ist eine Flagge - die eine mine. Hier wird aktuell immer is open returned

    private Cell getCell(final CellState state,  final CellContent content, final CellContent neighbourContent) {
        final Map<ICellPosition, Cell> field = minefield.getOriginalField();

        for(final Cell c : field.values()) {
            if(c.getContent() == content && c.getCellState() == state) {
                final Set<Cell> neighbours = c.getNeighbours();

                for(final Cell n : neighbours) {
                    if(n.getContent() == neighbourContent) return c;
                }
            }
        }

        return null;
    }

    private List<Cell> getAllCells(final CellContent content) {

        final ArrayList<Cell> cells = new ArrayList<>();
        final Map<ICellPosition, Cell> field = minefield.getOriginalField();

        for(final Cell c : field.values()) {
            if(c.getContent() == content) cells.add(c);
        }

        return cells;
    }

    private Cell getCell(final CellContent[] contentNotAllowed, final CellState state) {
        final Map<ICellPosition, Cell> field = minefield.getOriginalField();

        for(final Cell c : field.values()) {
            if(notContent(c.getContent(), contentNotAllowed) && c.getCellState() == state) {
                return c;
            }
        }

        return null;
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

    private Cell getCell(final CellContent content, final CellState state) {
        final Map<ICellPosition, Cell> field = minefield.getOriginalField();

        for(final Cell c : field.values()) {
            if(c.getContent() == content && c.getCellState() == state) return c;
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
