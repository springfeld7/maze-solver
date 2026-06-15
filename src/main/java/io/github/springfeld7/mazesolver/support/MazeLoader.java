package io.github.springfeld7.mazesolver.support;

import io.github.springfeld7.mazesolver.App;
import io.github.springfeld7.mazesolver.model.maze.Marker;
import io.github.springfeld7.mazesolver.model.maze.Maze;
import io.github.springfeld7.mazesolver.model.maze.MazeCell;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static io.github.springfeld7.mazesolver.support.AppConfig.CELL_SIZE;

/**
 * Class responsible for loading mazes from PNG image files.
 * @author Thomas Springfeldt
 */
public class MazeLoader {

    /**
     * Loads a maze from a PNG image file. The method reads the image, converts it to grayscale,
     * detects the maze boundaries, and constructs a {@code Maze} object by dividing the bounded region
     * into a grid of cells. Each cell is classified as a wall or path based on pixel intensity analysis.
     * @param fileName The path to the maze image file within the resources directory.
     * @return A {@code Maze} object representing the parsed maze grid.
     * @throws IOException if the image cannot be found or read from the specified file path.
     * @throws IllegalStateException if no wall pixels are found in the image,
     *                               or if not both start and finish points are present in the maze.
     * @throws IllegalArgumentException if cellSize is not positive.
     */
    public static Maze readMaze(String fileName) throws IOException {

        BufferedImage originalImage = readImage(fileName);
        BufferedImage grayscaleImage = convertToGrayscale(originalImage);

        Rectangle bounds = findMazeBoundaries(grayscaleImage, CELL_SIZE);

        int rows = bounds.height / CELL_SIZE;
        int cols = bounds.width / CELL_SIZE;

        MazeCell[][] maze = new MazeCell[rows][cols];

        Point start = null;
        Point finish = null;

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {

                int offsetX = bounds.x + col * CELL_SIZE;
                int offsetY = bounds.y + row * CELL_SIZE;

                maze[row][col] = processCellRegion(grayscaleImage, offsetX, offsetY, CELL_SIZE, AppConfig.CELL_WALL_RATIO_THRESHOLD);

                if (start != null && finish != null) continue;

                Marker marker = findMarkerInCell(originalImage, offsetX, offsetY, CELL_SIZE);

                if (marker == Marker.START && start == null) {
                    start = new Point(col, row);
                } else if (marker == Marker.FINISH && finish == null) {
                    finish = new Point(col, row);
                }
            }
        }

        if (start == null || finish == null) {
            throw new IllegalStateException("Both start and finish points needs to be present in the maze.");
        }

        return new Maze(maze, start, finish);
    }

    /**
     * Processes a square region of the grayscale image to determine whether it should be classified
     * as a wall or a path based on the proportion of dark (low-intensity) pixels.
     * @param grayscaleImage The grayscale image to analyze.
     * @param offsetX The x-coordinate of the top-left corner of the region to process.
     * @param offsetY The y-coordinate of the top-left corner of the region to process.
     * @param cellSize The width and height of the square cell in pixels.
     * @param wallThreshold The ratio of dark pixels required to classify the region as a wall.
     * @return A {@code MazeCell} object representing the region as either a wall or a path.
     */
    private static MazeCell processCellRegion(
            BufferedImage grayscaleImage, int offsetX, int offsetY, int cellSize, double wallThreshold) {

        int blackPixelCount = 0;
        int totalPixels = cellSize * cellSize;

        for (int y = offsetY; y < offsetY + cellSize; y++) {
            for (int x = offsetX; x < offsetX + cellSize; x++) {

                // Get intensity from blue channel
                int rgb = grayscaleImage.getRGB(x, y);
                int intensity = rgb & 0xFF;

                if (intensity < AppConfig.WALL_INTENSITY_THRESHOLD) { // Check if pixel is a wall
                    blackPixelCount++;
                }
            }
        }

        if (blackPixelCount / (double) totalPixels >= wallThreshold) {
            return new MazeCell(false);
        } else {
            return new MazeCell(true);
        }
    }

    /**
     * Scans a specified cell region within an image to detect if it contains a start or finish marker.
     * @param image The original colored image to scan for markers.
     * @param offsetX The x-coordinate of the top-left corner of the cell region.
     * @param offsetY The y-coordinate of the top-left corner of the cell region.
     * @param cellSize The width and height of the square cell region in pixels.
     * @return The {@code Marker} type detected in the cell region: START, FINISH, or NONE.
     */
    private static Marker findMarkerInCell(BufferedImage image, int offsetX, int offsetY, int cellSize) {

        for (int y = offsetY; y < offsetY + cellSize; y++) {
            for (int x = offsetX; x < offsetX + cellSize; x++) {

                int rgb = image.getRGB(x, y) & 0xFFFFFF;

                if (rgb == (AppConfig.START_COLOR.getRGB() & 0xFFFFFF)) return Marker.START;
                if (rgb == (AppConfig.FINISH_COLOR.getRGB() & 0xFFFFFF)) return Marker.FINISH;
            }
        }
        return Marker.NONE;
    }

    /**
     * Scans the grayscale image to find the bounding box of all wall pixels (pixels with intensity
     * below a defined threshold), and adjusts the box so that its width and height are aligned to
     * the given cell size. The alignment is centered around the detected area.
     * @param grayscaleImage The grayscale BufferedImage to scan.
     * @param cellSize The size of each maze cell in pixels.
     * @return A Rectangle representing the cell-aligned bounding box of the maze area.
     * @throws IllegalStateException if no wall pixels are found in the image.
     * @throws IllegalArgumentException if cellSize is not positive.
     */
    private static Rectangle findMazeBoundaries(BufferedImage grayscaleImage, int cellSize) {

        int width = grayscaleImage.getWidth();
        int height = grayscaleImage.getHeight();

        // Set min and max values to the bounds of the grayscale image
        int minX = width;
        int minY = height;
        int maxX = 0;
        int maxY = 0;

        // Iterate over each pixel in the image to scan for maze boundaries
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // Get intensity from blue channel
                int rgb = grayscaleImage.getRGB(x, y);
                int intensity = rgb & 0xFF;

                if (intensity < AppConfig.WALL_INTENSITY_THRESHOLD) { // Check if pixel is a wall
                    if (x < minX) minX = x;
                    if (x > maxX) maxX = x;
                    if (y < minY) minY = y;
                    if (y > maxY) maxY = y;
                }
            }
        }

        // Check if something that can be considered a maze was found
        if (minX > maxX || minY > maxY) {
            throw new IllegalStateException("No wall pixels found in image. Check input image or threshold.");
        }

        // Get width and height of maze region, add 1 since min and max values are inclusive
        int mazeWidth = maxX - minX + 1;
        int mazeHeight = maxY - minY + 1;

        // Adjust width and height to align with cell size
        int alignedWidth = alignLength(mazeWidth, cellSize);
        int alignedHeight = alignLength(mazeHeight, cellSize);

        // Calculate needed padding for width and height
        int widthPadding = alignedWidth - mazeWidth;
        int heightPadding = alignedHeight - mazeHeight;

        // Compute adjusted bounds, centered around the detected maze, clamped to image edges
        int adjustedMinX = Math.max(0, minX - widthPadding / 2);
        int adjustedMinY = Math.max(0, minY - heightPadding / 2);
        int adjustedMaxX = Math.min(width, adjustedMinX + alignedWidth);
        int adjustedMaxY = Math.min(height, adjustedMinY + alignedHeight);

        return new Rectangle(adjustedMinX, adjustedMinY, adjustedMaxX - adjustedMinX, adjustedMaxY - adjustedMinY);
    }

    /**
     * Rounds up the given length to the nearest multiple of the specified cell size.
     * @param length The original length to align.
     * @param cellSize The cell size to align to (must be > 0).
     * @return The aligned length, rounded up to the next multiple of cellSize.
     * @throws IllegalArgumentException if cellSize is not positive.
     */
    private static int alignLength(int length, int cellSize) {

        if (cellSize <= 0) {
            throw new IllegalArgumentException("cellSize must be a positive integer.");
        }

        int remainder = length % cellSize;

        return (remainder == 0) ? length : length + (cellSize - remainder);
    }

    /**
     * Reads an image from the resources folder using the classpath.
     * @param fileName The name of the image file.
     * @return A BufferedImage representing the original image.
     * @throws IOException if the image cannot be found or read.
     */
    private static BufferedImage readImage(String fileName) throws IOException {

        InputStream is = App.class.getClassLoader().getResourceAsStream(fileName);

        if (is == null) {
            throw new IOException("Resource not found: " + fileName);
        }

        return ImageIO.read(is);
    }

    /**
     * Converts a given image to grayscale.
     * @param original The original image.
     * @return A grayscale BufferedImage.
     */
    private static BufferedImage convertToGrayscale(BufferedImage original) {

        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage grayscale = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D g = grayscale.createGraphics();
        g.drawImage(original, 0, 0, null);
        g.dispose();

        return grayscale;
    }
}
