package eda.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>MazeSolverDC - Resolución de Laberintos mediante Divide y Vencerás</h3>
 * Implementación de un algoritmo para resolver laberintos utilizando la técnica de divide y vencerás.
 * Esta clase proporciona una solución para encontrar el camino más corto en un laberinto desde
 * una celda de origen hasta una celda de destino.
 * <p>
 * El algoritmo de divide y vencerás aborda el problema de manera recursiva, dividiendo el problema
 * original en subproblemas más pequeños, resolviendo cada uno de ellos y combinando sus soluciones.
 * Este método, aunque intuitivo y sencillo de implementar, puede tener una complejidad exponencial
 * en el peor de los casos debido a la posible exploración redundante de caminos.
 * <p>
 * Costes de los métodos:
 * <ul>
 *     <li>solve: O(4^n), donde n es el número de celdas libres en el laberinto. El tiempo de ejecución puede crecer exponencialmente en el peor de los casos debido a la exploración de múltiples caminos.</li>
 *     <li>findPath: O(4^n), el método encuentra el camino explorando todas las posibles rutas de manera recursiva.</li>
 * </ul>
 * <p>
 * Complejidad espacial de todas las operaciones: O(n^2), ya que se utiliza una matriz booleana para marcar las celdas visitadas durante la búsqueda.
 *
 * @param <E> el tipo de elementos que se almacenan en el laberinto, los cuales deben ser comparables

 * @author Marcos del Amo Fernandez y Gabriela Potenciano
 * @version Práctica 3 - Parte 1 - Estructuras de Datos y Algoritmos
 */
public class MazeSolverDC implements MazeSolver {

    /**
     * Encuentra un camino desde la posición inicial (`origin`) hasta la posición final (`destination`) en el laberinto `maze`.
     *
     * @param maze El objeto `Maze` que representa el laberinto a resolver.
     * @param origin Un arreglo de dos enteros que representa la posición inicial (fila, columna).
     * @param destination Un arreglo de dos enteros que representa la posición final (fila, columna).
     * @return Una lista de arreglos de dos enteros que representa el camino encontrado, o una lista vacía si no se encuentra un camino.
     */
    @Override
    public List<int[]> solve(Maze maze, int[] origin, int[] destination) {
        List<int[]> path = new ArrayList<>();
        if (findPath(maze, origin, destination, path, new boolean[maze.rows][maze.cols])) {
            return path;
        }
        return new ArrayList<>();
    }

    /**
     * Método recursivo que implementa el algoritmo de Divide y Conquista para encontrar un camino en el laberinto.
     *
     * @param maze El objeto `Maze` que representa el laberinto a resolver.
     * @param current Un arreglo de dos enteros que representa la posición actual (fila, columna).
     * @param destination Un arreglo de dos enteros que representa la posición final (fila, columna).
     * @param path Una lista de arreglos de dos enteros que almacena el camino encontrado hasta el momento.
     * @param visited Una matriz booleana que marca las celdas ya visitadas durante la búsqueda.
     * @return `true` si se encuentra un camino, `false` en caso contrario.
     */
    private boolean findPath(Maze maze, int[] current, int[] destination, List<int[]> path, boolean[][] visited) {
        int row = current[0];
        int col = current[1];

        if (row < 0 || col < 0 || row >= maze.rows || col >= maze.cols || maze.data[row][col] == Maze.WALL || visited[row][col]) {
            return false;
        }

        path.add(new int[]{row, col});
        visited[row][col] = true;

        if (row == destination[0] && col == destination[1]) {
            return true;
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            if (findPath(maze, new int[]{row + dir[0], col + dir[1]}, destination, path, visited)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }
}
