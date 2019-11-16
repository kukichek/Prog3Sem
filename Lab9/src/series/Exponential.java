package series;

final public class Exponential extends Series{

    public Exponential() {
        super();
    }

    @Override
    public double kthElement(int k) throws NumberFormatException {
        if (k < 1) throw new NumberFormatException();
        double kthElement = a;
        for (int i = 0; i < k - 1; ++i) {
            kthElement *= b;
        }
        return kthElement;
    }
}
