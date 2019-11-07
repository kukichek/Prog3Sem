package tester;

import binarySearchTree.*;
import complexNum.ComplexNum;

public class ComplexNumTester extends Tester{
    @Override
    public void test() {
        BinarySearchTree<ComplexNum> tree = new BinarySearchTree<ComplexNum>();

        tree.add(new ComplexNum(3, 4));
        tree.add(new ComplexNum(7, 1));
        tree.add(new ComplexNum(1, 5));
        tree.add(new ComplexNum(2, 2));
        tree.add(new ComplexNum(1, 1));
        tree.add(new ComplexNum(8, 0));

        System.out.print("NLR: ");
        tree.NLR();
        System.out.println();

        System.out.print("LNR: ");
        tree.LNR();
        System.out.println();

        System.out.print("LRN: ");
        tree.LRN();
        System.out.println();

        tree.delete(new ComplexNum(2, 2));

        System.out.print("NLR: ");
        tree.NLR();
        System.out.println();

        System.out.print("LNR: ");
        tree.LNR();
        System.out.println();

        System.out.print("LRN: ");
        tree.LRN();
        System.out.println();

        tree.delete(new ComplexNum(3, 4));

        System.out.print("NLR: ");
        tree.NLR();
        System.out.println();

        System.out.print("LNR: ");
        tree.LNR();
        System.out.println();

        System.out.print("LRN: ");
        tree.LRN();
        System.out.println();

        tree.delete(new ComplexNum(1, 5));

        System.out.print("NLR: ");
        tree.NLR();
        System.out.println();

        System.out.print("LNR: ");
        tree.LNR();
        System.out.println();

        System.out.print("LRN: ");
        tree.LRN();
        System.out.println();
    }
}
