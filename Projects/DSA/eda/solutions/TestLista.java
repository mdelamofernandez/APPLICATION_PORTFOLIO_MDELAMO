package eda.solutions;

import eda.adt.List;
import eda.ds.ListImpl;
import eda.exceptions.WrongIndexException;

import java.util.Iterator;

public class TestLista {
    public static void main(String[] args) throws WrongIndexException {

        List<String> lista = new ListImpl<>();
        lista.insert(0, "Elemento1");
        lista.insert(1, "Elemento2");
        lista.insert(2, "Elemento3");
        lista.insert(3, "Elemento4");

        System.out.println("Lista después de inserciones: " + lista);

        lista.delete(1);
        System.out.println("Lista después de eliminar el elemento en posición 1: " + lista);
        System.out.println("Elemento en posición 0: " + lista.get(0));
        System.out.println("Elemento en posición 1: " + lista.get(1));

        System.out.println(lista);
        System.out.println("Índice de 'Elemento1': " + lista.search("Elemento1"));
        System.out.println("Índice de 'Elemento2': " + lista.search("Elemento2"));
        System.out.println("Índice de 'Elemento3': " + lista.search("Elemento3"));
        System.out.println("Índice de 'Elemento4': " + lista.search("Elemento4"));

        System.out.println("Iterando sobre la lista:");

        for(String s:lista){
            System.out.println(s);
        }

        System.out.println("Añadimos elementos hasta tener del 1-10");
        lista.insert(1, "Elemento2");
        lista.insert(4, "Elemento5");
        lista.insert(5, "Elemento6");
        lista.insert(6, "Elemento7");
        lista.insert(7, "Elemento8");
        lista.insert(8, "Elemento9");
        lista.insert(9, "Elemento10");



        Iterator<String> it = lista.iterator();
        while(it.hasNext()){
            String s = it.next();
            System.out.println(s);
        }
        System.out.println("La talla de la lista es " + lista.size());
        System.out.println(lista);
        System.out.println("Elemento de la poscion 4: " + lista.get(4));
        System.out.println("Elemento de la poscion 6: " + lista.get(6));
        System.out.println("Elemento de la poscion 8: " + lista.get(8));


    }
}
