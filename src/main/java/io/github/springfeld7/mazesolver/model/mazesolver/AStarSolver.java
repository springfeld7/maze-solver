package io.github.springfeld7.mazesolver.model.mazesolver;

import io.github.springfeld7.mazesolver.model.heuristic.Heuristic;
import io.github.springfeld7.mazesolver.model.maze.Maze;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Maze solver that uses the A* Search algorithm.
 * This algorithm explores nodes in order of their combined path cost and heuristic estimate,
 * always expanding the node with the lowest estimated total cost to the goal.
 * @author Thomas Springfeldt
 */
public class AStarSolver extends HeuristicSolver {

    /**
     * Construction initializes private members in super class.
     * @param maze The maze to be solved.
     */
    public AStarSolver(Maze maze, Direction[] directions, Heuristic heuristic) {
        super(maze, directions, heuristic);
    }

    /**
     * {@inheritDoc}
     * This maze solver uses A* algorithm to solve the maze.
     */
    @Override
    public List<Point> solve() {

        Point start = getMaze().getStart();
        Point goal = getMaze().getFinish();
        Set<Point> inFrontier = new HashSet<>();
        Set<Point> expanded = new HashSet<>();
        Map<Point, Double> g = new HashMap<>();
        Heuristic h = getHeuristic();
        PriorityQueue<SearchNode> frontier = new PriorityQueue<>(
                Comparator.comparingDouble(SearchNode::getTotalCost)
        );

        g.put(start, 0.0);
        frontier.add(new SearchNode(start, null, 0.0, h.calculate(start, goal)));
        inFrontier.add(start);

        while (!frontier.isEmpty()) {

            SearchNode current = frontier.poll();
            // Check if this position already been processed with a cheaper path
            if (current.getPathCost() > g.getOrDefault(current.getPosition(), Double.POSITIVE_INFINITY)) {
                continue;
            }

            inFrontier.remove(current.getPosition());

            expanded.add(current.getPosition());
            addToExpansionOrder(current.getPosition());

            if (current.getPosition().equals(goal)) {
                return buildSolutionPath(current);
            }

            for (Point child : expand(current.getPosition())) {

                double pathCost = current.getPathCost() + calculateMovementCost(current.getPosition(), child);

                if (!inFrontier.contains(child) && !expanded.contains(child)) {

                    g.put(child, pathCost);
                    frontier.add(new SearchNode(child, current, pathCost, h.calculate(child, goal)));
                    inFrontier.add(child);

                } else if (pathCost < g.getOrDefault(child, Double.POSITIVE_INFINITY)) {

                    g.put(child, pathCost);
                    frontier.add(new SearchNode(child, current, pathCost, h.calculate(child, goal)));
                }
            }
        }
        return null;
    }
}
