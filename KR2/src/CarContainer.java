import java.util.ArrayList;
import java.util.Collections;

public class CarContainer<T extends AbstractCar> extends ArrayList<T> implements Cloneable {
    @Override
    public CarContainer<T> clone() {
        return (CarContainer<T>) super.clone();
    }

    public void print() {
        CarContainer<T> cloned = clone();
        Collections.sort(cloned);

        int size = cloned.size();
        for (int i = 0; i < size; ++i) {
            cloned.get(i).print();
            System.out.println();
        }
    }

    public int count(T car) {
        return Collections.frequency(this, car);
    }

    public CarContainer<T> threeMin() throws DataException {
        if (this.size() < 3) {
            throw new DataException("Container has size less than 3");
        }

        CarContainer<T> cloned = clone();
        Collections.sort(cloned);

        CarContainer<T> threeMin = new CarContainer<>();
        threeMin.add(cloned.get(0));
        threeMin.add(cloned.get(1));
        threeMin.add(cloned.get(2));

        return threeMin;
    }

    public int countByName(String name) {
        return (int) this.stream().filter(a -> a.getName().equals(name)).count();
    }
}
