package series;

import java.sql.SQLInvalidAuthorizationSpecException;

public final class Linear extends Series{

    public Linear() {
        super();
    }

    @Override
    public double kthElement(int k) throws Exception {
        if (k < 1) throw new Exception("Uncorrect index k");
        return a + (k - 1) * b;
    }
}
