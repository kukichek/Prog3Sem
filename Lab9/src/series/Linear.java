package series;

public final class Linear extends Series{

    public Linear() {
        super();
    }

    @Override
    public double kthElement(int k) throws NumberFormatException {
        if (k < 1) throw new NumberFormatException();
        return a + (k - 1) * b;
    }
}
