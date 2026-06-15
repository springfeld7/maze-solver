package io.github.springfeld7.mazesolver.support;

import java.awt.Color;

/**
 * Utility class for constants/app settings.
 * @author Thomas Springfeldt
 */
public final class AppConfig {

    /**
     * Ensures instantiation cannot be done.
     */
    private AppConfig() { }

    /* PROJECT SETTINGS */
    public static final String PROJECT_TITLE = "Maze Solver";

    /* IMAGE PROCESSING SETTINGS */
    public static final int CELL_SIZE = 2;
    public static final int WALL_INTENSITY_THRESHOLD = 200;
    public static final double CELL_WALL_RATIO_THRESHOLD = 0.1;

    /* VISUALIZATION SETTINGS */
    public static final int CELL_DISPLAY_SIZE = 2;
    public static final int ANIMATION_STEP_DELAY = 1;

    /* CELL STATE COLORS */
    public static final Color PATH_COLOR = new Color(0xFFFFFF);
    public static final Color WALL_COLOR = new Color(0x000000);
    public static final Color START_COLOR = new Color(0xFF69B4);
    public static final Color FINISH_COLOR = new Color(0x00FFFF);
    public static final Color EXPANDED_COLOR = new Color(0x4C00FF);
    public static final Color SOLUTION_COLOR = new Color(0xFF4466);
}
