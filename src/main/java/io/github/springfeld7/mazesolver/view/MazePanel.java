package io.github.springfeld7.mazesolver.view;

import io.github.springfeld7.mazesolver.model.maze.CellState;
import io.github.springfeld7.mazesolver.support.AppConfig;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;

import static io.github.springfeld7.mazesolver.support.AppConfig.CELL_DISPLAY_SIZE;

/**
 * A Swing panel for rendering a Maze visually.
 */
public class MazePanel extends JPanel {

    private final CellState[][] cellStates;

    /**
     * Constructs a MazePanel for visualizing the maze.
     * @param cellStates A grid of the states of each cell in the maze.
     */
    public MazePanel(CellState[][] cellStates) {

        this.cellStates = cellStates;

        // Set preferred size based on maze dimensions
        int width = cellStates[0].length * CELL_DISPLAY_SIZE;
        int height = cellStates.length * CELL_DISPLAY_SIZE;
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Custom painting method that renders the maze grid on the panel.
     * The wall, path, start, finish, expanded, and solution
     * cells are highlighted using specific colors defined in {@link AppConfig}.
     * @param g The {@link Graphics} object used for drawing on the panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < cellStates.length; row++) {
            for (int col = 0; col < cellStates[0].length; col++) {

                CellState state = cellStates[row][col];

                switch (state) {
                    case WALL -> g.setColor(AppConfig.WALL_COLOR);
                    case PATH -> g.setColor(AppConfig.PATH_COLOR);
                    case START -> g.setColor(AppConfig.START_COLOR);
                    case FINISH -> g.setColor(AppConfig.FINISH_COLOR);
                    case EXPANDED -> g.setColor(AppConfig.EXPANDED_COLOR);
                    case SOLUTION -> g.setColor(AppConfig.SOLUTION_COLOR);
                }

                int x = col * CELL_DISPLAY_SIZE;
                int y = row * CELL_DISPLAY_SIZE;
                g.fillRect(x, y, CELL_DISPLAY_SIZE, CELL_DISPLAY_SIZE);
            }
        }
    }

    /**
     * Updates a cell's state in cellStates array.
     * @param newState The new cell state.
     * @param row The row number in the array of the cell to update.
     * @param col The col number in the array of the cell to update.
     */
    public void updateCellState(CellState newState, int row, int col) {
        cellStates[row][col] = newState;
    }
}
