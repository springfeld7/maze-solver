package io.github.springfeld7.mazesolver.model.mazesolver;

import io.github.springfeld7.mazesolver.model.maze.Maze;
import io.github.springfeld7.mazesolver.model.maze.MazeCell;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for classes responsible for solving mazes represented by {@code Maze}.
 * @author Thomas Springfeldt
 */
public abstract class MazeSolver {

    private final Maze maze;
    private final Direction[] directions;
    private final List<Point> expansionOrder;

    /**
     * Protected construction for subclasses. Initializes the maze to be solved.
     * @param maze The maze to be solved.
     * @param directions The direction set used in the search.
     */
    protected MazeSolver(Maze maze, Direction[] directions) {

        this.maze = maze;
        this.directions = directions;
        this.expansionOrder = new ArrayList<>();
    }

    /**
     * Solves the maze and returns the reconstructed path from start to finish.
     * @return A list of Points representing the solution path, or null if no solution found.
     */
    public abstract List<Point> solve();

    /**
     * Returns a list of valid neighboring points from the given position, based on the allowed directions.
     * @param node The current position in the maze.
     * @return A list of neighboring {@code Point} objects that are valid to move to.
     */
    protected List<Point> expand(Point node) {

        List<Point> childNodes = new ArrayList<>();
        MazeCell[][] mazeGrid = this.maze.getMazeGrid();

        for (Direction dir : directions) {

            int newRow = node.y + dir.dRow;
            int newCol = node.x + dir.dCol;

            // Check if new position is within maze boundaries
            if (newRow < 0 || newRow >= mazeGrid.length || newCol < 0 || newCol >= mazeGrid[0].length) {
                continue;
            }

            boolean isDiagonal = dir.dRow != 0 && dir.dCol != 0;

            // Check that both adjacent cells of the diagonal moves are traversable
            if (isDiagonal) {

                // If either isn't walkable: continue without adding
                if (!mazeGrid[node.y + dir.dRow][node.x].isPath()) {
                    continue;
                }
                if (!mazeGrid[node.y][node.x + dir.dCol].isPath()) {
                    continue;
                }
            }

            if (mazeGrid[newRow][newCol].isPath()) {
                childNodes.add(new Point(newCol, newRow));
            }
        }
        return childNodes;
    }

    /**
     * Builds the solution path by following parent links from the goal node to the start,
     * then reversing the resulting list to return the path from start to goal.
     * @param goalNode The final node (goal).
     * @return The solution path from start to goal.
     */
    protected List<Point> buildSolutionPath(SearchNode goalNode) {

        List<Point> solutionPath = new ArrayList<>();
        solutionPath.add(goalNode.getPosition());

        SearchNode current = goalNode.getParent();

        while (current != null) {

            solutionPath.add(current.getPosition());

            current = current.getParent();
        }

        Collections.reverse(solutionPath);

        return solutionPath;
    }

    /**
     * Accessor for maze.
     * @return The maze instance.
     */
    public Maze getMaze() { return maze; }

    /**
     * Accessor for expansionOrder.
     * @return The list of positions in the order of expansion.
     */
    public List<Point> getExpansionOrder() { return expansionOrder; }

    /**
     * Adds a point to the list of expanded nodes.
     * @param position The position to add.
     */
    protected void addToExpansionOrder(Point position) { expansionOrder.add(position); }

    /**
     * Returns the movement cost from start point to destination point.
     * Orthogonal moves cost 1.0, diagonal moves cost sqrt(2).
     * @param start The starting point.
     * @param destination The destination point.
     * @return The cost of moving from current node to child.
     */
    protected double calculateMovementCost(Point start, Point destination) {

        int dx = Math.abs(destination.x - start.x);
        int dy = Math.abs(destination.y - start.y);

        return (dx + dy == 1) ? 1.0 : Math.sqrt(2);
    }

    /**
     * Returns a string representation of the maze solver.
     * @return a string in the format: solverName (movement model).
     */
    @Override
    public String toString() {

        String solverName = this.getClass().getSimpleName().replace("Solver", "");

        String movementModel = (directions == Direction.ORTHOGONAL)
                ? "Orthogonal"
                : "Diagonals allowed";

        return solverName + " (" + movementModel + ")";
    }
}
