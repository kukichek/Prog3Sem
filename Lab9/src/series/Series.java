package series;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

abstract public class Series {
    protected double a, b;
    protected int k;

    protected Series() {
        a = 0;
        b = 0;
    }

    public void setCoefs(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public void setElementAmount(int k) throws NumberFormatException {
        if (k < 1) throw new NumberFormatException();
        this.k = k;
    }

    abstract public double kthElement(int k) throws NumberFormatException;

    public double seriesSum() {
        float sum = 0;
        for (int i = 0; i < k; ++i) {
            sum += kthElement(i + 1);
        }
        return sum;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(kthElement(1));
        for (int i = 1; i < k; ++i) {
            stringBuilder.append(' ');
            stringBuilder.append(kthElement(i + 1));
        }
        return stringBuilder.toString();
    }

    public void saveToFile(String pathName) throws IOException {
        FileWriter writer = new FileWriter(pathName + ".txt");
        writer.write(toString());
        writer.close();
    }
}
