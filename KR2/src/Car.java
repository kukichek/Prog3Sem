public class Car extends AbstractCar {
    Body body;

    enum Body {
        SEDAN("sedan"), HATCHBACK("hatchback"), COUPE("coupe"), CROSSOVER("crossover");

        private String descript;

        private Body(String descript) {
            this.descript = descript;
        }
    }

    public Car(String name, String fuel, double petrolCons, int year, String body) throws DataException {
        super(name, fuel, petrolCons, year);

        if (body == null) {
            throw new DataException("Incorrect amount of doors");
        }

        switch (body) {
            case "sedan":
                this.body = Body.SEDAN;
                break;
            case "hatchback":
                this.body = Body.HATCHBACK;
                break;
            case "coupe":
                this.body = Body.COUPE;
                break;
            case "crossover":
                this.body = Body.CROSSOVER;
                break;
            default:
                throw new DataException("Incorrect body");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof Car)) {
                return false;
            }

            Car other = (Car) obj;

            return body == other.body;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());

        sb.append(", body: ").append(body.descript);

        return sb.toString();
    }

    @Override
    public void print() {
        System.out.print(toString());
    }
}
