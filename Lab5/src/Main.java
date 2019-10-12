import java.sql.SQLOutput;
import java.util.Locale;
import java.util.Scanner;

public class Main{
    static class MyException extends Exception {
        MyException() {}
        MyException(String msg) {
            super(msg);
        }
    }

    static double sum(double x, double eps) throws MyException {
    double sum = 0;

    if (eps <= 0) {
        throw new MyException("Invalid accuracy");
    }
    if (Math.abs(x) >= 3) {
        throw new MyException("Invalid x");
    }

    int k = 1;
    double term = -1 * (k + 1) * x / 3;
    while (Math.abs(term) > eps) {
        sum += term;

        k++;
        term *= (k + 1) * (-1) * x;
        term /= 3 * k;
    }

    return sum;
    }

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new MyException("Ivalid amount of arguments");
            }

            double x = Double.valueOf(args[0]);
            double eps = Double.valueOf(args[1]);

            System.out.println("Sum: " + String.valueOf(sum(x, eps)));
        }
        catch(MyException | NumberFormatException error) {
            System.err.println(error.getMessage());
        }
    }
}
