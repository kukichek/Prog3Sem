package operation;

import series.Series;

import java.util.Scanner;

public class PrintSeriesString extends Operation {
    public PrintSeriesString(Series s) {
        this.s = s;
    }

    @Override
    public void operate(Scanner scanner) {
        int k;
        System.out.println("Enter, please, number k to see k first elements of the series");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Enter, please, number k to see k first elements of the series");
        }
        k = scanner.nextInt();

        try {
            System.out.println(s.toString(k));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
