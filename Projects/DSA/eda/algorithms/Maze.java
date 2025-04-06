package eda.algorithms;

/**
 * <h3>Maze - Representación de un Laberinto 2D</h3>
 * Esta clase representa un laberinto utilizando una matriz de enteros. Cada celda del laberinto
 * puede ser un espacio vacío o una pared. Proporciona métodos para inicializar y representar
 * el laberinto como una cadena de caracteres.
 * <p>
 * Un laberinto se representa mediante una matriz de enteros de dimensiones mxn. Cada celda en
 * la matriz puede tener un valor de 0 (representando un espacio vacío) o 1 (representando una pared).
 *
 * @author Marcos del Amo Fernandez y Gabriela Potenciano
 * @version Práctica 3 - Parte 1 - Estructuras de Datos y Algoritmos
 * @param rows Número de filas del laberinto
 * @param cols Número de columnas del laberinto
 * @version Práctica 3 - Estructuras de Datos y Algoritmos
 */
public class Maze {
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public int[][] data;
    public int rows;
    public int cols;

    /**
     * Constructor que crea un laberinto con las dimensiones especificadas.
     * Inicializa todas las celdas del laberinto como paredes.
     *
     * @param rows El número de filas del laberinto.
     * @param cols El número de columnas del laberinto.
     */
    public Maze(int rows, int cols) {
        this.data = new int[rows][cols];
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = WALL;
            }
        }
    }

    /**
     * Retorna una representación en forma de cadena del laberinto.
     * Utiliza '#' para representar las paredes y ' ' (espacio) para representar los espacios libres.
     *
     * @return Una cadena que representa el laberinto.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : data) {
            for (int cell : row) {
                sb.append(cell == WALL ? "#" : " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
