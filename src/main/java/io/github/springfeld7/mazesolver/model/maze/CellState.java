package io.github.springfeld7.mazesolver.model.maze;

/**
 * Represents the state of a cell for visualization purposes
 * during maze solving.
 * @author Thomas Springfeldt
 */
public enum CellState {

    /**
     * A wall cell (not traversable).
     */
    WALL,

    /**
     * A normal path cell (traversable but not yet visited).
     */
    PATH,

    /**
     * The starting cell of the maze.
     */
    START,

    /**
     * The finishing cell of the maze.
     */
    FINISH,

    /**
     * A cell that has been expanded during the search.
     */
    EXPANDED,

    /**
     * A cell that is part of the final solution path.
     */
    SOLUTION
}
