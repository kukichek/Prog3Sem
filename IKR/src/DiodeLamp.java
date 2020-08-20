public class DiodeLamp extends AbstractLamp {
    private final int DIODE_LAMP_CONST = 25;
    private int diodeAmount;

    public DiodeLamp(String producer, int capacity, int diodeAmount) throws DataException {
        super(producer, capacity);

        if (diodeAmount <= 0) {
            throw new DataException("Incorrect amount of diodes");
        }

        this.diodeAmount = diodeAmount;
    }

    @Override
    public double countPrice() {
        double price = Math.round(capacity * diodeAmount / DIODE_LAMP_CONST * 100);
        return price / 100;
    }

    public String toString() {
        return super.toString() + ", amount of diodes: " + diodeAmount + ", price: " + countPrice();
    }
}
