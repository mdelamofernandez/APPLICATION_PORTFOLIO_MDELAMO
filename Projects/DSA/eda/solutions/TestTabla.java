package eda.solutions;

import eda.adt.Dictionary;
import eda.ds.HashTable;

import java.util.Iterator;

public class TestTabla {
    public static void main(String[] args) {
        Dictionary<String, Integer> hashTable = new HashTable<>(1);
        System.out.println(hashTable);
        hashTable.put("clave1", 1);
        System.out.println(hashTable);
        hashTable.put("clave2", 2);
        System.out.println(hashTable);
        hashTable.put("clave3", 3);
        hashTable.put("clave1", 10);
        System.out.println(hashTable);

        System.out.println("clave1: " + hashTable.get("clave1"));
        System.out.println("clave2: " + hashTable.get("clave2"));
        System.out.println(hashTable);

        System.out.println("¿Contiene clave3? " + hashTable.contains("clave3"));

        hashTable.remove("clave2");
        System.out.println(hashTable);

        System.out.println("¿Contiene clave2 después de eliminar? " + hashTable.contains("clave2"));

        System.out.println("Tamaño: " + hashTable.size());
        System.out.println("¿Está vacía? " + hashTable.isEmpty());

        Iterator<String> it = hashTable.iterator();

        System.out.println(hashTable);

        System.out.println("Elementos en la tabla hash:" + it.hasNext());

        while (it.hasNext()) {
            String key = it.next();
            System.out.println("Clave: " + key + ", Valor: " + hashTable.get(key));
        }
        System.out.println();
        for (String key:hashTable){
            System.out.println(key);
        }

        hashTable.clear();
        System.out.println("¿Está vacía después de limpiar? " + hashTable.isEmpty());

    }
}