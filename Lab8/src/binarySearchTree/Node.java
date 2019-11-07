package binarySearchTree;

public class Node <T extends Comparable> {
    Node<T> left, right, parent;
    T value;

    Node (T value) {
        this.value = value;
    }
}
