package io.github.springfeld7.mazesolver.model.mazesolver;

import io.github.springfeld7.mazesolver.model.heuristic.Heuristic;
import io.github.springfeld7.mazesolver.model.maze.Maze;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Maze solver that uses a Greedy Best First Search algorithm.
 * @author Thomas Springfeldt
 */
public class GreedyBestFirstSearchSolver extends HeuristicSolver {

    /**
     * Construction initializes private members in super class.
     * @param maze The maze to be solved.
     */
    public GreedyBestFirstSearchSolver(Maze maze, Direction[] directions, Heuristic heuristic) {
        super(maze, directions, heuristic);
    }

    /**
     * {@inheritDoc}
     * This maze solver uses a Greedy Best First Search algorithm to solve the maze.
     */
    @Override
    public List<Point> solve() {

        Point start = getMaze().getStart();
        Point goal = getMaze().getFinish();
        Set<Point> inFrontier = new HashSet<>();
        Set<Point> expanded = new HashSet<>();
        Heuristic h = getHeuristic();
        PriorityQueue<SearchNode> frontier = new PriorityQueue<>(
                Comparator.comparingDouble(SearchNode::getEstimatedCost)
        );

        frontier.add(new SearchNode(start, null, 0.0, h.calculate(start, goal)));
        inFrontier.add(start);

        while (!frontier.isEmpty()) {

            SearchNode current = frontier.poll();
            inFrontier.remove(current.getPosition());

            expanded.add(current.getPosition());
            addToExpansionOrder(current.getPosition());

            if (current.getPosition().equals(goal)) {
                return buildSolutionPath(current);
            }

            for (Point child : expand(current.getPosition())) {

                if (!inFrontier.contains(child) && !expanded.contains(child)) {
                    frontier.add(new SearchNode(child, current, 0.0, h.calculate(child, goal)));
                    inFrontier.add(child);
                }
            }
        }
        return null;
    }
}
