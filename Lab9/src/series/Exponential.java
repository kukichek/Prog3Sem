package series;

final public class Exponential extends Series{
    double a, d;

    public Exponential(double a, double d) {
        this.a = a;
        this.d = d;
    }

    @Override
    public double kthElement(int k) throws Exception {
        if (k < 1) throw new Exception("Uncorrect index k");
        double kthElement = a;
        for (int i = 0; i < k - 1; ++i) {
            kthElement *= d;
        }
        return kthElement;
    }
}
