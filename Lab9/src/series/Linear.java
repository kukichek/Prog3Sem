package series;

import java.sql.SQLInvalidAuthorizationSpecException;

public final class Linear extends Series{
    double a, b;

    public Linear(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double kthElement(int k) throws Exception {
        if (k < 1) throw new Exception("Uncorrect index k");
        return a + (k - 1) * b;
    }
}
