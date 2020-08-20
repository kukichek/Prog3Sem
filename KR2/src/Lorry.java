public class Lorry extends AbstractCar {
    private int tonnage;
    private int wheelAmount;

    public Lorry(String name, String fuel, double petrolCons, int year, int tonnage, int wheelAmount) throws DataException {
        super(name, fuel, petrolCons, year);

        if (tonnage < 0) {
            throw new DataException("Incorrect tonnage");
        }
        if (wheelAmount < 4) {
            throw new DataException("Incorrect amount of wheels");
        }

        this.tonnage = tonnage;
        this.wheelAmount = wheelAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof Lorry)) {
                return false;
            }

            Lorry other = (Lorry) obj;
            return tonnage == other.tonnage && wheelAmount == other.wheelAmount;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());

        sb.append(", tonnage: ").append(tonnage);
        sb.append(", amount of wheels ").append(wheelAmount);

        return sb.toString();
    }

    @Override
    public void print() {
        System.out.print(toString());
    }
}
