package de.jaschas.jminesweeper.minesweeper.unittests;

import de.jaschas.jminesweeper.minesweeper.Difficulty;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DifficultSettingsTest {

    /**
     * EASY
     */
    @Test
    public void Easy_Has8Rows() {
        final int rows = Difficulty.EASY.getRows();
        Assertions.assertEquals(8, rows);
    }

    @Test
    public void Easy_Has8Columns() {
        final int cols = Difficulty.EASY.getColumns();
        Assertions.assertEquals(8, cols);
    }

    @Test
    public void Easy_Has16PercentMines() {
        final int minesPercent = Difficulty.EASY.getMinesPercent();
        Assertions.assertEquals(16, minesPercent);
    }

    @Test
    public void Easy_IsNotEditable() {
        final boolean isEditable = Difficulty.EASY.isEditable();
        Assertions.assertFalse(isEditable);
    }

    @Test
    public void Easy_SetRow_DoesNotChangeRow() {
        Difficulty.EASY.setRows(10);
        Easy_Has8Rows();
    }

    @Test
    public void Easy_SetColumns_DoesNotChangeColumns() {
        Difficulty.EASY.setColumns(10);
        Easy_Has8Columns();
    }

    @Test
    public void Easy_SetMinesPercent_DoesNotChangeMinesPercent() {
        Difficulty.EASY.setMinesPercent(99);
        Easy_Has16PercentMines();
    }

    /**
     * EXPERIENCED
     */
    @Test
    public void EXPERIENCED_Has16Rows() {
        final int rows = Difficulty.EXPERIENCED.getRows();
        Assertions.assertEquals(16, rows);
    }

    @Test
    public void EXPERIENCED_Has16Columns() {
        final int cols = Difficulty.EXPERIENCED.getColumns();
        Assertions.assertEquals(16, cols);
    }

    @Test
    public void EXPERIENCED_Has16PercentMines() {
        final int minesPercent = Difficulty.EXPERIENCED.getMinesPercent();
        Assertions.assertEquals(16, minesPercent);
    }

    @Test
    public void EXPERIENCED_IsNotEditable() {
        final boolean isEditable = Difficulty.EXPERIENCED.isEditable();
        Assertions.assertFalse(isEditable);
    }

    @Test
    public void EXPERIENCED_SetRow_DoesNotChangeRow() {
        Difficulty.EXPERIENCED.setRows(10);
        EXPERIENCED_Has16Rows();
    }

    @Test
    public void EXPERIENCED_SetColumns_DoesNotChangeColumns() {
        Difficulty.EXPERIENCED.setColumns(10);
        EXPERIENCED_Has16Columns();
    }

    @Test
    public void EXPERIENCED_SetMinesPercent_DoesNotChangeMinesPercent() {
        Difficulty.EXPERIENCED.setMinesPercent(99);
        EXPERIENCED_Has16PercentMines();
    }
    /**
     * EXPERT
     */
    @Test
    public void EXPERT_Has30Rows() {
        final int rows = Difficulty.EXPERT.getRows();
        Assertions.assertEquals(30, rows);
    }

    @Test
    public void EXPERT_Has16Columns() {
        final int cols = Difficulty.EXPERT.getColumns();
        Assertions.assertEquals(16, cols);
    }

    @Test
    public void EXPERT_Has21PercentMines() {
        final int minesPercent = Difficulty.EXPERT.getMinesPercent();
        Assertions.assertEquals(21, minesPercent);
    }

    @Test
    public void EXPERT_IsNotEditable() {
        final boolean isEditable = Difficulty.EXPERT.isEditable();
        Assertions.assertFalse(isEditable);
    }

    @Test
    public void EXPERT_SetRow_DoesNotChangeRow() {
        Difficulty.EXPERT.setRows(10);
        EXPERT_Has30Rows();
    }

    @Test
    public void EXPERT_SetColumns_DoesNotChangeColumns() {
        Difficulty.EXPERT.setColumns(10);
        EXPERT_Has16Columns();
    }

    @Test
    public void EXPERT_SetMinesPercent_DoesNotChangeMinesPercent() {
        Difficulty.EXPERT.setMinesPercent(99);
        EXPERT_Has21PercentMines();
    }

    /**
     * CUSTOM
     */
    @Test
    public void CUSTOM_IsEditable() {
        final boolean isEditable = Difficulty.CUSTOM.isEditable();
        Assertions.assertTrue(isEditable);
    }

    @Test
    public void CUSTOM_SetRow_DoesChangeRowToMin() {
        Difficulty.CUSTOM.setRows(Difficulty.MIN_ROWS);

        final int rows = Difficulty.CUSTOM.getRows();
        Assertions.assertEquals(Difficulty.MIN_ROWS, rows);
    }

    @Test
    public void CUSTOM_SetRow_DoesNotChangeRowBelowMin() {
        Difficulty.CUSTOM.setRows(Difficulty.MIN_ROWS - 1);

        final int rows = Difficulty.CUSTOM.getRows();
        Assertions.assertEquals(Difficulty.MIN_ROWS, rows);
    }

    @Test
    public void CUSTOM_SetRow_DoesChangeRowWhenAboveMin() {
        Difficulty.CUSTOM.setRows(Difficulty.MIN_ROWS + 1);

        final int rows = Difficulty.CUSTOM.getRows();
        Assertions.assertEquals(Difficulty.MIN_ROWS + 1, rows);
    }

    @Test
    public void CUSTOM_SetRow_DoesChangeRowToMax() {
        Difficulty.CUSTOM.setRows(Difficulty.MAX_ROWS);

        final int rows = Difficulty.CUSTOM.getRows();
        Assertions.assertEquals(Difficulty.MAX_ROWS, rows);
    }

    @Test
    public void CUSTOM_SetRow_DoesChangeRowWhenBelowMax() {
        Difficulty.CUSTOM.setRows(Difficulty.MAX_ROWS - 1);

        final int rows = Difficulty.CUSTOM.getRows();
        Assertions.assertEquals(Difficulty.MAX_ROWS - 1, rows);
    }

    @Test
    public void CUSTOM_SetRow_DoesNotChangeRowAboveMax() {
        Difficulty.CUSTOM.setRows(Difficulty.MAX_ROWS + 1);

        final int rows = Difficulty.CUSTOM.getRows();
        Assertions.assertEquals(Difficulty.MAX_ROWS, rows);
    }

    @Test
    public void CUSTOM_SetColumns_DoesChangeColumnsToMin() {
        Difficulty.CUSTOM.setColumns(Difficulty.MIN_COLUMNS);

        final int cols = Difficulty.CUSTOM.getColumns();
        Assertions.assertEquals(Difficulty.MIN_COLUMNS, cols);
    }

    @Test
    public void CUSTOM_SetColumns_DoesNotChangeColumnsBelowMin() {
        Difficulty.CUSTOM.setColumns(Difficulty.MIN_COLUMNS - 1);

        final int cols = Difficulty.CUSTOM.getColumns();
        Assertions.assertEquals(Difficulty.MIN_COLUMNS, cols);
    }

    @Test
    public void CUSTOM_SetColumns_DoesChangeColumnsWhenAboveMin() {
        Difficulty.CUSTOM.setColumns(Difficulty.MIN_COLUMNS + 1);

        final int cols = Difficulty.CUSTOM.getColumns();
        Assertions.assertEquals(Difficulty.MIN_COLUMNS + 1, cols);
    }

    @Test
    public void CUSTOM_SetColumns_DoesChangeColumnsToMax() {
        Difficulty.CUSTOM.setColumns(Difficulty.MAX_COLUMNS);

        final int cols = Difficulty.CUSTOM.getColumns();
        Assertions.assertEquals(Difficulty.MAX_COLUMNS, cols);
    }

    @Test
    public void CUSTOM_SetColumns_DoesChangeColumnsWhenBelowMax() {
        Difficulty.CUSTOM.setColumns(Difficulty.MAX_COLUMNS - 1);

        final int cols = Difficulty.CUSTOM.getColumns();
        Assertions.assertEquals(Difficulty.MAX_COLUMNS - 1, cols);
    }

    @Test
    public void CUSTOM_SetColumns_DoesNotChangeColumnsAboveMax() {
        Difficulty.CUSTOM.setColumns(Difficulty.MAX_COLUMNS + 1);

        final int cols = Difficulty.CUSTOM.getColumns();
        Assertions.assertEquals(Difficulty.MAX_COLUMNS, cols);
    }

    @Test
    public void CUSTOM_SetMinesPercent_DoesChangeMinesPercentToMin() {
        Difficulty.CUSTOM.setMinesPercent(Difficulty.MIN_MINES_PERCENT);

        final int minesPercent = Difficulty.CUSTOM.getMinesPercent();
        Assertions.assertEquals(Difficulty.MIN_MINES_PERCENT, minesPercent);
    }

    @Test
    public void CUSTOM_SetMinesPercent_DoesNotChangeMinesPercentBelowMin() {
        Difficulty.CUSTOM.setMinesPercent(Difficulty.MIN_MINES_PERCENT - 1);

        final int minesPercent = Difficulty.CUSTOM.getMinesPercent();
        Assertions.assertEquals(Difficulty.MIN_MINES_PERCENT, minesPercent);
    }

    @Test
    public void CUSTOM_SetMinesPercent_DoesChangeMinesPercentWhenAboveMin() {
        Difficulty.CUSTOM.setMinesPercent(Difficulty.MIN_MINES_PERCENT + 1);

        final int minesPercent = Difficulty.CUSTOM.getMinesPercent();
        Assertions.assertEquals(Difficulty.MIN_MINES_PERCENT + 1, minesPercent);
    }

    @Test
    public void CUSTOM_SetMinesPercent_DoesChangeMinesPercentToMax() {
        Difficulty.CUSTOM.setMinesPercent(Difficulty.MAX_MINES_PERCENT);

        final int minesPercent = Difficulty.CUSTOM.getMinesPercent();
        Assertions.assertEquals(Difficulty.MAX_MINES_PERCENT, minesPercent);
    }

    @Test
    public void CUSTOM_SetMinesPercent_DoesChangeMinesPercentWhenBelowMax() {
        Difficulty.CUSTOM.setMinesPercent(Difficulty.MAX_MINES_PERCENT - 1);

        final int minesPercent = Difficulty.CUSTOM.getMinesPercent();
        Assertions.assertEquals(Difficulty.MAX_MINES_PERCENT - 1, minesPercent);
    }

    @Test
    public void CUSTOM_SetMinesPercent_DoesNotChangeMinesPercentAboveMax() {
        Difficulty.CUSTOM.setMinesPercent(Difficulty.MAX_MINES_PERCENT + 1);

        final int minesPercent = Difficulty.CUSTOM.getMinesPercent();
        Assertions.assertEquals(Difficulty.MAX_MINES_PERCENT, minesPercent);
    }
}
