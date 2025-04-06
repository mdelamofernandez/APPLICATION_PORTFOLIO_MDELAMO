package eda.ds;

import eda.adt.List;
import eda.exceptions.WrongIndexException;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**<h3>ListImpl - Lista Doblemente Enlazada
 * </h3>
 * Implementación de una lista doblemente enlazada.
 * Esta clase implementa la interfaz List y proporciona funcionalidades para
 * insertar, eliminar, obtener y buscar elementos en la lista.
 * <p>
 * Se ha elegido una implementación doblemente enlazada debido a su eficiencia
 * en operaciones de inserción y eliminación en cualquier posición de la lista.
 * La estructura de datos permite un acceso bidireccional a los elementos, lo que
 * facilita la navegación tanto hacia adelante como hacia atrás en la lista. Para ello
 * se ha hecho uso de dos nodos centinela head y tail.
 * <p><p>
 Costes de los métodos:
 * <ul>
 *     <li>insert: O(min(k, N - k)), donde k es el índice de la posición de inserción
 *     y N es el tamaño actual de la lista. La inserción puede requerir recorrer
 *     la lista hasta la posición deseada.</li>
 *     <li>delete: O(min(k, N - k)), la eliminación de un elemento se realiza en tiempo constante
 *     al tener acceso directo al nodo anterior y al siguiente.</li>
 *     <li>get: O(min(k, N - k), donde k es la posición de inserción y n es el tamaño actual de la lista. La búsqueda del elemento
 *     requerirá recorrer la lista hasta la posición deseada.</li>
 *     <li>search: O(min(k, N - k)), donde k es el índice de la posición de inserción
 *     y N es el tamaño actual de la lista. La búsqueda del elemento requerirá recorrer
 *     la lista hasta encontrarlo o llegar al final.</li>
 * </ul>
 * <p>
 * Complejidad espacial de todas las operaciones: O(1).
 *
 * @param <E> el tipo de elementos que se almacenan en la lista
 * @author Marcos del Amo Fernández
 * @version Práctica 1 - Parte 1 - Estructuras de Datos y Algoritmos
 */

public class ListImpl <E> implements List<E>
{
    private int size = 0;
    private final Node<E> head, tail;
    /**
     * Constructor de la lista que inicializa la cabeza y la cola con nodos nulos.
     * También establece el tamaño de la lista como 0.
     */
    public ListImpl() {
        head = new Node<>(null);
        tail = new Node<>(null);
        head.next = tail;
        tail.prev = head;
        this.size = 0;
    }
    /**
     * Clase que representa un nodo en una lista doblemente enlazada.
     * Cada nodo contiene un dato y referencias al nodo anterior y al siguiente.
     *
     * @param <E> el tipo de dato almacenado en el nodo
     */
    private static class Node<E>
    {
        E data;
        Node<E> next;
        Node<E> prev;

        /**
         * Constructor de un nodo que inicializa el dato y las referencias a null.
         *
         * @param data el dato a almacenar en el nodo
         */
        Node(E data) {this(data, null, null); }
        /**
         * Constructor de un nodo que inicializa el dato y las referencias especificadas.
         *
         * @param data el dato a almacenar en el nodo
         * @param prev la referencia al nodo anterior
         * @param next la referencia al nodo siguiente
         */
        Node(E data, Node<E> prev, Node<E> next) {
            this.data = data; this.prev = prev; this.next = next;
        }
    }
    /**
     * Obtiene el nodo en la posición especificada de la lista.
     * Este método busca el nodo por su índice y retorna una referencia a él.
     *
     * @param pos la posición del nodo a obtener
     * @return el nodo en la posición especificada
     */
    private Node<E> getNode(int pos) {
        Node<E> current;
        if (pos < size / 2) {
            current = head.next;
            for (int i = 0; i < pos; i++) {
                current = current.next;
            }
        } else {
            current = tail.prev;
            for (int i = size - 1; i > pos; i--) {
                current = current.prev;
            }
        }
        return current;
    }
    /**
     * Inserta un elemento en la posición especificada de la lista.
     * Si la posición es menor que 0 o mayor que el tamaño de la lista,
     * se lanza una excepción WrongIndexException.
     *
     * @param pos la posición en la que insertar el elemento
     * @param data el elemento a insertar
     * @throws WrongIndexException si la posición es inválida
     */
    @Override
    public void insert(int pos, E data) throws WrongIndexException {
        if (pos < 0 || pos > size) throw new WrongIndexException("Index " + pos + " is invalid. ");
        Node<E> prev;
        if (pos == size) {
            prev = tail.prev;
        } else {
            prev = getNode(pos - 1);
        }
        Node<E> succ = prev.next;
        Node<E> newNode = new Node<>(data, prev, succ);
        prev.next = newNode;
        succ.prev = newNode;
        size++;
    }
    /**
     * Elimina el elemento en la posición especificada de la lista.
     * Si la posición es menor que 0 o igual o mayor que el tamaño de la lista,
     * se lanza una excepción WrongIndexException.
     *
     * @param pos la posición del elemento a eliminar
     * @throws WrongIndexException si la posición es inválida
     */
    @Override
    public void delete(int pos) throws WrongIndexException {
        if (pos < 0 || pos >= size) throw new WrongIndexException("Index " + pos + " is invalid. ");
        Node<E> toDelete = getNode(pos);
        Node<E> prev = toDelete.prev;
        Node<E> succ = toDelete.next;
        prev.next = succ;
        if (succ != null) {
            succ.prev = prev;
        } else {
            tail.prev = prev;
        }
        size--;

    }
    /**
     * Obtiene el elemento en la posición especificada de la lista.
     * Si la posición es menor que 0 o mayor o igual que el tamaño de la lista,
     * se lanza una excepción WrongIndexException.
     *
     * @param pos la posición del elemento a obtener
     * @return el elemento en la posición especificada
     * @throws WrongIndexException si la posición es inválida
     */
    @Override
    public E get(int pos) throws WrongIndexException {
        if (pos < 0 || pos >= size) throw new WrongIndexException("Index " + pos + " is invalid. ");
        Node<E> aux = getNode(pos);
        return aux.data;
    }
    /**
     * Busca el índice de la primera aparición del elemento especificado en la lista.
     * Si el elemento no se encuentra en la lista, retorna -1.
     *
     * @param data el elemento a buscar
     * @return el índice de la primera aparición del elemento, o -1 si no se encuentra
     */
    @Override
    public int search(E data) {
        Node<E> current = head.next;
        int index = 0;
        while (current != tail) {
            if (current.data != null && current.data.equals(data)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }
    /**
     * Retorna un iterador sobre los elementos de la lista.
     *
     * @return un iterador sobre los elementos de la lista
     */
    @Override
    public Iterator<E> iterator() {
        return new CIterator();
    }
    /**
     * Retorna el número de elementos en la lista.
     *
     * @return el número de elementos en la lista
     */
    @Override
    public int size() {
        return this.size;
    }
    /**
     * Retorna una representación en forma de cadena de la lista.
     * La cadena contiene los elementos de la lista separados por flechas (->).
     *
     * @return una representación en forma de cadena de la lista
     */
    @Override
    public String toString() {
        if (head.next == tail) return "[]";
        StringBuilder result = new StringBuilder("[");
        Node<E> aux = head.next;
        for (int i = 0; i < size - 1; ++i){
            result.append(aux.data).append(" -> ");
            aux = aux.next;
        }
        result.append(aux.data).append("]");
        return result.toString();
    }
    /**
     * Clase interna privada que implementa un iterador sobre los elementos de la lista.
     */
    private class CIterator implements Iterator<E>
    {
        private Node<E> current;
        /**
         * Constructor del iterador que inicializa el nodo actual.
         */
        public CIterator() {
            current = head.next ;
        }
        /**
         * Verifica si hay más elementos en la lista.
         *
         * @return true si hay más elementos, false de lo contrario
         */
        @Override
        public boolean hasNext() {
            return current != null && current != tail;
        }
        /**
         * Retorna el siguiente elemento en la lista.
         *
         * @return el siguiente elemento en la lista
         * @throws NoSuchElementException si no hay más elementos en la lista
         */
        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E data = current.data;
            current = current.next;
            return data;
        }
    }
}
