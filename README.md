# How to run the app

To run the app these arguments need to be provided:

- url of maze image
- type of solver to use

Optionally pass "-d" or "--diagonal" to allow maze solving with diagonal movement.

Valid solver arguments (case-insensitive):

BFS
DFS
GBFS
ASTAR
UCS

For example:

- medium/rectangle/maze_medium_rectangle3 bfs
- hard/oval/maze_hard_oval2 ASTAR --diagonal
