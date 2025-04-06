package eda.ds;

import java.util.*;
/** <h3>BST - Árbol de Búsqueda Binario
 * </h3>
 * Implementación de un árbol de búsqueda binaria (BST).
 * Esta clase proporciona funcionalidades para insertar, eliminar, obtener y buscar elementos en el árbol.
 * <p>
 * La estructura de un árbol de búsqueda binaria debido a su eficiencia
 * en operaciones de búsqueda, inserción y eliminación, con un coste promedio de O(log N) en cada una de estas operaciones,
 * donde N es el número de elementos en el árbol. La estructura permite una organización jerárquica de los elementos,
 * facilitando la búsqueda y el mantenimiento del orden.
 * <p>
 * Costes de los métodos:
 * <ul>
 *     <li>insert: O(log N) en promedio, donde N es el tamaño actual del árbol.
 *     La inserción puede requerir recorrer el árbol desde la raíz hasta la posición de inserción.</li>
 *     <li>delete: O(log N) en promedio, la eliminación de un nodo puede requerir recorrer el árbol
 *     para encontrar el nodo y reorganizar los subárboles si es necesario.</li>
 *     <li>search: O(log N) en promedio, donde N es el tamaño actual del árbol.
 *     La búsqueda de un elemento requerirá recorrer el árbol desde la raíz hasta el nodo deseado.</li>
 *     <li>max/min: O(log N) en promedio, donde N es el tamaño actual del árbol.
 *     Encontrar el valor máximo o mínimo requiere recorrer el subárbol derecho o izquierdo, respectivamente.</li>
 * </ul>
 * <p>
 * Complejidad espacial de todas las operaciones: O(1).
 *
 * @param <E> el tipo de elementos que se almacenan en el árbol, los cuales deben ser comparables
 * @author Marcos del Amo Fernández y Gabriela Potenciano Carpintero
 * @version Práctica 2 - Parte 1 - Estructuras de Datos y Algoritmos
 */
public class BST <E extends Comparable<E>> implements Iterable<E> {
    /**
     * Clase que representa un nodo en un árbol de búsqueda binaria.
     * Cada nodo contiene un dato y referencias a los nodos hijos izquierdo y derecho.
     *
     * @param <E> el tipo de dato almacenado en el nodo, el cual debe ser comparable
     */
    public static class TreeNode<E extends Comparable<E>> {
        public E val;
        public TreeNode<E> left;
        public TreeNode<E> right;
        /**
         * Constructor de un nodo que inicializa el dato y las referencias a null.
         *
         * @param val el dato a almacenar en el nodo
         */
        public TreeNode(E val) {
            this(val, null, null);
        }
        /**
         * Constructor de un nodo que inicializa el dato y las referencias especificadas.
         *
         * @param val el dato a almacenar en el nodo
         * @param left la referencia al nodo hijo izquierdo
         * @param right la referencia al nodo hijo derecho
         */
        public TreeNode(E val, TreeNode<E> left, TreeNode<E> right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    private TreeNode<E> root;
    private int size;
    /**
     * Constructor del árbol que inicializa la raíz como nula y el tamaño del árbol como 0.
     */
    public BST() {this(null);}
    /**
     * Constructor del árbol que inicializa la raíz con el nodo especificado.
     * Si la raíz no es nula, se recalcula el tamaño del árbol.
     *
     * @param root el nodo raíz del árbol
     */
    public BST(TreeNode<E> root) {
        this.root = root;
        this.size = 0;
        if (this.root == null) return;
        for (E val:this) this.size++;
    }
    /**
     * Retorna el tamaño actual del árbol.
     *
     * @return el tamaño actual del árbol
     */
    public int getSize() {return this.size;}
    /**
     * Inserta un elemento en el árbol binario de búsqueda.
     *
     * @param val el elemento a insertar
     */
    public void add(E val) {
        if (val == null) return;
        this.size++;
        TreeNode<E> node = this.root;
        while (node != null) {
            if (node.val.compareTo(val) > 0) {
                if (node.left == null) {
                    node.left = new TreeNode<E>(val);
                    return;
                } else node = node.left;
            } else {
                if (node.right == null) {
                    node.right = new TreeNode<E>(val);
                    return;
                } else node = node.right;
            }
        }
        this.root = new TreeNode<>(val);
    }
    /**
     * Elimina un elemento del árbol binario de búsqueda.
     *
     * @param val el elemento a eliminar
     */
    public void delete(E val) {
        if (this.root == null || val == null) return;
        this.size--;
        if (this.root.val == val) this.root = deleteNode(this.root);
        TreeNode<E> node = this.root;
        while (node != null) {
            if (node.val.compareTo(val) > 0) {
                if (node.left != null && node.left.val == val) {
                    node.left = deleteNode(node.left);
                    return;
                } else node = node.left;
            } else {
                if (node.right != null && node.right.val == val) {
                    node.right = deleteNode(node.right);
                    return;
                } else node = node.right;
            }
        }
    }
    /**
     * Elimina el nodo especificado y retorna su reemplazo.
     *
     * @param node el nodo a eliminar
     * @return el nodo de reemplazo
     */
    private TreeNode<E> deleteNode(TreeNode<E> node) {
        if (node.left == null) return node.right;
        if (node.right == null) return node.left;
        TreeNode<E> rightNode = node.right;
        TreeNode<E> leftNode = node.left;
        while (leftNode.right != null) leftNode = leftNode.right;
        leftNode.right = rightNode;
        return root.left;
    }
    /**
     * Busca un elemento en el árbol binario de búsqueda.
     *
     * @param val el elemento a buscar
     * @return el nodo que contiene el elemento, o null si no se encuentra
     */
    public TreeNode<E> search(E val) {
        if (val == null) return null;
        TreeNode<E> node = this.root;
        while (node != null) {
            if (val.compareTo(node.val) == 0) return node;
            else if (val.compareTo(node.val) < 0) node = node.left;
            else node = node.right;
        }
        return null;
    }
    /**
     * Retorna el nodo con el valor máximo en el árbol.
     *
     * @return el nodo con el valor máximo
     */
    public TreeNode<E> max() {
        if (this.root == null) return null;
        TreeNode<E> node = this.root;
        while (node.right != null) node = node.right;
        return node;
    }
    /**
     * Retorna el nodo con el valor mínimo en el árbol.
     *
     * @return el nodo con el valor mínimo
     */
    public TreeNode<E> min() {
        if (this.root == null) return null;
        TreeNode<E> node = this.root;
        while (node.left != null) node = node.left;
        return node;
    }
    /**
     * Retorna una representación en forma de cadena del árbol.
     * La cadena contiene los elementos del árbol en una estructura jerárquica.
     *
     * @return una representación en forma de cadena del árbol
     */
    public String toString() {return toString(this.root);}
    /**
     * Retorna una representación en forma de cadena del árbol a partir del nodo especificado.
     *
     * @param node el nodo desde el cual construir la cadena
     * @return una representación en forma de cadena del árbol
     */
    public String toString(TreeNode<E> node) {
        if (node == null) return "Empty Tree";
        StringBuilder sb = new StringBuilder();
        toStringTraversal(node, sb, 1);
        return sb.toString();
    }
    /**
     * Realiza una recorrida en profundidad para construir la representación en forma de cadena del árbol.
     *
     * @param node el nodo actual
     * @param sb el StringBuilder para construir la cadena
     * @param depth la profundidad actual
     */
    public void toStringTraversal(TreeNode<E> node, StringBuilder sb, int depth) {
        sb.append("\n");
        for (int i = 0; i < depth; ++i) sb.append("     ");
        if (node == null) {sb.append("null"); return;}
        sb.append(node.val);
        toStringTraversal(node.left, sb, depth + 1);
        toStringTraversal(node.right, sb, depth + 1);
    }
    /**
     * Retorna un iterador para recorrer el árbol en inorden
     *
     * @return un iterador para recorrer el árbol en inorden
     */
    public Iterator<E> inorderIterator(){return new InorderIterator<>(this.root);}
    /**
     * Retorna un iterador para recorrer el árbol en preorden.
     *
     * @return un iterador para recorrer el árbol en preorden
     */
    public Iterator<E> preorderIterator(){return new PreorderIterator<>(this.root);}
    /**
     * Retorna un iterador para recorrer el árbol en postorden.
     *
     * @return un iterador para recorrer el árbol en postorden
     */
    public Iterator<E> postorderIterator(){return new PostorderIterator<>(this.root);}
    /**
     * Retorna un iterador para recorrer el árbol por defecto, en este caso por niveles
     *
     * @return un iterador para recorrer el árbol por defecto, en este caso por niveles
     */
    public Iterator<E> iterator() {
        //return new InorderIterator<>(this.root);
        //return new PreorderIterator<>(this.root);
        //return new PostorderIterator<>(this.root);
        return new LevelorderIterator<>(this.root);
    }
    /**
     * Clase que implementa un iterador en inorden para el árbol de búsqueda binaria.
     *
     * @param <E> el tipo de elemento que contiene el árbol, el cual debe ser comparable
     */
    private static class InorderIterator<E extends Comparable<E>> implements Iterator<E> {
        Deque<TreeNode<E>> stack = new ArrayDeque<>();
        public InorderIterator(TreeNode<E> root) {diveLeft(root);}
        private void diveLeft(TreeNode<E> node){
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        public boolean hasNext() {return !stack.isEmpty();}
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            TreeNode<E> node = stack.pop();
            diveLeft(node.right);
            return node.val;
        }
    }
    /**
     * Clase que implementa un iterador en preorden para el árbol de búsqueda binaria.
     *
     * @param <E> el tipo de elemento que contiene el árbol, el cual debe ser comparable
     */
    private static class PreorderIterator<E extends Comparable<E>> implements Iterator<E> {
        Deque<TreeNode<E>> stack = new ArrayDeque<>();
        public PreorderIterator(TreeNode<E> root) {
            stack.push(root);
        }
        public boolean hasNext() {return !stack.isEmpty();}
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            TreeNode<E> node = stack.pop();
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
            return node.val;
        }
    }
    /**
     * Clase que implementa un iterador en postorden para el árbol de búsqueda binaria.
     *
     * @param <E> el tipo de elemento que contiene el árbol, el cual debe ser comparable
     */
    private static class PostorderIterator<E extends Comparable<E>> implements Iterator<E> {
        Deque<TreeNode<E>> stack = new ArrayDeque<>();
        public PostorderIterator(TreeNode<E> root) {stack.push(root);}
        public boolean hasNext() {return !stack.isEmpty();}
        public E next() {
            while (!stack.isEmpty()) {
                TreeNode<E> node = stack.pop();
                if (node.left == null && node.right == null) return node.val;
                stack.push(new TreeNode<>(node.val));
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
            }
            throw new NoSuchElementException();
        }
    }
    /**
     * Clase que implementa un iterador en nivel para el árbol de búsqueda binaria.
     *
     * @param <E> el tipo de elemento que contiene el árbol, el cual debe ser comparable
     */
    private static class LevelorderIterator<E extends Comparable<E>> implements Iterator<E> {
        Deque<TreeNode<E>> queue = new ArrayDeque<>();
        public LevelorderIterator(TreeNode<E> root) {queue.add(root);}
        public boolean hasNext() {return !queue.isEmpty();}
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            TreeNode<E> node = queue.poll();
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
            return node.val;
        }
    }
}