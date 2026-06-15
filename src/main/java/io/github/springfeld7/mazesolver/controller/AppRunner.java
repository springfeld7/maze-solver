package io.github.springfeld7.mazesolver.controller;

import io.github.springfeld7.mazesolver.model.heuristic.Heuristic;
import io.github.springfeld7.mazesolver.model.heuristic.ManhattanDistanceHeuristic;
import io.github.springfeld7.mazesolver.model.heuristic.OctileDistanceHeuristic;
import io.github.springfeld7.mazesolver.model.mazesolver.AStarSolver;
import io.github.springfeld7.mazesolver.model.mazesolver.BFSSolver;
import io.github.springfeld7.mazesolver.model.mazesolver.DFSSolver;
import io.github.springfeld7.mazesolver.model.mazesolver.Direction;
import io.github.springfeld7.mazesolver.model.maze.Maze;
import io.github.springfeld7.mazesolver.model.mazesolver.GreedyBestFirstSearchSolver;
import io.github.springfeld7.mazesolver.model.mazesolver.MazeSolver;
import io.github.springfeld7.mazesolver.model.mazesolver.UCSSolver;
import io.github.springfeld7.mazesolver.support.MazeLoader;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Responsible for parsing program arguments and initializing the maze-solving application.
 * @author Thomas Springfeldt
 */
public class AppRunner {

    private final String[] args;

    /**
     * Construction initializes the maze-solving application.
     * @param args Arguments passed to the program.
     */
    public AppRunner(String[] args) {
        this.args = args;
    }

    /**
     * Parses arguments to set up and pass solver to app controller.
     */
    public void runApp() throws IOException, URISyntaxException {

        if (args.length < 2) {
            throw new IllegalArgumentException("Arguments for maze url and solver type needs to be provided.");
        }

        // Parse arguments
        String mazeUrl = args[0];
        String solverType = args[1];
        boolean useDiagonals = (args.length >= 3) &&
                (args[2].equalsIgnoreCase("-d") || args[2].equalsIgnoreCase("--diagonal"));

        Maze maze = loadMaze(mazeUrl);

        // Pick movement model
        Direction[] directions = useDiagonals ? Direction.ORTHOGONAL_AND_DIAGONAL : Direction.ORTHOGONAL;

        // Create solver
        MazeSolver mazeSolver = createSolver(solverType, maze, directions);

        SwingUtilities.invokeLater(() ->
                new AppController(mazeSolver)
        );
    }

    /**
     * Builds the maze path and loads the maze from resources.
     * @param  mazeUrl The URL of the maze.
     */
    private Maze loadMaze(String mazeUrl) throws IOException {

        String path = mazeUrl.toLowerCase();

        if (!path.startsWith("mazes/")) {
            path = "mazes/" + path;
        }
        if (!path.endsWith(".png")) {
            path = path + ".png";
        }

        try {
            return MazeLoader.readMaze(path);
        } catch (IOException e) {
            throw new IOException("Error while reading or processing the image: " + path + " " + e.getMessage());
        }
    }

    /**
     * Creates a maze solver instance based on the solver name and movement model.
     *
     * @param solverType The type of solver to use.
     * @param maze The maze to be solved.
     * @param directions The set of allowed movement directions.
     * @return a {@link MazeSolver} configured with the given maze and movement model.
     * @throws IllegalArgumentException if the solver name is invalid.
     */
    private MazeSolver createSolver(String solverType, Maze maze, Direction[] directions) {

        String solverName = solverType.trim().toUpperCase();

        return switch (solverName) {
            case "BFS" -> new BFSSolver(maze, directions);
            case "DFS" -> new DFSSolver(maze, directions);
            case "GBFS" -> new GreedyBestFirstSearchSolver(maze, directions, selectHeuristic(directions));
            case "ASTAR" -> new AStarSolver(maze, directions, selectHeuristic(directions));
            case "UCS" -> new UCSSolver(maze, directions);
            default -> throw new IllegalArgumentException("Unknown solver: " + solverName);
        };
    }

    /**
     * Selects the appropriate heuristic based on the movement model.
     */
    private Heuristic selectHeuristic(Direction[] direction) {

        if (direction == Direction.ORTHOGONAL) {
            return new ManhattanDistanceHeuristic();
        } else {
            return new OctileDistanceHeuristic();
        }
    }
}
