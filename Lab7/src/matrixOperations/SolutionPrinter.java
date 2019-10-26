package matrixOperations;

public class SolutionPrinter {
    public static void printSolution(LinearEquationsSystem les) {
        int n = les.matrix.n;

        System.out.println("Solution:");
        for (int i = 0; i < n; ++i) {
            System.out.println(les.matrix.a[i][n]);
        }
    }
}
