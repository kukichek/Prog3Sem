package complexNum;

public class ComplexNum implements Comparable<ComplexNum> {
    double re, im;

    public ComplexNum(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double abs() {
        return re * re + im * im;
    }

    @Override
    public int compareTo(ComplexNum other) {
        double difference = abs() - other.abs();
        if (Math.abs(difference) < 1e-5) return 0;

        //return difference > 0 ? 1 : -1;
        return Double.compare(abs(), other.abs());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(re);
        stringBuilder.append(" + ");
        stringBuilder.append(im);

        return stringBuilder.toString();
    }
}
