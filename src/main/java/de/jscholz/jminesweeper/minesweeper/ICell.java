package de.jscholz.jminesweeper.minesweeper;

/**
 * The interface <i>ICell</i> represents the cells of the game.
 */
public interface ICell {

    /**
     * Returns the position of the cell.
     * @return position of the cell.
     */
    ICellPosition getPosition();

    /**
     * Returns the content of the cell.
     * @return content of the cell.
     */
    CellContent getContent();

    /**
     * Returns the current state of the cell.
     * @return state of the cell.
     */
    CellState getCellState();

    /**
     * The enum which represents the cell content.
     */
    enum CellContent {
        MINE(10),
        EIGHT(8),
        SEVEN(7, EIGHT),
        SIX(6, SEVEN),
        FIVE(5, SIX),
        FOUR(4, FIVE),
        THREE(3, FOUR),
        TWO(2, THREE),
        ONE(1, TWO),
        EMPTY(9, ONE);

        private final CellContent next;
        private final int number;

        CellContent() {
            this(-1,null);
        }

        CellContent(final int number) {
            this(number,null);
        }

        CellContent(final int number, final CellContent next) {
            this.next = next;
            this.number = number;
        }

        CellContent getNext() {
            return next;
        }

        int getNumber() {
            return this.number;
        }
    }

    /**
     * The enum which represents the cell state.
     */
    enum CellState {
        OPEN,
        UNDISCOVERED,
        FLAGGED
    }
}
