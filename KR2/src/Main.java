import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CarContainer<Car> container = new CarContainer<>();
            CarReader.read(container, "input1.txt");
            container.print();
            System.out.println();

            Car mitsubishi = new Car("Mitsubishi", "gasoline", 9.7, 2014, "crossover");
            System.out.print("Car ");
            mitsubishi.print();
            System.out.println(" is found in container " + container.count(mitsubishi) + " times");
            System.out.println();

            String nameToFind = new String("BMW");
            System.out.print("Amount of cars with name " + nameToFind + ": " + container.countByName(nameToFind));
            System.out.println();

            System.out.println("Three min: ");
            container.threeMin().print();
            System.out.println("\n");
        }
        catch (IOException | DataException e) {
            System.err.println(e.getMessage());
        }

        try {
            CarContainer<Lorry> container = new CarContainer<>();
            CarReader.read(container, "input2.txt");
            container.print();
            System.out.println();

            Lorry scania = new Lorry("Scania", "gasoline", 28.3, 2001, 38, 8);
            System.out.print("Car ");
            scania.print();
            System.out.println(" is found in container " + container.count(scania) + " times");
            System.out.println();

            String nameToFind = new String("MAZ");
            System.out.print("Amount of cars with name " + nameToFind + ": " + container.countByName(nameToFind));
            System.out.println();

            System.out.println("Three min: ");
            container.threeMin().print();
            System.out.println("\n");
        }
        catch (IOException | DataException e) {
            System.err.println(e.getMessage());
        }
    }
}
