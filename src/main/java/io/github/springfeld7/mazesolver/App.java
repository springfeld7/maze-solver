package io.github.springfeld7.mazesolver;

import io.github.springfeld7.mazesolver.controller.AppRunner;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Main starting point for the project application - a maze solving program.
 * @author Thomas Springfeldt
 */
public final class App {

    private App() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * Starts the maze solver.
     * @param args the program arguments.
     */
    public static void main(String[] args) {

        try {
            new AppRunner(args).runApp();
        } catch (IOException | IllegalStateException | URISyntaxException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
