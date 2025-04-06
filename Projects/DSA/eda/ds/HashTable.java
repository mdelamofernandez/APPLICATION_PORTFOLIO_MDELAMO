package eda.ds;
import eda.adt.Dictionary;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
/**<h3>HashTable - Con encadenamiento de entradas de tabla de pares clave valor enlazados
 * </h3>
 * Implementación de una tabla hash utilizando encadenamiento para resolver colisiones.
 * Esta clase implementa la interfaz Dictionary y proporciona funcionalidades para
 * agregar, eliminar, obtener y buscar elementos en la tabla hash.
 * <p>
 * Se ha elegido la resolución de colisiones mediante encadenamiento debido a su
 * simplicidad y eficiencia en términos de memoria y tiempo de acceso.
 * <p>
 * Costes de los métodos:
 * <ul>
 *     <li>put: O(1) en promedio, con un peor caso de O(n) si se produce un rehashing.</li>
 *     <li>get: O(1) en promedio, con un peor caso de O(n) si se produce un rehashing.</li>
 *     <li>remove: O(1) en promedio, con un peor caso de O(n) si se produce un rehashing.</li>
 *     <li>contains: O(1) en promedio, con un peor caso de O(n) si se produce un rehashing.</li>
 *     <li>size: O(1).</li>
 *     <li>isEmpty: O(1).</li>
 *     <li>clear: O(1).</li>
 *     <li>iterator: O(n), donde n es el número de elementos en la tabla hash.</li>
 * </ul>
 * <p>
 * La tabla hash realiza un rehashing cuando el factor de carga supera el umbral predefinido (0.75).
 * El rehashing implica duplicar el tamaño de la tabla y reasignar todas las entradas existentes
 * a las nuevas posiciones de la tabla, lo que puede llevar a un aumento temporal en la complejidad
 * de las operaciones.
 *
 * @param <K> el tipo de las claves almacenadas en la tabla hash
 * @param <V> el tipo de los valores asociados a las claves en la tabla hash
 * @author Marcos del Amo Fernández
 * @version Práctica 1 - Parte 2 - Estructuras de Datos y Algoritmos
 * @see Dictionary
 */
public class HashTable<K, V> implements Dictionary<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private TableEntry<K, V>[] table;
    private int n = 0;
    @SuppressWarnings("unchecked")
    public HashTable(int capacity){
        table =(TableEntry<K, V>[]) new TableEntry[capacity];
    }
    public HashTable(){
        this(DEFAULT_CAPACITY);
    }
    /**
     * Clase interna que representa una entrada en la tabla hash.
     */
    private static class TableEntry<K, V> {
        K key;
        V value;
        TableEntry<K, V> next;
        TableEntry(K key, V value, TableEntry<K, V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
        /**
         * Compara esta entrada con otro objeto para verificar si son iguales.
         *
         * @param obj el objeto a comparar
         * @return true si las entradas son iguales, false de lo contrario
         */
        @SuppressWarnings("unchecked")
        public boolean equals(Object obj) {
            if (!(obj instanceof HashTable.TableEntry<?, ?>)) return false;
            TableEntry<K,V> ent = (TableEntry<K, V>) obj;
            return ent.key.equals(key);
        }
    }
    /**
     * Clase interna que implementa el iterador para la tabla hash.
     */
    private class CIterator implements Iterator<K>{
        private TableEntry<K, V> current;
        private int table_index;

        public CIterator() {
            this.table_index = -1;
            advance();
        }
        /**
         * Avanza al siguiente elemento en la tabla hash.
         */
        private void advance() {
            if (current != null && current.next != null) {
                current = current.next;
            } else {
                current = null;
                while (++table_index < table.length) {
                    if (table[table_index] != null) {
                        current = table[table_index];
                        break;
                    }
                }
            }
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public K next() {
            if (!hasNext()) throw new NoSuchElementException();
            K key = current.key;
            advance();
            return key;
        }
    }
    /**
     * Usaremos getEntry como método privado y auxiliar para obtener la entrada a tabla correspondiente.
     * Obtiene la entrada correspondiente a la clave especificada en la tabla hash.
     *
     * @param key la clave de la entrada que se desea obtener
     * @return la entrada correspondiente a la clave especificada, o null si no se encuentra
     */
    private TableEntry<K, V> getEntry(K key){
        int index = hash(key);
        if (table[index] == null) return null;
        else {
            TableEntry<K, V> aux = table[index];
            while (aux != null){
                if (aux.key.equals(key)) return aux;
                aux = aux.next;
            }
            return aux;
        }
    }
    /**
     * Agrega un par clave-valor a la tabla hash.
     *
     * @param key la clave a agregar
     * @param value el valor asociado a la clave
     * @return el valor anterior asociado a la clave, o null si la clave no estaba presente
     */
    public V put(K key, V value) {
        int index = hash(key);
        TableEntry<K, V> entry = getEntry(key);
        if (entry == null){
            this.n++;
            table[index] = new TableEntry<>(key, value, table[index]);
            if ((double)this.n/table.length > LOAD_FACTOR) resize(table.length*2);
            return null;
        } else {
            V result = entry.value;
            entry.key = key;
            entry.value = value;
            return result;
        }
    }
    /**
     * Obtiene el valor asociado a la clave especificada en la tabla hash.
     *
     * @param key la clave cuyo valor se desea obtener
     * @return el valor asociado a la clave especificada, o null si la clave no se encuentra en la tabla
     */
    public V get(K key) {
        TableEntry<K, V> entry = getEntry(key);
        if (entry == null) return null;
        else return entry.value;
    }
    /**
     * Elimina la entrada correspondiente a la clave especificada de la tabla hash.
     *
     * @param key la clave de la entrada que se desea eliminar
     * @return el valor asociado a la clave especificada, o null si la clave no se encuentra en la tabla
     */
    public V remove(K key) {
        int index = hash(key);
        TableEntry<K, V> entry = getEntry(key);
        if (entry != null) {
            if (entry == table[index]) {
                table[index] = entry.next;
            } else {
                TableEntry<K, V> prev = table[index];
                while (prev.next != entry) {
                    prev = prev.next;
                }
                prev.next = entry.next;
            }
            this.n--;
            return entry.value;
        }

        return null;
    }
    /**
     * Verifica si la tabla hash contiene una entrada con la clave especificada.
     *
     * @param key la clave cuya presencia se desea verificar en la tabla
     * @return true si la tabla contiene una entrada con la clave especificada, false de lo contrario
     */
    public boolean contains(K key) {
        return getEntry(key) != null;
    }
    /**
     * Retorna el número de entradas en la tabla hash.
     *
     * @return el número de entradas en la tabla hash
     */
    public int size(){
        return this.n;
    }
    /**
     * Verifica si la tabla hash está vacía.
     *
     * @return true si la tabla hash está vacía, false de lo contrario
     */
    public boolean isEmpty(){
        return n==0;
    }
    /**
     * Elimina todas las entradas de la tabla hash.
     */
    @SuppressWarnings("unchecked")
    public void clear(){
        table = (TableEntry<K, V>[])new TableEntry[table.length];
        n = 0;
    }
    /**
     * Retorna un iterador sobre las claves en la tabla hash.
     *
     * @return un iterador sobre las claves en la tabla hash
     */
    public Iterator<K> iterator(){
        return new CIterator();
    }
    /**
     * Retorna una representación de cadena de la tabla hash.
     *
     * @return una cadena que representa la tabla hash
     */
    public String toString() {
        if (n == 0) return "[]";
        StringBuilder result = new StringBuilder("[\n");
        for (TableEntry<K, V> entry : table) {
            result.append("    ");
            while (entry != null) {
                result.append("(")
                        .append(entry.key).append(":").append(entry.value)
                        .append(") -> ");
                entry = entry.next;
            }
            result.append("null,\n");
        }
        result.append("]");
        return result.toString();
    }
    /**
     * Calcula el índice hash para la clave especificada.
     *
     * @param key la clave para la cual se calculará el índice hash
     * @return el índice hash calculado para la clave especificada
     */
    private int hash(K key){
        return Math.abs(key.hashCode()) % table.length;
    }
    /**
     * Redimensiona la tabla hash al nuevo tamaño especificado.
     *
     * @param capacity el nuevo tamaño de la tabla hash
     */
    private void resize(int capacity){
        HashTable<K, V> new_table = new HashTable<>(capacity);
        for (TableEntry<K, V> entry:table){
            new_table.put(entry.key, entry.value);
        }
        this.table = new_table.table;
    }
}

Node<Integer> head;
head != head.val;

int[][] table;
for (int[] elemento : table[0]) {}

HashMap<Integer, String> map;
map != map.table;

Entry<K,V>[] table;

for (Entry<K,V> e : map.table) {

        }