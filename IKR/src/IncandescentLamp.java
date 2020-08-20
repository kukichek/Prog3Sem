public class IncandescentLamp extends AbstractLamp {
    private final double INC_LAMP_CONST = 0.23;
    private int exploitTime;

    public IncandescentLamp(String producer, int capacity, int exploitTime) throws DataException {
        super(producer, capacity);
        if (exploitTime < 0) {
            throw new DataException("Incorrect exploit time");
        }

        this.exploitTime = exploitTime;
    }

    @Override
    public double countPrice() {
        double price = Math.round(capacity * INC_LAMP_CONST * exploitTime * 100);
        return price / 100;
    }

    public String toString() {
        return super.toString() + ", exploit time: " + exploitTime + ", price: " + countPrice();
    }
}
