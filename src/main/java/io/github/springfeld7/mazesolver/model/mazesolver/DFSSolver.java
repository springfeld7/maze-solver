package io.github.springfeld7.mazesolver.model.mazesolver;

import io.github.springfeld7.mazesolver.model.maze.Maze;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Maze solver that uses the Depth-First Search algorithm (graph-search variant).
 * This algorithm explores nodes by always expanding the deepest unvisited node first,
 * effectively following one path as far as possible before backtracking.
 * @author Thomas Springfeldt
 */
public class DFSSolver extends MazeSolver {

    /**
     * Construction initializes private members in super class.
     * @param maze The maze to be solved.
     */
    public DFSSolver(Maze maze, Direction[] directions) {
        super(maze, directions);
    }

    /**
     * {@inheritDoc}
     * This maze solver uses a DFS algorithm to solve the maze.
     */
    @Override
    public List<Point> solve() {

        Stack<SearchNode> frontier = new Stack<>();
        Set<Point> inFrontier = new HashSet<>();
        Set<Point> expanded = new HashSet<>();

        Point start = getMaze().getStart();
        Point goal = getMaze().getFinish();

        frontier.push(new SearchNode(start, null));
        inFrontier.add(start);

        while (!frontier.isEmpty()) {

            SearchNode current = frontier.pop();
            inFrontier.remove(current.getPosition());

            expanded.add(current.getPosition());
            addToExpansionOrder(current.getPosition());

            if (current.getPosition().equals(goal)) {
                return buildSolutionPath(current);
            }

            for (Point child : expand(current.getPosition())) {

                if (!inFrontier.contains(child) && !expanded.contains(child)) {
                    frontier.push(new SearchNode(child, current));
                    inFrontier.add(child);
                }
            }
        }
        return null;
    }
}
