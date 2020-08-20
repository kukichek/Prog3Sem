public abstract class AbstractLamp {
    protected String producer;
    protected int capacity;

    public AbstractLamp(String producer, int capacity) throws DataException {
        if (producer == null || producer.equals("")) {
            throw new DataException("Producer is missing");
        }

        if (capacity <= 0) {
            throw new DataException("Incorrect capacity");
        }

        this.producer = producer;
        this.capacity = capacity;
    }

    public String getProducer() {
        return producer;
    }

    public String toString() {
        return "Producer: " + producer + ", capacity: " + capacity;
    }

    public abstract double countPrice();
}
