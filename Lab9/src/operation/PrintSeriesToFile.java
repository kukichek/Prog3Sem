package operation;

import series.Series;

import java.util.Scanner;

public class PrintSeriesToFile extends Operation {
    public PrintSeriesToFile(Series s) {
        this.s = s;
    }

    @Override
    public void operate(Scanner scanner) {
        int k;
        String pathName;
        System.out.println("Enter, please, number k to see k first elements of the series");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Enter, please, number k to see k first elements of the series");
        }
        k = scanner.nextInt();

        System.out.println("Enter, please, a name of file without extension");
        pathName = scanner.nextLine();
        pathName = scanner.nextLine();

        try {
            s.saveToFile(pathName, k);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
