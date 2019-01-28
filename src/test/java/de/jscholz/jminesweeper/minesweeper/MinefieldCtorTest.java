package de.jscholz.jminesweeper.minesweeper;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

public class MinefieldCtorTest {

    @Test
    public void minesNumberTest() {
        final Minefield field = null; //(Minefield) GameCreator.createBeginnerGame();
        final Map<ICellPosition, Cell> f = field.getOriginalField();

        int amount = 10;
        for(final Cell c : f.values()) {
            if(c.getContent() == CellContent.MINE) --amount;
        }

        Assert.assertEquals(0, amount);
    }

    @Test
    public void checkNeighbourNumberTest() {
        final Minefield field = null; //(Minefield) GameCreator.createBeginnerGame();
        final Map<ICellPosition, Cell> f = field.getOriginalField();

        for(final Cell c : f.values()) {
            final CellContent content = c.getContent();

            /*
             * Check the mine only, when the content is a number.
             */
            if(content != CellContent.MINE && content != CellContent.EMPTY) {
                final int number = content.getMinesInNeighbourhood();
                final Set<Cell> neighbours = c.getNeighbours();

                /*
                 * Check the amount of numbers in the neighbourhood.
                 */
                int amountOfMinesInNeighbourhood = 0;
                for(final Cell nc : neighbours) {
                    boolean isMine = nc.getContent() == CellContent.MINE;
                    if (isMine) ++amountOfMinesInNeighbourhood;
                }

                Assert.assertTrue(amountOfMinesInNeighbourhood == number);
            }
        }
    }
}
