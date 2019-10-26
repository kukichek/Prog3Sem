package matrixOperations;

import java.util.InvalidPropertiesFormatException;

public class LinearEquationsSystem {
    Matrix matrix;

    public LinearEquationsSystem(final Matrix matrix) {
        this.matrix = new Matrix(matrix);
    }

    private void toTriangleFormPartialPivoting() {
        for (int k = 0; k < matrix.n - 1; ++k) {
            int rowNum = k;

            for (int i = k + 1; i < matrix.n; ++i) {
                if (Math.abs(matrix.a[i][k]) > Math.abs(matrix.a[rowNum][k])) {
                    rowNum = i;
                }
            }

            for (int j = 0; j < matrix.n + 1; ++j) {
                double temp = matrix.a[k][j];
                matrix.a[k][j] = matrix.a[rowNum][j];
                matrix.a[rowNum][j] = temp;
            }

            for (int i = k + 1; i < matrix.n; ++i) {
                double l = matrix.a[i][k] / matrix.a[k][k];
                matrix.a[i][k] = 0;
                for (int j = k + 1; j < matrix.n + 1; ++j) {
                    matrix.a[i][j] -= l * matrix.a[k][j];
                }
            }
        }
    }

    public void findSolution() throws InvalidPropertiesFormatException {
        if (Math.abs(Matrix.getDeterminant(matrix)) < 1e-6) {
            throw new InvalidPropertiesFormatException("Linear equations system hasn't only a single solution");
        }

        toTriangleFormPartialPivoting();

        for (int i = matrix.n - 1; i >= 0; --i) {
            for (int j = i + 1; j < matrix.n; ++j) {
                matrix.a[i][matrix.n] -= matrix.a[i][j] * matrix.a[j][matrix.n];
            }
            matrix.a[i][matrix.n] /= matrix.a[i][i];
        }
    }
}
