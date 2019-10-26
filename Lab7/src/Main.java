import matrixOperations.Matrix;
import matrixOperations.*;

import java.io.FileNotFoundException;
import java.util.InvalidPropertiesFormatException;

public class Main {
    public static void main(String[] args) {
        try{
            if (args.length != 1) {
                throw new InvalidPropertiesFormatException("Incorrect input");
            }
            Matrix m = MatrixReader.readMatrixFromFile(args[0]);
            LinearEquationsSystem les = new LinearEquationsSystem(m);
            les.findSolution();
            SolutionPrinter.printSolution(les);
        }
        catch(FileNotFoundException | RuntimeException | InvalidPropertiesFormatException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
