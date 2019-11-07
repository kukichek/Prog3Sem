package tester;

import binarySearchTree.BinarySearchTree;

public class IntegerTester extends Tester {
    @Override
    public void test() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();

        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(1);
        tree.add(8);
        tree.add(9);
        tree.add(13);
        tree.add(14);
        tree.add(7);
        tree.add(18);
        tree.add(19);
        tree.add(11);
        tree.add(12);

        tree.delete(10);
        tree.delete(18);
        tree.delete(19);

        System.out.print("NLR: ");
        tree.NLR();
        System.out.println();

//        System.out.print("LNR: ");
//        tree.LNR();
//        System.out.println();
//
//        System.out.print("LRN: ");
//        tree.LRN();
//        System.out.println();

//        tree.delete(15);
//        tree.delete(10);
//        tree.delete(5);
//
//        System.out.print("NLR: ");
//        tree.NLR();
//        System.out.println();
//
//        System.out.print("LNR: ");
//        tree.LNR();
//        System.out.println();
//
//        System.out.print("LRN: ");
//        tree.LRN();
//        System.out.println();
//
//        tree.delete(6);
//
//        System.out.print("NLR: ");
//        tree.NLR();
//        System.out.println();
//
//        System.out.print("LNR: ");
//        tree.LNR();
//        System.out.println();
//
//        System.out.print("LRN: ");
//        tree.LRN();
//        System.out.println();
//
//        tree.delete(1);
//        tree.delete(8);
//        tree.delete(9);
//        tree.delete(13);
//        tree.delete(14);
//        tree.delete(7);
//        tree.delete(18);
//        tree.delete(19);
//
//        tree.LNR();
//        System.out.println();
    }
}
