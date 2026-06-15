package io.github.springfeld7.mazesolver.model.heuristic;

import java.awt.Point;

/**
 * Represents a heuristic function used in search algorithms to estimate
 * the cost from the current position to the goal position.
 * @author Thomas Springfeldt
 */
public interface Heuristic {

    /**
     * Calculates the estimated cost from a given current position to a goal position.
     * @param current The current position as a {@link Point}.
     * @param goal The target position as a {@link Point}.
     * @return The estimated cost as a {@code double}.
     */
    double calculate(Point current, Point goal);
}
