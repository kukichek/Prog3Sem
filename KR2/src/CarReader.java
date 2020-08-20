import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class CarReader {
    public static <T extends AbstractCar> void read(CarContainer<T> container, String path) throws IOException, DataException, NumberFormatException {
        Scanner scanner = new Scanner(Paths.get(path));
        Scanner lineScanner;

        String name;
        String fuel;
        String petrolCons;
        int year;

        String temp;

        if (!scanner.hasNext()) {
            throw new IOException("File is empty");
        }
        while (scanner.hasNext()) {
            lineScanner = new Scanner(scanner.nextLine());

            name = lineScanner.next();
            fuel = lineScanner.next();
            petrolCons = lineScanner.next();
            year = lineScanner.nextInt();
            temp = lineScanner.next();

            if (lineScanner.hasNext()) {
                container.add((T) new Lorry(name, fuel, Double.valueOf(petrolCons), year, Integer.valueOf(temp), lineScanner.nextInt()));
            } else {
                container.add((T) new Car(name, fuel, Double.valueOf(petrolCons), year, temp));
            }
        }
    }
}
