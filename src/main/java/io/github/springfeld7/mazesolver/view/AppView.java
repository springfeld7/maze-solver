package io.github.springfeld7.mazesolver.view;

import io.github.springfeld7.mazesolver.model.maze.CellState;
import io.github.springfeld7.mazesolver.support.AppConfig;

import javax.swing.JFrame;

/**
 * Main view of the application.
 * @author Thomas Springfeldt
 */
public class AppView extends JFrame {

    private final MazePanel mazePanel;

    /**
     * Constructs the GUI.
     * @param cellStates The states of each cell, used for rendering purposes.
     */
    public AppView(CellState[][] cellStates) {

        mazePanel = new MazePanel(cellStates);
        setupFrame();
    }

    /**
     * Sets up initial settings for the frame.
     */
    private void setupFrame() {

        setTitle(AppConfig.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mazePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Accessor for mazePanel.
     * @return The mazePanel.
     */
    public MazePanel getMazePanel() { return mazePanel; }
}
