package matrixOperations;

public class Matrix {
    int n, m;
    double[][] a;

    public Matrix(final Matrix otherMatrix) {
        a = new double[otherMatrix.n][otherMatrix.m];
        n = otherMatrix.n;
        m = otherMatrix.m;

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j  < m; ++j) {
                a[i][j] = otherMatrix.a[i][j];
            }
        }
    }

    public Matrix(int n, int m) {
        a = new double[n][m];
        this.n = n;
        this.m = m;
    }

    public int width() {
        return n;
    }
    public int height() { return m; };

    public double getElement(int i, int j) {
        return a[i][j];
    }

    static double getDeterminant(Matrix m) {
        if (m.n == 1) { return m.a[0][0]; }

        double det = 0;
        for (int i = 0; i < m.n; ++i) {
            Matrix subM = new Matrix(m.n - 1, m.n - 1);

            int subMatrixRowIndex = 0;
            for (int matrixRowIndex = 0; matrixRowIndex < i; ++matrixRowIndex, ++subMatrixRowIndex) {
                for (int j = 1; j < m.n; ++j) {
                    subM.a[subMatrixRowIndex][j - 1] = m.a[matrixRowIndex][j];
                }
            }
            for (int matrixRowIndex = i + 1; matrixRowIndex < m.n; ++matrixRowIndex, ++subMatrixRowIndex) {
                for (int j = 1; j < m.n; ++j) {
                    subM.a[subMatrixRowIndex][j - 1] = m.a[matrixRowIndex][j];
                }
            }

            if (i % 2 == 0) {
                det += m.a[i][0] * getDeterminant(subM);
            } else {
                det -= m.a[i][0] * getDeterminant(subM);
            }
        }

        return det;
    }
}
