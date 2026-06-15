package io.github.springfeld7.mazesolver.model.maze;

import java.awt.Point;

/**
 * Represents a maze made up of a 2D grid of {@code MazeCell}.
 * @author Thomas Springfeldt
 */
public class Maze {

    private final MazeCell[][] maze;
    private final Point start;
    private final Point finish;

    public Maze(MazeCell[][] maze, Point start, Point finish) {

        this.maze = maze;
        this.start = start;
        this.finish = finish;
    }

    /**
     * Accessor for the maze grid.
     * @return The maze grid.
     */
    public MazeCell[][] getMazeGrid() { return maze; }

    /**
     * Accessor for the starting point of the maze.
     * @return The starting point of the maze.
     */
    public Point getStart() { return start; }

    /**
     * Accessor for the finish point of the maze.
     * @return The finish point of the maze.
     */
    public Point getFinish() { return finish; }
}
