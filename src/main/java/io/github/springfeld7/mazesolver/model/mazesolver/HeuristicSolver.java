package io.github.springfeld7.mazesolver.model.mazesolver;

import io.github.springfeld7.mazesolver.model.heuristic.Heuristic;
import io.github.springfeld7.mazesolver.model.maze.Maze;

/**
 * Base class for heuristic maze solvers.
 * @author Thomas Springfeldt
 */
abstract class HeuristicSolver extends MazeSolver {

    private final Heuristic heuristic;

    /**
     * Protected constructor for subclasses. Initializes the maze solver with
     * the maze, allowed movement directions, and the heuristic to be used.
     * @param maze The {@link Maze} instance representing the maze to solve.
     * @param directions The set of {@link Direction} values that defines allowed movement.
     * @param heuristic The {@link Heuristic} implementation used to estimate costs during the search.
     */
    protected HeuristicSolver(Maze maze, Direction[] directions, Heuristic heuristic) {

        super(maze, directions);
        this.heuristic = heuristic;
    }

    /**
     * Accessor for the heuristic.
     * @return The heuristic.
     */
    protected Heuristic getHeuristic() { return heuristic; }
}
