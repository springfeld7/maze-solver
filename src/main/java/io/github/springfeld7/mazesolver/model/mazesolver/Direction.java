package io.github.springfeld7.mazesolver.model.mazesolver;

/**
 * Represents the possible directions for maze traversal.
 * @author Thomas Springfeldt
 */
public enum Direction {

    UP(-1, 0, 1.0),
    UP_RIGHT(-1, 1, Math.sqrt(2)),
    RIGHT(0, 1, 1.0),
    DOWN_RIGHT(1, 1, Math.sqrt(2)),
    DOWN(1, 0, 1.0),
    DOWN_LEFT(1, -1, Math.sqrt(2)),
    LEFT(0, -1, 1.0),
    UP_LEFT(-1, -1, Math.sqrt(2));

    public final int dRow;
    public final int dCol;
    public final double cost;

    Direction(int dRow, int dCol, double cost) {
        this.dRow = dRow;
        this.dCol = dCol;
        this.cost = cost;
    }

    /**
     * An array of the four cardinal directions: up, down, left, and right.
     */
    public static final Direction[] ORTHOGONAL = {UP, RIGHT, DOWN, LEFT};

    /**
     * An array of all eight directions, including diagonals.
     */
    public static final Direction[] ORTHOGONAL_AND_DIAGONAL = values();
}
