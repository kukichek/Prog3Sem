package series;

final public class Exponential extends Series{

    public Exponential() {
        super();
    }

    @Override
    public double kthElement(int k) throws Exception {
        if (k < 1) throw new Exception("Uncorrect index k");
        double kthElement = a;
        for (int i = 0; i < k - 1; ++i) {
            kthElement *= b;
        }
        return kthElement;
    }
}
