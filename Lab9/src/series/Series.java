package series;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

abstract public class Series {
    protected double a, b;

    protected Series() {
        a = 0;
        b = 0;
    }

    public void setCoefs(double a, double b) {
        this.a = a;
        this.b = b;
    }

    abstract public double kthElement(int k) throws Exception;
    public double seriesSum(int k) throws Exception {
        float sum = 0;
        for (int i = 0; i < k; ++i) {
            sum += kthElement(i + 1);
        }
        return sum;
    }
    public String toString(int k) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        if (k < 1) throw new Exception("Uncorrect index k");
        stringBuilder.append(kthElement(1));
        for (int i = 1; i < k; ++i) {
            stringBuilder.append(' ');
            stringBuilder.append(kthElement(i + 1));
        }
        return stringBuilder.toString();
    }
    public void saveToFile(String pathName, int k) throws Exception {
        FileWriter writer = new FileWriter(pathName + ".txt");
        writer.write(toString(k));
        writer.close();
    }
}
