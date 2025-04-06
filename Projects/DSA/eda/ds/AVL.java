package eda.ds;

import java.util.*;
/**
 * <h3>AVL - Árbol AVL (Auto-Balanceado, Georgii Adelson-Velskii y Yevgeniy Landis)</h3>
 * Implementación de un árbol AVL, una variante de árbol de búsqueda binaria que se auto-balancea.
 * Esta clase proporciona funcionalidades para insertar, eliminar, obtener y buscar elementos en el árbol.
 * <p>
 *El árbol AVL toma su nombre de las iniciales de los apellidos de sus inventores, Georgii Adelson-Velskii y Yevgeniy Landis. Lo dieron a conocer en la publicación de un artículo en 1962, «An algorithm for the organization of information» («Un algoritmo para la organización de la información»).
 * Los árboles AVL están siempre equilibrados de tal modo que para todos los nodos, la altura de la rama izquierda no difiere en más de una unidad de la altura de la rama derecha o viceversa. Gracias a esta forma de equilibrio (o balanceo), la complejidad de una búsqueda en uno de estos árboles se mantiene siempre en orden de complejidad O(log n). El factor de equilibrio puede ser almacenado directamente en cada nodo o ser computado a partir de las alturas de los subárboles.
 * Para conseguir esta propiedad de equilibrio, la inserción y el borrado de los nodos se ha de realizar de una forma especial. Si al realizar una operación de inserción o borrado se rompe la condición de equilibrio, hay que realizar una serie de rotaciones de los nodos.
 * <p>
 * Costes de los métodos:
 * <ul>
 *     <li>insert: O(log N), donde N es el tamaño actual del árbol. La inserción se realiza garantizando el balance del árbol.</li>
 *     <li>delete: O(log N), la eliminación de un nodo puede requerir rebalancear el árbol.</li>
 *     <li>search: O(log N), donde N es el tamaño actual del árbol. La búsqueda de un elemento se realiza de manera eficiente gracias a la estructura balanceada.</li>
 *     <li>max/min: O(log N), donde N es el tamaño actual del árbol. Encontrar el valor máximo o mínimo es rápido debido a la estructura del árbol.</li>
 * </ul>
 * <p>
 * Complejidad espacial de todas las operaciones: O(1).
 *
 * @param <E> el tipo de elementos que se almacenan en el árbol, los cuales deben ser comparables
 * @author Marcos del Amo Fernández y Gabriela Potenciano Carpintero
 * @version Práctica 2 - Parte 2 - Estructuras de Datos y Algoritmos
 */
public class AVL<E extends Comparable<E>> implements Iterable<E> {
    public TreeNode<E> root;
    public int size;
    /**
     * Constructor que crea un árbol AVL vacío.
     */
    public AVL(){this(null);}
    /**
     * Constructor que crea un árbol AVL a partir de un nodo raíz dado.
     *
     * @param root el nodo raíz del árbol
     */
    public AVL(TreeNode<E> root) {
        this.root = root;
        this.size = 0;
        if (this.root==null) return;
        for (E val:this) this.size++;
    }
    /**
     * Clase que representa un nodo en el árbol AVL.
     *
     * @param <E> el tipo de elemento que contiene el nodo, el cual debe ser comparable
     */
    public static class TreeNode<E> {
        public E val;
        TreeNode<E> left;
        TreeNode<E> right;
        int height;
        /**
         * Constructor que inicializa un nodo con un valor, subárboles izquierdo y derecho, y altura dados.
         *
         * @param val    el valor del nodo
         * @param left   el subárbol izquierdo
         * @param right  el subárbol derecho
         * @param height la altura del nodo
         */
        public TreeNode(E val, TreeNode<E> left, TreeNode<E> right, int height) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.height = height;
        }
        /**
         * Constructor que inicializa un nodo con un valor dado y sin subárboles.
         *
         * @param val el valor del nodo
         */
        public TreeNode(E val) {this(val, null, null, 0);}
    }
    /**
     * Retorna el tamaño del árbol.
     *
     * @return el tamaño del árbol
     */
    public int getSize() {return this.size;}
    /**
     * Agrega un valor al árbol, asegurando que el árbol se mantenga balanceado.
     *
     * @param val el valor a agregar
     */
    public void add(E val) {
        if (val == null) return;
        this.size++;
        if (this.root == null) {
            this.root = new TreeNode<>(val);
            return;
        }
        TreeNode<E> parent = null, node = this.root;
        Deque<TreeNode<E>> stack = new ArrayDeque<>();
        while (node != null) {
            parent = node;
            stack.push(parent);
            if (val.compareTo(node.val) > 0) node = node.right;
            else node = node.left;
        }
        if (val.compareTo(parent.val) < 0) parent.left = new TreeNode<>(val);
        else parent.right = new TreeNode<>(val);
        rebalance(stack);
    }
    /**
     * Elimina un valor del árbol, asegurando que el árbol se mantenga balanceado.
     *
     * @param val el valor a eliminar
     */
    public void delete(E val) {
        if (val == null || this.root == null) return;
        this.size--;
        TreeNode<E> parent = null;
        TreeNode<E> node = this.root;
        Deque<TreeNode<E>> stack = new ArrayDeque<>();
        while (node != null && !node.val.equals(val)) {
            parent = node;
            stack.push(parent);
            if (val.compareTo(node.val) > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        if (node == null) return;
        if (node.left == null || node.right == null) {
            TreeNode<E> child = (node.left != null) ? node.left : node.right;
            if (parent == null) {
                this.root = child;
            } else if (parent.left == node) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        } else {
            TreeNode<E> successor = node.right;
            TreeNode<E> successorParent = node;
            stack.push(successorParent);
            while (successor.left != null) {
                successorParent = successor;
                stack.push(successorParent);
                successor = successor.left;
            }
            node.val = successor.val;
            if (successorParent == node) {
                successorParent.right = successor.right;
            } else {
                successorParent.left = successor.right;
            }
        }
        rebalance(stack);
    }
    /**
     * Rebala el árbol AVL después de una inserción o eliminación.
     *
     * @param stack la pila de nodos que necesitan ser balanceados
     */
    private void rebalance(Deque<TreeNode<E>> stack) {
        while (!stack.isEmpty()) {
            TreeNode<E> node = stack.pop();
            updateHeight(node);
            int balanceFactor = getBalanceFactor(node);
            if (balanceFactor > 1) {
                if (getBalanceFactor(node.left) < 0)
                    node.left = rotateLeft(node.left);
                node = rotateRight(node);
            } else if (balanceFactor < -1) {
                if (getBalanceFactor(node.right) > 0)
                    node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
            if (stack.isEmpty()) this.root = node;
            else {
                TreeNode<E> parent = stack.peek();
                if (parent.val.compareTo(node.val) > 0) parent.left = node;
                else parent.right = node;
            }
        }
    }
    /**
     * Realiza una rotación hacia la izquierda en el subárbol enraizado en el nodo dado.
     *
     * @param X el nodo raíz del subárbol a rotar
     * @return el nuevo nodo raíz del subárbol rotado
     */
    private TreeNode<E> rotateLeft(TreeNode<E> X) {
        TreeNode<E> Y = X.right;
        X.right = Y.left;
        Y.left = X;
        updateHeight(X);
        updateHeight(Y);
        return Y;
    }
    /**
     * Realiza una rotación hacia la derecha en el subárbol enraizado en el nodo dado.
     *
     * @param X el nodo raíz del subárbol a rotar
     * @return el nuevo nodo raíz del subárbol rotado
     */
    private TreeNode<E> rotateRight(TreeNode<E> X) {
        TreeNode<E> Y = X.left;
        X.left = Y.right;
        Y.right = X;
        updateHeight(X);
        updateHeight(Y);
        return Y;
    }
    /**
     * Retorna la altura del nodo dado.
     *
     * @param node el nodo cuya altura se quiere obtener
     * @return la altura del nodo, o -1 si el nodo es null
     */
    private int height(TreeNode<E> node) {return (node == null) ? -1 : node.height;}
    /**
     * Actualiza la altura del nodo dado.
     *
     * @param node el nodo cuya altura se quiere actualizar
     */
    private void updateHeight (TreeNode<E> node) {node.height = Math.max(height(node.left), height(node.right)) + 1;}
    /**
     * Retorna el factor de balance del nodo dado.
     *
     * @param node el nodo cuyo factor de balance se quiere obtener
     * @return el factor de balance del nodo
     */
    private int getBalanceFactor(TreeNode<E> node) {return  height(node.left) - height(node.right);}
    /**
     * Busca un valor en el árbol.
     *
     * @param val el valor a buscar
     * @return el nodo que contiene el valor, o null si el valor no se encuentra
     */
    public TreeNode<E> search(E val) {
        TreeNode<E> node = this.root;
        while (node != null) {
            if (val.compareTo(node.val) > 0){
                if (node.right != null && node.right.val == val) return node.right;
                else node = node.right;
            } else {
                if (node.left != null && node.left.val == val) return node.left;
                else node = node.left;
            }
        }
        return null;
    }
    /**
     * Retorna el nodo con el valor máximo en el árbol.
     *
     * @return el nodo con el valor máximo, o null si el árbol está vacío
     */
    public TreeNode<E> max() {
        TreeNode<E> node = this.root;
        while (node.right != null) node = node.right;
        return node;
    }
    /**
     * Retorna el nodo con el valor mínimo en el árbol.
     *
     * @return el nodo con el valor mínimo, o null si el árbol está vacío
     */
    public TreeNode<E> min() {
        TreeNode<E> node = this.root;
        while (node.left != null) node = node.left;
        return node;
    }
    /**
     * Retorna una representación en forma de cadena del árbol.
     *
     * @return una cadena que representa el árbol
     */
    public String toString() {return toString(this.root);}
    public String toString(TreeNode<E> node) {
        if (node == null) return "Empty Tree (NIL | ø)";
        StringBuilder sb = new StringBuilder();
        toStringTraversal(node, sb, 1);
        return sb.toString();
    }
    public void toStringTraversal(TreeNode<E> node, StringBuilder sb, int depth) {
        sb.append("\n");
        for (int i = 0; i < depth; ++i) sb.append("             ");
        if (node == null) { sb.append("NIL | ø");  return; }
        sb.append("(")
                .append(node.val)
                .append(")")
                .append(" ~ ")
                .append("(H:")
                .append(node.height)
                .append("|F:")
                .append(getBalanceFactor(node))
                .append(")");
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
        public InorderIterator(TreeNode<E> root) { diveLeft(root);}
        private void diveLeft(TreeNode<E> node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        public boolean hasNext() {return !this.stack.isEmpty();}
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
        public PreorderIterator(TreeNode<E> root) {stack.push(root);}
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
        public boolean hasNext(){return !stack.isEmpty(); }
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
        public LevelorderIterator(TreeNode<E> root) { queue.add(root);}
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