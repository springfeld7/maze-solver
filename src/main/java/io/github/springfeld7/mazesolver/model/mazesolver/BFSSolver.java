package io.github.springfeld7.mazesolver.model.mazesolver;

import io.github.springfeld7.mazesolver.model.maze.Maze;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Maze solver that uses the Breadth-First Search algorithm.
 * This algorithm explores nodes in order of their depth from the start,
 * always expanding all nodes at the current level before moving to the next.
 * @author Thomas Springfeldt
 */
public class BFSSolver extends MazeSolver {

    /**
     * Construction initializes private members in super class.
     * @param maze The maze to be solved.
     */
    public BFSSolver(Maze maze, Direction[] directions) {
        super(maze, directions);
    }

    /**
     * {@inheritDoc}
     * This maze solver uses a BFS algorithm to solve the maze.
     */
    @Override
    public List<Point> solve() {

        Queue<SearchNode> frontier = new LinkedList<>();
        Set<Point> inFrontier = new HashSet<>();
        Set<Point> expanded = new HashSet<>();

        Point start = getMaze().getStart();
        Point goal = getMaze().getFinish();

        frontier.add(new SearchNode(start, null));
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
                    frontier.add(new SearchNode(child, current));
                    inFrontier.add(child);
                }
            }
        }
        return null;
    }
}
