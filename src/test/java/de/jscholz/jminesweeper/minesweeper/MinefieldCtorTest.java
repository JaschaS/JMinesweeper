package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MinefieldCtorTest {

    @Test
    public void minesNumberTest() {
        final Minefield field = (Minefield) GameCreator.createBeginnerGame();
        final Map<ICellPosition, Cell> f = field.getOriginalField();

        int amount = 10;
        for(final ICell c : f.values()) {
            if(c.getContent() == ICell.CellContent.MINE) --amount;
        }

        Assert.assertEquals(0, amount);
    }

    @Test
    public void checkNeighbourNumberTest() {
        final IMinefield field = GameCreator.createBeginnerGame();
        final Map<ICellPosition, ICell> f = field.getField();

        for(ICell c : f.values()) {
            final ICell.CellContent content = c.getContent();

            /*
             * Check the mine only, when the content is a number.
             */
            if(content != ICell.CellContent.MINE && content != ICell.CellContent.EMPTY) {
                final int number = content.getNumber();
                final Set<Cell> neighbours = ((Cell) c).getNeighbours();

                /*
                 * Check the amount of numbers in the neighbourhood.
                 */
                int amountOfMinesInNeighbourhood = 0;
                for(final Cell nc : neighbours) {
                    boolean isMine = nc.getContent() == ICell.CellContent.MINE;
                    if (isMine) ++amountOfMinesInNeighbourhood;
                }

                Assert.assertTrue(amountOfMinesInNeighbourhood == number);
            }
        }
    }
}
