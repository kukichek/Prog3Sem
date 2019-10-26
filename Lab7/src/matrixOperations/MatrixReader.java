package matrixOperations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MatrixReader {
    public static Matrix readMatrixFromFile(String pathName) throws FileNotFoundException,
            RuntimeException {
        Scanner scanner = new Scanner(new File(pathName));
        if (!scanner.hasNext()) {
            throw new NoSuchElementException("Empty file");
        }
        if (!scanner.hasNextDouble()) {
            throw new RuntimeException("Incorrect input");
        }
        int matrixSize = scanner.nextInt();

        Matrix matrix = new Matrix(matrixSize, matrixSize + 1);

        for (int i = 0; i < matrixSize; ++i) {
            for (int j = 0; j < matrixSize + 1; ++j) {
                if (!scanner.hasNext()) {
                    throw new NoSuchElementException("File has less elements than needed");
                }
                if (!scanner.hasNextDouble()) {
                    throw new RuntimeException("Incorrect input");
                }
                matrix.a[i][j] = scanner.nextDouble();
            }
        }

        if (scanner.hasNext()) {
            throw new RuntimeException("File has more elements than needed");
        }

        return matrix;
    }
}
