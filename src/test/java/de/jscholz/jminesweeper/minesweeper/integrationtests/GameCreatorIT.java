package de.jscholz.jminesweeper.minesweeper.integrationtests;

import de.jscholz.jminesweeper.minesweeper.Difficulty;
import de.jscholz.jminesweeper.minesweeper.GameCreator;
import de.jscholz.jminesweeper.minesweeper.IMinefield;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class GameCreatorIT {


    @ParameterizedTest(name = "[{index}] create {0}, expects mines={1}, rows={2}, cols={3}")
    @MethodSource("defaultGameProvider")
    public void createDefaultGameTest(final Difficulty difficulty,
                               final int expectedRows,
                               final int expectedCols,
                               final int expectedMines) {

        GameCreator.setDifficulty(difficulty);

        final Difficulty currentDifficulty = GameCreator.getCurrentDifficulty();

        assertThat(currentDifficulty)
                .as("Difficult level should be equal to %s", difficulty)
                .isEqualTo(difficulty);

        final IMinefield minefield = GameCreator.createGame();

        assertThat(minefield)
                .as("Minefield shouldn't be null")
                .isNotNull();

        assertThat(minefield.getRows())
                .as("Minefield rows should be equal to %s", expectedRows)
                .isEqualTo(expectedRows);

        assertThat(minefield.getColumns())
                .as("Minefield columns should be equal to %s", expectedCols)
                .isEqualTo(expectedCols);

        assertThat(minefield.getTotalMines())
                .as("Minefield should contain %s mines", expectedMines)
                .isEqualTo(expectedMines);
    }


    @ParameterizedTest(name = "[{index}] create {0}, expects mines={1}, rows={2}, cols={3}")
    @MethodSource("customGameProvider")
    public void createCustomGameTest(final int rows,
                                final int cols,
                                final int minesPercent,
                                final int expectedRows,
                                final int expectedCols,
                                final int expectedMines) {

        //GameCreator.setDifficulty(rows, cols, minesPercent);
        final Difficulty currentDifficulty = GameCreator.getCurrentDifficulty();

        assertThat(currentDifficulty)
                .as("Difficult level should be equal to %s", Difficulty.CUSTOM)
                .isEqualTo(Difficulty.CUSTOM);

        final IMinefield minefield = GameCreator.createGame();

        assertThat(minefield)
                .as("Minefield shouldn't be null")
                .isNotNull();

        assertThat(minefield.getRows())
                .as("Minefield rows should be equal to %s", expectedRows)
                .isEqualTo(expectedRows);

        assertThat(minefield.getColumns())
                .as("Minefield columns should be equal to %s", expectedCols)
                .isEqualTo(expectedCols);

        assertThat(minefield.getTotalMines())
                .as("Minefield should contain %s mines", expectedMines)
                .isEqualTo(expectedMines);
    }

    private static Stream<Arguments> customGameProvider() {
        return Stream.of(
                Arguments.of(8, 8, 16, 8, 8, 10),   // min
                Arguments.of(7, 8, 16, 8, 8, 10),   // above min
                Arguments.of(8, 7, 16, 8, 8, 10),   // above min
                Arguments.of(8, 8, 10, 8, 8, 10)    // above min
        );
    }

    private static Stream<Arguments> defaultGameProvider() {
        return Stream.of(
                Arguments.of(Difficulty.EASY, 8, 8, 10),
                Arguments.of(Difficulty.EXPERIENCED, 16, 16, 40),
                Arguments.of(Difficulty.EXPERT, 30, 16, 100)
        );
    }

}
