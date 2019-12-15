package tree;

public class Fruiter extends Tree {
    private int yieldWeight;
    private int averageShelfLife;

    public Fruiter(String name, String type, int age, int yieldWeight, int averageShelfLife) throws IncorrectInputDataException {
        super(name, type, age);

        if (yieldWeight <= 0) {
            throw new IncorrectInputDataException("Incorrect yield weight");
        }
        if (averageShelfLife <= 0) {
            throw new IncorrectInputDataException("Incorrect average shelf life");
        }

        this.yieldWeight = yieldWeight;
        this.averageShelfLife = averageShelfLife;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Fruiter)) return false;
        if (!(super.equals(obj))) return false;

        Fruiter someFruiter = (Fruiter) obj;
        return yieldWeight == someFruiter.yieldWeight &&
                averageShelfLife == someFruiter.averageShelfLife;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Фруктовое дерево: ");
        stringBuilder.append(super.toString()).append(", ");
        stringBuilder.append(yieldWeight).append(" - масса урожая, ");
        stringBuilder.append(averageShelfLife).append(" - средняя продолжительность хранения.");
        return stringBuilder.toString();
    }

    @Override
    public void print() {
        System.out.println(this.toString());
    }
}
