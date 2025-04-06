package eda.algorithms;

import java.util.List;

/**<h1>PRUEBAS EMPÍRICAS PRÁCTICA 3  </h1>
 * <h3>TestLaberinto - Prueba de Generación y Resolución de Laberintos</h3>
 * Esta clase contiene un método principal para probar la generación de laberintos
 * utilizando el algoritmo DFS y su resolución utilizando el algoritmo de divide y vencerás.
 * <p>
 * La clase `TestLaberinto` demuestra la generación de un laberinto de tamaño fijo (11x11)
 * y la búsqueda de un camino desde una posición de inicio a una posición de fin.
 * <p>
 * Costes de los métodos:
 * <ul>
 *     <li>main: El coste de la generación y resolución del laberinto depende de los algoritmos
 *     subyacentes utilizados (`MazeGeneratorDFS` y `MazeSolverDC`). La generación tiene un coste
 *     de O(n * m) y la resolución puede tener un coste de O(4^n) en el peor de los casos.</li>
 * </ul>
 * <p>
 * Complejidad espacial de todas las operaciones: O(n * m) para la generación y resolución del laberinto.
 *
 */
public class TestLaberinto {
    public static void main(String[] args) {
        MazeGenerator generator = new MazeGeneratorDFS();
        Maze maze = generator.generate(11, 11);

        System.out.println("Generated Maze:");
        System.out.println(maze);

        MazeSolver solver = new MazeSolverDC();
        List<int[]> solution = solver.solve(maze, new int[]{1, 1}, new int[]{9, 9});

        if (solution.isEmpty()) {
            System.out.println("No solution found.");
        } else {
            System.out.println("Solution Path:");
            for (int[] step : solution) {
                System.out.println("(" + step[0] + ", " + step[1] + ")");
            }
        }
    }
}

/**
 * <h3>MazeComparison - Comparación Empírica de Algoritmos de Resolución de Laberintos</h3>
 * Esta clase contiene un método principal para comparar empíricamente el tiempo de ejecución
 * de dos algoritmos de resolución de laberintos: divide y vencerás y programación dinámica.
 * <p>
 * La clase `MazeComparison` mide el tiempo necesario para resolver laberintos de diferentes
 * tamaños utilizando ambos algoritmos y genera una tabla de tiempos.
 * <p>
 * <p>
 * Costes de los métodos:
 * <ul>
 *     <li>main: El coste de la generación y resolución del laberinto depende de los algoritmos
 *     subyacentes utilizados (`MazeGeneratorDFS`, `MazeSolverDC` y `MazeSolverDP`). La generación
 *     tiene un coste de O(n * m) y la resolución puede tener un coste de O(4^n) en el peor de los casos
 *     para divide y vencerás, y O(n^2) para programación dinámica.</li>
 * </ul>
 * <p>
 * Complejidad espacial de todas las operaciones: O(n * m) para la generación y resolución del laberinto.
 *
 */
class MazeComparison {
    public static void main(String[] args) {
        int[] sizes = {11, 21, 31, 41, 51, 61, 71, 81, 91, 150};

        System.out.println("| Dimensión | Divide y vencerás  | Programación dinámica |");
        for (int size : sizes) {
            MazeGenerator generator = new MazeGeneratorDFS();
            Maze maze = generator.generate(size, size);

            int[] origin = {1, 1};
            int[] destination = {size - 2, size - 2};

            MazeSolver solverDC = new MazeSolverDC();
            long startDC = System.nanoTime();
            solverDC.solve(maze, origin, destination);
            long endDC = System.nanoTime();
            double timeDC = (endDC - startDC) / 1e6;

            MazeSolver solverDP = new MazeSolverDP();
            long startDP = System.nanoTime();
            solverDP.solve(maze, origin, destination);
            long endDP = System.nanoTime();
            double timeDP = (endDP - startDP) / 1e6;

            System.out.printf("|    %d     |   %.6f ms      |     %.6f ms        |\n", size, timeDC, timeDP);
        }
    }
}
