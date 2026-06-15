# Maze Solver

A Java-based maze solving application that uses classical graph search algorithms to find paths in grid-based mazes. The project supports multiple solvers, configurable movement rules, and a visual Swing-based interface.

---

## Features

- Multiple pathfinding algorithms:
    - Breadth-First Search (BFS)
    - Depth-First Search (DFS)
    - Greedy Best-First Search (GBFS)
    - A* Search (ASTAR)
    - Uniform Cost Search (UCS)

- Optional diagonal movement support
- Maze visualization with step-by-step exploration
- Configurable image-based maze input
- Modular architecture separating:
    - controllers
    - solvers
    - models
    - UI/view
    - support utilities

---

## Project Structure
io.github.springfeld7.mazesolver

├── controller → Application startup & orchestration (AppRunner, AppController)

├── model → Maze representation + search algorithms + heuristics

├── support → Utilities (maze loading, configuration, constants)

├── view → Swing-based visualization

└── App.java → Application entry point

---

## Maze Format

Mazes are loaded from image resources located in:

src/main/resources/mazes/

### Difficulty levels

- easy
- medium
- hard
- mega

### Maze types per difficulty

Each difficulty folder contains:

- cutout
- escape
- oval
- rectangle
- wheel

⚠️ Exception:
- `mega` only contains `rectangle` mazes

### Example resource path

mazes/medium/rectangle/maze_medium_rectangle3.png

---

## How to Run the Application

The application requires two mandatory arguments:

1. Maze path (relative to resources)
2. Solver type

Optionally, a third argument enables diagonal movement.

---

### Command format

```<maze-path> <solver-type> [--diagonal | -d]```

---

### Example usage

#### BFS on a medium maze
```
medium/rectangle/maze_medium_rectangle3 bfs
```

#### A* with diagonal movement
```
hard/oval/maze_hard_oval2 astar --diagonal
```
#### DFS without diagonal movement
```
easy/cutout/maze_easy_cutout1 dfs
```
### Solver Types

| Type  | Description                              |
| ----- | ---------------------------------------- |
| BFS   | Finds shortest path in unweighted graphs |
| DFS   | Depth-first exploration (not optimal)    |
| GBFS  | Greedy best-first search using heuristic |
| ASTAR | A* search using cost + heuristic         |
| UCS   | Uniform cost search (Dijkstra-like)      |

---

### Configuration

The application uses a centralized configuration class:

```AppConfig```

Key settings include:

- Image processing thresholds
- Cell sizing and visualization scaling
- Animation speed
- UI colors for visualization states

---

## Author

Thomas Springfeldt