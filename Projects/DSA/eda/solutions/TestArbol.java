package eda.solutions;

import eda.ds.AVL;
import eda.ds.BST;
import java.util.Iterator;

public class TestArbol {
    private static void add_print(Integer val, BST<Integer> bst) {
        System.out.println("Inserting val = " + val + " to the BST ...");
        bst.add(val);
        System.out.println("BST:\n" + bst + "\n SIZE = " + bst.getSize());
    }
    private static void add_print(Integer val, AVL<Integer> avl) {
        System.out.println("Inserting val = " + val + " to the AVL ...");
        avl.add(val);
        System.out.println("AVL: \n" + avl + "\n SIZE = " + avl.getSize());
    }
    private static void delete_print(Integer val, BST<Integer> bst) {
        System.out.println("Deleting val = " + val + " in the BST ...");
        bst.delete(val);
        System.out.println("BST:\n" + bst + "\n SIZE = " + bst.getSize());
    }
    private static void delete_print(Integer val, AVL<Integer> avl) {
        System.out.println("Deleting val = " + val + " in the AVL ...");
        avl.delete(val);
        System.out.println("AVL: \n" + avl + "\n SIZE = " + avl.getSize());
    }
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        AVL<Integer> avl = new AVL<>();
        System.out.println("________________________________________________________");
        System.out.println("\n*** BST Test ***");
        System.out.println("BST INSERTION TEST") ;
        add_print(null, bst);
        add_print(5, bst);
        add_print(2, bst);
        add_print(1, bst);
        add_print(0, bst);
        add_print(3, bst);
        add_print(4, bst);
        add_print(8, bst);
        add_print(6, bst);
        add_print(7, bst);
        add_print(10, bst);
        add_print(9, bst);
        System.out.println("________________________________________________________");
        System.out.println("Final length of the BST is : " + bst.getSize());
        System.out.println("Searching for the element 20: " + bst.search(20));
        System.out.println("Maximum element of the BST: " + bst.max().val);
        System.out.println("Minimum element of the BST: " + bst.min().val);
        System.out.println("BST ITERATION TEST");
        System.out.println("Inorder:");
        Iterator<Integer> inorder_bst = bst.inorderIterator();
        while (inorder_bst.hasNext()) {
            System.out.println(inorder_bst.next());
        }
        System.out.println("Postorder:");
        Iterator<Integer> postorder_bst = bst.postorderIterator();
        while (postorder_bst.hasNext()) {
            System.out.println(postorder_bst.next());
        }
        System.out.println("Preorder:");
        Iterator<Integer> preorder_bst= bst.preorderIterator();
        while (preorder_bst.hasNext()) {
            System.out.println(preorder_bst.next());
        }
        System.out.println("Levelorder:");
        System.out.println();
        for (Integer i : bst) {
            System.out.println(i);
        }
        System.out.println("________________________________________________________");
        System.out.println("BST DELETION TEST") ;
        delete_print(null, bst);
        delete_print(5, bst);
        delete_print(2, bst);
        delete_print(1, bst);
        delete_print(0, bst);
        delete_print(3, bst);
        delete_print(4, bst);
        delete_print(8, bst);
        delete_print(6, bst);
        delete_print(7, bst);
        delete_print(10, bst);
        delete_print(9, bst);
        System.out.println("________________________________________________________");
        System.out.println("\n*** AVL Test ***\n");
        System.out.println("AVL INSERTION TEST") ;
        add_print(null, avl);
        add_print(5, avl);
        add_print(2, avl);
        add_print(1, avl);
        add_print(0, avl);
        add_print(3, avl);
        add_print(4, avl);
        add_print(8, avl);
        add_print(6, avl);
        add_print(7, avl);
        add_print(10, avl);
        add_print(9, avl);
        System.out.println("Final length of the AVL is : " + avl.getSize());
        System.out.println("Searching for the element 20: " + avl.search(20));
        System.out.println("Maximum element of the AVL: " + avl.max().val);
        System.out.println("Minimum element of the AVL: " + avl.min().val);
        System.out.println("AVL ITERATION TEST");
        System.out.println("Inorder:");
        Iterator<Integer> inorder_avl = avl.inorderIterator();
        while (inorder_avl.hasNext()) {
            System.out.println(inorder_avl.next());
        }
        System.out.println("Postorder:");
        Iterator<Integer> postorder_avl = avl.postorderIterator();
        while (postorder_avl.hasNext()) {
            System.out.println(postorder_avl.next());
        }
        System.out.println("Preorder:");
        Iterator<Integer> preorder_avl = avl.preorderIterator();
        while (preorder_avl.hasNext()) {
            System.out.println(preorder_avl.next());
        }
        System.out.println("Levelorder:");
        System.out.println();
        for (Integer i : avl) {
            System.out.println(i);
        }
        System.out.println("________________________________________________________");
        System.out.println("AVL DELETION TEST");
        delete_print(null, avl);
        delete_print(5, avl);
        delete_print(2, avl);
        delete_print(1, avl);
        delete_print(0, avl);
        delete_print(3, avl);
        delete_print(4, avl);
        delete_print(8, avl);
        delete_print(6, avl);
        delete_print(7, avl);
        delete_print(10, avl);
        delete_print(9, avl);
        System.out.println("________________________________________________________");
    }
}
