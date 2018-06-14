package de.jscholz.jminesweeper.minesweeper;

import java.util.Map;

/**
 * <p>The implementation of the ICellPosition. It will represent the positions of cells within the minefield.</p>
 */
class CellPosition implements ICellPosition {

    private int x;
    private int y;

    /**
     * Default-Ctor of cell position. It creates a position with values x=0 and y=0.
     */
    public CellPosition() {
        this(0, 0);
    }

    /**
     * Custom-Ctor of cell position. Creates a position with the given values.
     * @param x the x position of the cell.
     * @param y the y position of the cell.
     */
    public CellPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy-Ctor copies the attributes from the given ICellPosition.
     * @param position the position which should be copied.
     */
    public CellPosition(final ICellPosition position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Sets the x value of the position.
     * @param x The new x position.
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * Sets the y value of the position.
     * @param y The new y position.
     */
    public void setY(final int y) {
        this.y = y;
    }

    @Override
    public boolean compare(final int x, final int y) {
        return  this.x == x && this.y == y;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj == null) return false;
        if ( !(obj instanceof ICellPosition) ) return false;
        if ( this == obj ) return true;

        return hashCode() == obj.hashCode();
    }

    @Override
    public int compareTo(final ICellPosition other) {

        final int otherY = other.getY();
        final int otherX = other.getX();

        // The other y position is greater than the y position, means the other comes after this object.
        if(otherY > y) return -1;

        // The other y position is smaler than the y position, means the other comes before this object.
        if(otherY < y) return 1;

        // The y's are the same. Compare the X values.
        // Both positions are the same.
        if(otherX == x) return 0;

        // Other x is greater than x, means it comes after this object.
        if(otherX > x) return -1;

        // Other X is smaler than x, means it comes after this object.
        return 1;
    }

    @Override
    public int hashCode() {

        //using Integer Spiral to generate the hashcode
        //from: https://stackoverflow.com/questions/9135759/java-hashcode-for-a-point-class

        int ax = Math.abs(x);
        int ay = Math.abs(y);
        if (ax>ay && x>0) return 4*x*x-3*x+y+1;
        if (ax>ay && x<=0) return 4*x*x-x-y+1;
        if (ax<=ay && y>0) return 4*y*y-y-x+1;
        return 4*y*y-3*y+x+1;
    }

    @Override
    public String toString() {
        return "[x: " + x + ", y: " + y + "]";
    }
}
