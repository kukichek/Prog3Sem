public abstract class AbstractCar implements Comparable<AbstractCar> {
    protected String name;
    protected Fuel fuel;
    protected double petrolCons;
    protected int year;

    enum Fuel {
        GASOLINE("gasoline"), DIESEL("diesel");
        private String description;

        Fuel(String description) {
            this.description = description;
        }
    }

    public AbstractCar(String name, String fuel, double petrolCons, int year) throws DataException {
        if (name == null || name.equals("")) {
            throw new DataException("Incorrect name");
        }

        this.name = name;

        if (fuel == null) {
            throw new DataException("Incorrect fuel");
        }

        switch(fuel) {
            case "gasoline":
                this.fuel = Fuel.GASOLINE;
                break;
            case "diesel":
                this.fuel = Fuel.DIESEL;
                break;
            default:
                throw new DataException("Incorrect fuel");
        }

        if (petrolCons < 0) {
            throw new DataException("Incorrect petrol consumption");
        }
        if (year < 1800) {
            throw new DataException("Incorrect year");
        }

        this.petrolCons = petrolCons;
        this.year = year;
    }

    String getName() {
        return name;
    }

    @Override
    public int compareTo(AbstractCar abstractCar) {
        int diff = ((Integer) abstractCar.year).compareTo(year);
        if (diff == 0) {
            return ((Double) petrolCons).compareTo(abstractCar.petrolCons);
        }

        return diff;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        AbstractCar other = (AbstractCar) obj;

        return name.equals(other.name) && (fuel == other.fuel) &&
                (petrolCons == other.petrolCons) && (year == other.year);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(", ");
        sb.append("fuel: ").append(fuel.description);
        sb.append(", consumption of petrol: ").append(petrolCons);
        sb.append(", year: ").append(year);

        return sb.toString();
    }

    public abstract void print();
}

