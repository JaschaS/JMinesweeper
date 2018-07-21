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

    

}
