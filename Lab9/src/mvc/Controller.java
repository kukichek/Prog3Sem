package mvc;

import series.Exponential;
import series.Linear;
import series.Series;

import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class Controller {
    private Series model;
    private View view;

    public Controller(View view) {
        this.view = view;
        view.setController(this);
    }

    void initializeLinear() {
        model = new Linear();
    }

    void initializeExponential() {
        model = new Exponential();
    }

    void setCoefs(String stringA, String stringB) throws NumberFormatException, ClassNotFoundException{
        if (stringA.equals("") || stringB.equals("")) throw new NullPointerException();

        double a = Double.valueOf(stringA);
        double b = Double.valueOf(stringB);

        if (model == null) throw new ClassNotFoundException();

        model.setCoefs(a, b);
    }

    void writeToFile(String pathName, int k) throws NullPointerException, ClassNotFoundException, IOException, Exception{
        if (pathName.equals("")) throw new NullPointerException();
        if (pathName.matches(".*[+=:;«,<>|/?*].*")) throw new IOException();
        if (model == null) throw new ClassNotFoundException("You didn't choose the type of series, please, choose one");
        model.saveToFile(pathName, k);
    }

    String kthElement(int k) throws ClassNotFoundException, Exception {
        if (model == null) throw new ClassNotFoundException("You didn't choose the type of series, please, choose one");
        return Double.toString(model.kthElement(k));
    }

    String sum(int k) throws ClassNotFoundException, Exception {
        if (model == null) throw new ClassNotFoundException("You didn't choose the type of series, please, choose one");
        return Double.toString(model.seriesSum(k));
    }

    String firstKElements(int k) throws ClassNotFoundException, Exception {
        if (model == null) throw new ClassNotFoundException("You didn't choose the type of series, please, choose one");
        return model.toString(k);
    }
}
