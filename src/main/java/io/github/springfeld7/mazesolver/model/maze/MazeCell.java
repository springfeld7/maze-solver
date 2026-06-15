package io.github.springfeld7.mazesolver.model.maze;

/**
 * Represents a cell in the maze.
 * @author Thomas Springfeldt
 */
public class MazeCell {

    private final boolean isPath;

    /**
     * Public construction which initializes class properties.
     * @param isPath signifies if the cell is path or wall.
     */
    public MazeCell(boolean isPath) { this.isPath = isPath; }

    /**
     * Accessor for isPath boolean.
     * @return whether the cell is a path.
     */
    public boolean isPath() { return isPath; }
}
