package de.jscholz.jminesweeper;

import de.jscholz.jminesweeper.minesweeper.*;

import java.util.*;

/**
 * This class should help to understand how to use the JMinesweeper framework.
 */
public class Application {
    public static void main(final String[] args) {

        // Get a minefield from the factory pattern.
        GameCreator.setGame(Difficulty.EASY);
        final IMinefield minefieldGame = GameCreator.createGame();

        // Get the visible field
        final Map<ICellPosition, ICell> field = minefieldGame.getFieldForVisualization();

        // Print the field to see how it looks.
        printField(field);

        // Flag some cells
        minefieldGame.secondaryClick(7, 7);

        // Get the updated list
        Set<ICell> updatedCells = minefieldGame.getUpdateCells();

        // Lets update the field
        updateField(field, updatedCells);

        // Print the field to see how it looks.
        printField(field);

        // Open a cell
        IMinefield.OpenReturn returnValue = minefieldGame.singleClick(0, 0);

        // Get the updated list
        updatedCells = minefieldGame.getUpdateCells();

        // Lets update the field
        updateField(field, updatedCells);

        // Print the field to see how it looks.
        printField(field);

        System.out.println("Game Over ? " + minefieldGame.gameOver());
    }

    private static void printField(final Map<ICellPosition, ICell> field) {

        final int rowBreak = 8;
        final StringBuilder builder = new StringBuilder();
        int rowsCount = 0;

        for (final ICellPosition position : field.keySet()) {
            final ICell cell = field.get(position);

            // Depend on the state print something.
            switch (cell.getCellState()) {
                case UNDISCOVERED:
                    builder.append("#");
                    break;
                case OPEN:

                    // Know check the content.
                    switch (cell.getCellContent()) {
                        case MINE:
                            builder.append("*");
                            break;
                        case EMPTY:
                            builder.append(".");
                            break;
                        case ONE:
                            builder.append("1");
                            break;
                        case TWO:
                            builder.append("2");
                            break;
                        case THREE:
                            builder.append("3");
                            break;
                        case FOUR:
                            builder.append("4");
                            break;
                        case FIVE:
                            builder.append("5");
                            break;
                        case SIX:
                            builder.append("6");
                            break;
                        case SEVEN:
                            builder.append("7");
                            break;
                        case EIGHT:
                            builder.append("8");
                            break;
                    }

                    break;
                case FLAGGED:
                    builder.append("F");
                    break;
            }

            ++rowsCount;
            if(rowsCount >= rowBreak) {
                builder.append("\n");
                rowsCount = 0;
            }
        }

        System.out.print(builder.toString());
        System.out.println();
    }

    private static void updateField(final Map<ICellPosition, ICell> field, final Set<ICell> updatedCells) {
        for(final ICell cell : updatedCells) {
            final ICellPosition position = cell.getPosition();
            assert field.containsKey(position) : "doesn't contain key";

            field.put(position, cell);
        }
    }
}
