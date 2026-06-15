package io.github.springfeld7.mazesolver.model.heuristic;

import java.awt.Point;

/**
 * A heuristic function implementation that calculates the Manhattan distance between two points.
 * @author Thomas Springfeldt
 */
public class ManhattanDistanceHeuristic implements Heuristic {

    /**
     * Calculates the Manhattan distance between the current point and the goal point.
     * @param current the starting point
     * @param goal the destination point
     * @return the estimated cost to reach the goal from the current point
     */
    @Override
    public double calculate(Point current, Point goal) {
        return Math.abs(goal.x - current.x) + Math.abs(goal.y - current.y);
    }
}
