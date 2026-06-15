package io.github.springfeld7.mazesolver.model.mazesolver;

import java.awt.Point;

/**
 * A node used by search algorithms that holds position maze position along with its parent node.
 * @author Thomas Springfeldt
 */
public class SearchNode {
    private Point position;
    private SearchNode parent;
    private double pathCost;
    private double estimatedCost;

    /**
     * Creates a new search node with the given position, and parent node.
     * @param position A {@code Point} pointing to the position of the node in the maze.
     * @param parent The parent node in the search path.
     */
    public SearchNode(Point position, SearchNode parent) {

        this.position = position;
        this.parent = parent;
    }

    /**
     * Creates a new search node with the given position, parent, path cost from the start node,
     * and estimated cost to the goal.
     * @param position The position represented by this node.
     * @param parent The parent node in the search path, or {@code null} if this is the start node.
     * @param pathCost The path cost from the start node to this node.
     * @param estimatedCost The estimated cost from this node to the goal (heuristic).
     */
    public SearchNode(Point position, SearchNode parent, double pathCost, double estimatedCost) {

        this.position = position;
        this.parent = parent;
        this.pathCost = pathCost;
        this.estimatedCost = estimatedCost;
    }

    /**
     * Accessor for position.
     * @return The position.
     */
    public Point getPosition() { return position; }

    /**
     * Accessor for parent.
     * @return The parent {@code SearchNode}.
     */
    public SearchNode getParent() { return parent; }

    /**
     * Accessor for pathCost.
     * @return The path cost from start node.
     */
    public double getPathCost() { return pathCost; }

    /**
     * Accessor for estimatedCost calculated by the heuristic used.
     * @return The heuristic estimate to the goal.
     */
    public double getEstimatedCost() { return estimatedCost; }

    /**
     * Returns the total estimated cost (f = (g) pathCost + (h) estimatedCost).
     * @return The sum of path cost and estimated cost.
     */
    public double getTotalCost() { return pathCost + estimatedCost; }
}
