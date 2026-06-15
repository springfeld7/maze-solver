package io.github.springfeld7.mazesolver.model.heuristic;

import java.awt.Point;

/**
 * A heuristic function implementation that calculates the Octile distance between two points.
 * @author Thomas Springfeldt
 */
public class OctileDistanceHeuristic implements Heuristic {


    /**
     * Calculates the Octile distance between the current point and the goal point.
     * @param current the starting point
     * @param goal the destination point
     * @return the estimated cost to reach the goal from the current point
     */
    @Override
    public double calculate(Point current, Point goal) {

        double dx = Math.abs(goal.x - current.x);
        double dy = Math.abs(goal.y - current.y);

        return (Math.sqrt(2) * Math.min(dx, dy)) + Math.abs(dx - dy);
    }
}
