package eda.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h3>MazeSolverDP - Resolución de Laberintos mediante Programación Dinámica</h3>
 * Implementación de un algoritmo para resolver laberintos utilizando programación dinámica.
 * Esta clase proporciona una solución eficiente para encontrar el camino más corto en un laberinto
 * desde una celda de origen hasta una celda de destino.
 * <p>
 * La programación dinámica es una técnica que permite resolver problemas complejos
 * almacenando los resultados de subproblemas ya resueltos, evitando así cálculos repetitivos
 * y mejorando la eficiencia del algoritmo.
 * <p>
 * Este algoritmo utiliza una tabla de soluciones parciales para almacenar los resultados
 * intermedios durante la búsqueda del camino, permitiendo así reducir la complejidad temporal
 * del problema.
 * <p>
 * Costes de los métodos:
 * <ul>
 *     <li>solve: O(n^2), donde n es la dimensión del laberinto. El tiempo de ejecución es cuadrático debido al almacenamiento y consulta de soluciones parciales.</li>
 *     <li>findPath: O(n^2), el método encuentra el camino almacenando soluciones parciales y evitando recalcular rutas ya exploradas.</li>
 * </ul>
 * <p>
 * Complejidad espacial de todas las operaciones: O(n^2), ya que se necesita una tabla adicional de tamaño proporcional al laberinto para almacenar las soluciones parciales.
 *
 * @param <E> el tipo de elementos que se almacenan en el laberinto, los cuales deben ser comparables
 * @author Marcos del Amo Fernandez y Gabriela Potenciano
 * @version Práctica 3 - Parte 2 - Estructuras de Datos y Algoritmos
 */
public class MazeSolverDP implements MazeSolver {

    /**
     * Resuelve el laberinto utilizando programación dinámica, encontrando el camino más corto
     * desde una celda de origen hasta una celda de destino.
     *
     * @param maze El laberinto a resolver.
     * @param origin La celda de inicio en el laberinto.
     * @param destination La celda de destino en el laberinto.
     * @return Una lista de coordenadas (filas y columnas) que representan el camino desde la celda de origen hasta la celda de destino. Si no hay solución, retorna una lista vacía.
     */
    @Override
    public List<int[]> solve(Maze maze, int[] origin, int[] destination) {
        int[][] sol = new int[maze.rows][maze.cols];
        for (int[] row : sol) {
            Arrays.fill(row, -1);
        }

        List<int[]> path = new ArrayList<>();
        if (findPath(maze, origin, destination, path, sol)) {
            return path;
        }
        return new ArrayList<>();
    }

    /**
     * Método auxiliar que encuentra el camino más corto en el laberinto utilizando programación dinámica.
     * Este método almacena soluciones parciales para evitar recalcular rutas ya exploradas.
     *
     * @param maze El laberinto a resolver.
     * @param current La celda actual en el laberinto.
     * @param destination La celda de destino en el laberinto.
     * @param path La lista de coordenadas que representan el camino actual.
     * @param sol La tabla de soluciones parciales.
     * @return Verdadero si se encuentra un camino desde la celda actual hasta la celda de destino, falso en caso contrario.
     */
    private boolean findPath(Maze maze, int[] current, int[] destination, List<int[]> path, int[][] sol) {
        int row = current[0];
        int col = current[1];

        if (row < 0 || col < 0 || row >= maze.rows || col >= maze.cols || maze.data[row][col] == Maze.WALL) {
            return false;
        }

        if (sol[row][col] != -1) {
            return sol[row][col] == 1;
        }

        path.add(new int[]{row, col});
        if (row == destination[0] && col == destination[1]) {
            sol[row][col] = 1;
            return true;
        }

        sol[row][col] = 0;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            if (findPath(maze, new int[]{row + dir[0], col + dir[1]}, destination, path, sol)) {
                sol[row][col] = 1;
                return true;
            }
        }

        path.remove(path.size() - 1);
        sol[row][col] = 0;
        return false;
    }
}
