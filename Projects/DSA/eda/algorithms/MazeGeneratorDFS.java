package eda.algorithms;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>MazeGeneratorDFS - Generación de Laberintos mediante Búsqueda en Profundidad (DFS)</h3>
 * Implementación de un algoritmo para generar laberintos utilizando el método de búsqueda en profundidad (DFS).
 * Esta clase proporciona una solución para crear laberintos aleatorios, garantizando que haya al menos un camino
 * entre la celda de inicio y la celda de fin.
 * <p>
 * El algoritmo de búsqueda en profundidad (DFS) es un enfoque basado en el recorrido exhaustivo de todos los nodos
 * de un grafo, comenzando desde un nodo inicial y explorando tan lejos como sea posible antes de retroceder. Este
 * método es adecuado para la generación de laberintos, ya que asegura que cada celda del laberinto será visitada y
 * que se creará un camino único entre dos celdas.
 * <p>
 * Costes de los métodos:
 * <ul>
 *     <li>generate: O(n * m), donde n es el número de filas y m es el número de columnas del laberinto. El tiempo de ejecución es lineal respecto al tamaño del laberinto.</li>
 *     <li>dfs: O(n * m), el método recorre todas las celdas del laberinto de manera exhaustiva.</li>
 *     <li>isValidMove: O(1), verifica si un movimiento es válido en tiempo constante.</li>
 * </ul>
 * <p>
 * Complejidad espacial de todas las operaciones: O(n * m), ya que se necesita una matriz adicional para marcar las celdas visitadas.
 *
 * @param <E> el tipo de elementos que se almacenan en el laberinto, los cuales deben ser comparables
 * @author Marcos del Amo Fernandez y Gabriela Potenciano
 * @version Práctica 3 - Parte 1 - Estructuras de Datos y Algoritmos
 */
public class MazeGeneratorDFS implements MazeGenerator {

    /**
     * Genera un laberinto aleatorio utilizando el algoritmo DFS.
     *
     * @param rows El número de filas en el laberinto.
     * @param cols El número de columnas en el laberinto.
     * @return Un objeto `Maze` que representa el laberinto generado.
     */
    @Override
    public Maze generate(int rows, int cols) {
        Maze maze = new Maze(rows, cols);
        boolean[][] visited = new boolean[rows][cols];
        dfs(maze, visited, 1, 1);
        return maze;
    }

    /**
     * Método recursivo que implementa el algoritmo DFS para explorar el laberinto.
     *
     * @param maze El objeto `Maze` que se está generando.
     * @param visited La matriz booleana que marca las celdas visitadas.
     * @param row La fila actual en la que se encuentra la búsqueda.
     * @param col La columna actual en la que se encuentra la búsqueda.
     */
    private void dfs(Maze maze, boolean[][] visited, int row, int col) {
        visited[row][col] = true;
        maze.data[row][col] = Maze.EMPTY;

        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{-2, 0});
        directions.add(new int[]{2, 0});
        directions.add(new int[]{0, -2});
        directions.add(new int[]{0, 2});
        Collections.shuffle(directions);

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValidMove(maze, visited, newRow, newCol)) {
                maze.data[row + dir[0] / 2][col + dir[1] / 2] = Maze.EMPTY;
                dfs(maze, visited, newRow, newCol);
            }
        }
    }

    /**
     * Verifica si una posición dada (`row`, `col`) es un movimiento válido dentro del laberinto.
     *
     * @param maze El objeto `Maze` que se está generando.
     * @param visited La matriz booleana que marca las celdas visitadas.
     * @param row La fila que se está evaluando como posible movimiento.
     * @param col La columna que se está evaluando como posible movimiento.
     * @return `true` si la posición representa un movimiento válido, `false` en caso contrario.
     */
    private boolean isValidMove(Maze maze, boolean[][] visited, int row, int col) {
        return row > 0 && row < maze.rows - 1 && col > 0 && col < maze.cols - 1 && !visited[row][col];
    }
}
