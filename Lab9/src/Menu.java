import operation.*;
import series.Exponential;
import series.Linear;
import series.Series;
import operation.Operation;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Menu {
    public static void main(String[] args) {
        int i = getSeriesIndex();
        Series series;
        double[] coefs;
        if (i == 1) {
            coefs = getCoefs(true);
            series = new Linear(coefs[0], coefs[1]);
        } else {
            coefs = getCoefs(false);
            series = new Exponential(coefs[0], coefs[1]);
        }

        runMenu(series);
    }

    private static void runMenu(Series series) {
        Scanner scanner = new Scanner(System.in);
        String s;
        Operation[] operations = new Operation[4];
        operations[0] = new CalcElement(series);
        operations[1] = new CalcSum(series);
        operations[2] = new PrintSeriesString(series);
        operations[3] = new PrintSeriesToFile(series);

        System.out.println("Choose operation:\n1 - calculate k-th element of series;");
        System.out.println("2 - calculate sum of k first elements of series;");
        System.out.println("3 - out string of series to console;");
        System.out.println("4 - print first k elements of series to file;");
        System.out.println("5 - exit;");
        s = scanner.nextLine();
        while(!s.equals("5")) {
            try {
                int i = Integer.valueOf(s);
                if (i > 0 && i < 5) {
                    operations[i - 1].operate(scanner);
                    if (i != 4) {
                        s = scanner.nextLine();
                    }
                }
            }
            catch (Exception e) {}

            System.out.println("Choose operation:\n1 - calculate k-th element of series;");
            System.out.println("2 - calculate sum of k first elements of series;");
            System.out.println("3 - out string of series to console;");
            System.out.println("4 - print first k elements of series to file;");
            System.out.println("5 - exit;");
            s = scanner.nextLine();
        }
    }

    private static int getSeriesIndex() {
        Scanner scanner = new Scanner(System.in);
        String s;
        do {
            System.out.println("Choose type of series:\n1 - linear series;\n2 - exponential series;");
            s = scanner.nextLine();
        } while (!(s.equals("1") || s.equals("2")));
        return Integer.valueOf(s);
    }

    static private double[] getCoefs(boolean isLinear) {
        Scanner scanner = new Scanner(System.in);
        boolean isPossibleToGet = false;
        double[] coefs = new double[2];
        do {
            if (isLinear) {
                System.out.println("Enter, please, coefficients a, b");
            } else {
                System.out.println("Enter, please, coefficients a, d");
            }

            StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine());
            try {
                if (tokenizer.hasMoreTokens()) {
                    coefs[0] = Double.valueOf(tokenizer.nextToken());
                } else {
                    isPossibleToGet = false;
                }
                if (tokenizer.hasMoreTokens()) {
                    coefs[1] = Double.valueOf(tokenizer.nextToken());
                } else {
                    isPossibleToGet = false;
                }
                isPossibleToGet = true;
            }
            catch (Exception e) {
                isPossibleToGet = false;
            }
        } while(!isPossibleToGet);

        return coefs;
    }
}
