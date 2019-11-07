package operation;

import series.Series;

import java.util.Scanner;

public class CalcElement extends Operation {
    public CalcElement(Series s) {
        this.s = s;
    }

    @Override
    public void operate(Scanner scanner) {
        int k;
        System.out.println("Enter, please, number of element you want to get");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Enter, please, number of element you want to get");
        }
        k = scanner.nextInt();

        try {
            System.out.println(s.kthElement(k));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
