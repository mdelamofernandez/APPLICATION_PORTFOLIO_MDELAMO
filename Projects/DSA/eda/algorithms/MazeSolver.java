package eda.algorithms;

import java.util.List;
public interface MazeSolver {
    List<int[]> solve(Maze maze, int[] origin, int[] destination);
}
