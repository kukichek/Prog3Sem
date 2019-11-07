package operation;

import series.Series;

import java.util.Scanner;

public class CalcSum extends Operation {
    public CalcSum(Series s) {
        this.s = s;
    }

    @Override
    public void operate(Scanner scanner) {
        int k;
        System.out.println("Enter, please, number k to get sum of k first elements");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Enter, please, number k to get sum of k first elements");
        }
        k = scanner.nextInt();

        try {
            System.out.println(s.seriesSum(k));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
