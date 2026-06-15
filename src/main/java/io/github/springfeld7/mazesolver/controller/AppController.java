package io.github.springfeld7.mazesolver.controller;

import io.github.springfeld7.mazesolver.model.maze.CellState;
import io.github.springfeld7.mazesolver.model.maze.Maze;
import io.github.springfeld7.mazesolver.model.maze.MazeCell;
import io.github.springfeld7.mazesolver.view.AppView;
import io.github.springfeld7.mazesolver.model.mazesolver.MazeSolver;

import javax.swing.SwingUtilities;
import java.awt.Point;
import java.util.List;

import static io.github.springfeld7.mazesolver.support.AppConfig.ANIMATION_STEP_DELAY;

/**
 * Main controller of the maze solver app.
 * @author Thomas Springfeldt
 */
public class AppController {

    AppView appView;
    MazeSolver mazeSolver;
    Maze maze;

    /**
     * Construction initializes private members and starts the maze solver.
     * @param mazeSolver The solver used for solving the maze.
     */
    public AppController(MazeSolver mazeSolver) {

        this.mazeSolver = mazeSolver;
        this.maze = mazeSolver.getMaze();

        CellState[][] cellStates = initializeCellStates();

        appView = new AppView(cellStates);

        new Thread(() -> {

            List<Point> solutionPath = mazeSolver.solve();
            List<Point> expanded = mazeSolver.getExpansionOrder();

            visualizeSearch(expanded, solutionPath);
            printSolutionActionSequence(solutionPath);
            printSolutionInfo(expanded, solutionPath);

        }).start();
    }

    /**
     * Initializes cellStates grid to reflect the unexplored maze.
     * @return The initialized CellState grid.
     */
    private CellState[][] initializeCellStates() {

        CellState[][] cellStates;

        MazeCell[][] grid = mazeSolver.getMaze().getMazeGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        cellStates = new CellState[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cellStates[row][col] = grid[row][col].isPath() ? CellState.PATH : CellState.WALL;
            }
        }

        Point start = maze.getStart();
        Point finish = maze.getFinish();

        cellStates[start.y][start.x] = CellState.START;
        cellStates[finish.y][finish.x] = CellState.FINISH;

        return cellStates;
    }

    /**
     * Visually animates the maze solving process by updating the UI with each expanded node
     * and solution cell in sequence. The expanded nodes are painted first, followed by
     * the final solution path.
     * @param expanded A list of {@link Point}s representing the order in which nodes were expanded.
     * @param solutionPath A list of {@link Point}s representing the final path from start to goal.
     */
    private void visualizeSearch(List<Point> expanded, List<Point> solutionPath) {

        // Animate node expansion
        for (Point node : expanded) {

            appView.getMazePanel().updateCellState(CellState.EXPANDED, node.y, node.x);
            SwingUtilities.invokeLater(() -> appView.getMazePanel().repaint());

            try {
                Thread.sleep(ANIMATION_STEP_DELAY);
            } catch (InterruptedException e) {
                System.out.println("Search visualization interrupted: " + e.getMessage());
            }
        }

        // Animate solution path
        if (solutionPath != null) {

            for (Point node : solutionPath) {

                appView.getMazePanel().updateCellState(CellState.SOLUTION, node.y, node.x);
                SwingUtilities.invokeLater(() -> appView.getMazePanel().repaint());

                try {
                    Thread.sleep(ANIMATION_STEP_DELAY);
                } catch (InterruptedException e) {
                    System.out.println("Solution visualization interrupted: " + e.getMessage());
                }
            }
        } else {
            System.out.println("No solution path was found.");
        }
    }

    /**
     * Prints the point to point movement of the solution path.
     * @param path The ordered list of points in the solution path.
     */
    private void printSolutionActionSequence(List<Point> path) {

        System.out.println("Action sequence of solution path:");

        for (int i = 1; i < path.size(); i++) {

            Point from = path.get(i - 1);
            Point to = path.get(i);
            System.out.printf("[%d, %d] -> [%d, %d]%n", from.x, from.y, to.x, to.y);
        }
    }

    /**
     * Prints information about the solution path and search process.
     * @param expanded A list of nodes (points) that were expanded during the search.
     * @param solutionPath The final solution path from start to goal, represented as a list of points.
     */
    private void printSolutionInfo(List<Point> expanded, List<Point> solutionPath) {

        int pathDepth = solutionPath.size() - 1;
        double pathCost = calculatePathCost(solutionPath);
        int mazeTotalCells = maze.getMazeGrid().length * maze.getMazeGrid()[0].length;
        int expNodes = expanded.size();

        System.out.println(mazeSolver.toString());
        System.out.printf("Total cells: %d - Expanded: %d - Path depth: %d - Path cost: %.2f", mazeTotalCells, expNodes, pathDepth, pathCost);
    }

    /**
     * Calculates the total path cost of a solution.
     * @param solutionPath List of points representing the solution path.
     * @return The total path cost.
     */
    private double calculatePathCost(List<Point> solutionPath) {

        double pathCost = 0.0;

        for (int i = 1; i < solutionPath.size(); i++) {

            Point prev = solutionPath.get(i - 1);
            Point curr = solutionPath.get(i);

            int dx = Math.abs(curr.x - prev.x);
            int dy = Math.abs(curr.y - prev.y);

            if (dx == 1 && dy == 1) { // Check if step is diagonal
                pathCost += Math.sqrt(2);

            } else if ((dx == 1 && dy == 0) || (dx == 0 && dy == 1)) { // Check if step is orthogonal
                pathCost += 1;
            }
        }
        return pathCost;
    }
}
