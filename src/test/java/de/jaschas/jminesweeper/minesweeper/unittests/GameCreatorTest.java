package de.jaschas.jminesweeper.minesweeper.unittests;

import de.jaschas.jminesweeper.minesweeper.Difficulty;
import de.jaschas.jminesweeper.minesweeper.GameCreator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GameCreatorTest {

    @Test
    public void GameCreator_SetDifficultyLeveL_IsEasyWhenSetToEasy() {
        GameCreator.setDifficulty(Difficulty.EASY);

        Assertions.assertEquals(Difficulty.EASY, GameCreator.getCurrentDifficulty());
    }

    @Test
    public void GameCreator_SetDifficultyLeveL_IsExperiencedWhenSetToExperienced() {
        GameCreator.setDifficulty(Difficulty.EXPERIENCED);

        Assertions.assertEquals(Difficulty.EXPERIENCED, GameCreator.getCurrentDifficulty());
    }

    @Test
    public void GameCreator_SetDifficultyLeveL_IsExpertWhenSetToExpert() {
        GameCreator.setDifficulty(Difficulty.EXPERT);

        Assertions.assertEquals(Difficulty.EXPERT, GameCreator.getCurrentDifficulty());
    }

    @Test
    public void GameCreator_SetDifficultyLeveL_IsCustomWhenSetToCustom() {
        GameCreator.setDifficulty(Difficulty.CUSTOM);

        Assertions.assertEquals(Difficulty.CUSTOM, GameCreator.getCurrentDifficulty());
    }
}
