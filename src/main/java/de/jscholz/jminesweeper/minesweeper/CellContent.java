package de.jscholz.jminesweeper.minesweeper;

/**
 * <p>The enum which represents the cell content. Each value has a pointer to the next enum value and a number.</p>
 * <p>
 * The number defines how many mines a cell has in the moore-neighbourhood. The value is between 0 and 8.
 * </p>
 * <ul>
 *     <li>0, when the cell has no mines in the moore-neighbourhood.</li>
 *     <li>And 8, when the moore-neighbourhood is full of mines.</li>
 *     <li>If the cell content is unknown, this value is -1. Also when the content of a cell is mine.</li>
 * </ul>
 *
 *
 * <p>
 * The pointer points to the next number of mines inside the moore-neighbourhood. For instance, the value <i>EMPTY</i>
 * points to the value <i>ONE</i> because from 0 mines the next value is 1 mine in the neighbourhood. Note that the
 * values <i>UNKNOWN</i>, <i>MINE</i> and <i>EIGHT</i> do not point to an enum value.
 * </p>
 */
public enum CellContent {
    /**
     * Shows that the content of the cell is unknown. This will be set if the status of the cell is <i>UNDISCOVERED</i>.
     * The mines in the moore-neighbourhood is -1 and the enum doesn't point to an other enum value.
     */
    UNKNOWN(-2),
    /**
     * Shows that the cell contains a mine. The mines in the moore-neighbourhood is -1 and it doesn't point to an
     * other enum value.
     */
    MINE(-1),
    /**
     * Is set when a cell has 8 mines in the moore-neighbourhood. It doesn't point to an other enum value because it
     * is already the maximum amount of mines in the moore-neighbourhood.
     */
    EIGHT(8),
    /**
     * Is set when a cell has 7 mines in the moore-neighbourhood. It points to the enum value <i>EIGHT</i>.
     */
    SEVEN(7, EIGHT),
    /**
     * Is set when a cell has 6 mines in the moore-neighbourhood. It points to the enum value <i>SEVEN</i>.
     */
    SIX(6, SEVEN),
    /**
     * Is set when a cell has 5 mines in the moore-neighbourhood. It points to the enum value <i>SIX</i>.
     */
    FIVE(5, SIX),
    /**
     * Is set when a cell has 4 mines in the moore-neighbourhood. It points to the enum value <i>FIVE</i>.
     */
    FOUR(4, FIVE),
    /**
     * Is set when a cell has 3 mines in the moore-neighbourhood. It points to the enum value <i>FOUR</i>.
     */
    THREE(3, FOUR),
    /**
     * Is set when a cell has 2 mines in the moore-neighbourhood. It points to the enum value <i>THREE</i>.
     */
    TWO(2, THREE),
    /**
     * Is set when a cell has 1 mines in the moore-neighbourhood. It points to the enum value <i>TWO</i>.
     */
    ONE(1, TWO),
    /**
     * Is set when a cell has 0 mines in the moore-neighbourhood. It points to the enum value <i>ONE</i>.
     */
    EMPTY(0, ONE);

    private final CellContent next;
    private final int minesInNeighbourhood;

    /**
     * Custom-Ctor creates an enum value with the given amount of mines in the moore-neighbourhood.
     * @param minesInNeighbourhood Mines in the moore-neighbourhood of the cell.
     */
    CellContent(final int minesInNeighbourhood) {
        this(minesInNeighbourhood, null);
    }

    /**
     * Custom-Ctor creates an enum value with the given amount of mines in the moore-neighbourhood and a pointer to
     * the next enum-value.
     * @param minesInNeighbourhood Mines in the moroe-neighbourhood of the cell.
     * @param next The pointer to the next enum value.
     */
    CellContent(final int minesInNeighbourhood, final CellContent next) {
        this.next = next;
        this.minesInNeighbourhood = minesInNeighbourhood;
    }

    /**
     * Returns the pointer to the next enum value.
     * @return The pointer to the next enum value. Null if there is no pointer.
     */
    CellContent getNext() {
        return next;
    }

    /**
     * Returns the amount of mines in the moore-neighbourhood. If the content is a mine or is unknown a -1 will be
     * returned
     * @return The amount of mines in the moore-neighbourhood or -1 when the content is a mine or unknown.
     */
    int getMinesInNeighbourhood() {
        return this.minesInNeighbourhood;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
