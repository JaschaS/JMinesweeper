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

    /**
     * - Cell is Undiscovered
     *      - all neighbours are undiscovered
     *      - all neighbours are flagged
     *      - one neighbour is flagged
     *      - empty cell in neighbourhood
     *      - all neighbours are opened
     *      - neighbour is mine
     *      - neighbours are last undiscovered cells
     * - Cell is flagged
     *      - all neighbours are undiscovered
     *      - all neighbours are flagged
     *      - one neighbour is flagged
     *      - empty cell in neighbourhood
     *      - all neighbours are opened
     *      - neighbour is mine
     *      - neighbours are last undiscovered cells
     * - Cell is open
     *      - all neighbours are undiscovered
     *      - all neighbours are flagged
     *      - one neighbour is flagged
     *      - empty cell in neighbourhood
     *      - all neighbours are opened
     *      - neighbour is mine
     *      - neighbours are last undiscovered cells
     */

}
