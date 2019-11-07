package binarySearchTree;

public class BinarySearchTree <T extends Comparable> {
    Node<T> root;

    private Node<T> add(Node<T> curNode, T value) {
        if (curNode == null) {
            return new Node<T> (value);
        }

        if (value.compareTo(curNode.value) < 0) {
            curNode.left = add(curNode.left, value);
        } else if (value.compareTo(curNode.value) > 0) {
            curNode.right = add(curNode.right, value);
        }

        return curNode;
    }
    public void add(T value) {
        root = add(root, value);
    }

    private T findSmallestValue(Node<T> root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }
    private Node<T> delete(Node<T> curNode, T value) {
        if (curNode == null) {
            return null;
        }

        if (value.compareTo(curNode.value) == 0) {
            if (curNode.left == null && curNode.right == null) return null;
            if (curNode.left == null) return curNode.right;
            if (curNode.right == null) return curNode.left;

            T valueToReplace = findSmallestValue(curNode.right);
            curNode.value = valueToReplace;
            curNode.right = delete(curNode.right, valueToReplace);
            return curNode;
        }
        if (value.compareTo(curNode.value) < 0) {
            curNode.left = delete(curNode.left, value);
            return curNode;
        }
        curNode.right = delete(curNode.right, value);
        return curNode;
    }
    public void delete(T value) {
        root = delete(root, value);
    }

    private Node<T> findNode(T value, Node<T> curNode) {
        if (curNode == null) return null;
        if (curNode.value.compareTo(value) < 0) return findNode(value, curNode.right);
        if (curNode.value.compareTo(value) > 0) return findNode(value, curNode.left);
        return curNode;
    }
    private Node<T> findNode(T value) {
        return findNode(value, root);
    }
    public boolean find(T value) {
        return findNode(value) == null? false : true;
    }

    private void NLR(Node<T> curNode) {
        if (curNode == null) return;

        System.out.print(curNode.value);
        System.out.print(" ");

        NLR(curNode.left);
        NLR(curNode.right);
    }
    public void NLR() { NLR(root); }

    private void LNR(Node<T> curNode) {
        if (curNode == null) return;

        LNR(curNode.left);

        System.out.print(curNode.value);
        System.out.print(" ");

        LNR(curNode.right);
    }
    public void LNR() { LNR(root); }

    private void LRN(Node<T> curNode) {
        if (curNode == null) return;

        LRN(curNode.left);
        LRN(curNode.right);

        System.out.print(curNode.value);
        System.out.print(" ");
    }
    public void LRN() { LRN(root); }
}
