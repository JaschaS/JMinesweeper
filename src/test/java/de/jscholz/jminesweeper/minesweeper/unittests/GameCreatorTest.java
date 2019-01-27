package de.jscholz.jminesweeper.minesweeper.unittests;

import de.jscholz.jminesweeper.minesweeper.GameCreator;
import de.jscholz.jminesweeper.minesweeper.IMinefield;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class GameCreatorTest {


    @ParameterizedTest(name = "[{index}] create {0}, expects mines={2}, rows={3}, cols={4}")
    @MethodSource("minefieldProvider")
    public void createGameTest(final String testName,
                               final IMinefield field,
                               final int expectedMines,
                               final int expectedRows,
                               final int expectedCols) {

        final int mines = field.getTotalMines();
        final int rows = field.getRows();
        final int cols = field.getColumns();

        Assert.assertEquals(mines, field.getTotalMines());
        Assert.assertEquals(rows, field.getRows());
        Assert.assertEquals(cols, field.getColumns());

    }

    private static Stream<Arguments> minefieldProvider() {
        return Stream.of(
                Arguments.of("Beginner-Game", GameCreator.createBeginnerGame(), 10, 8, 8)
        );
    }

}
