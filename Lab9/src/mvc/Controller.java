package mvc;

import series.Exponential;
import series.Linear;
import series.Series;

import java.io.IOException;

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

    void setElementAmount(int k) throws ClassNotFoundException {
        if (model == null) throw new ClassNotFoundException("Choose, please, a type of series");

        model.setElementAmount(k);
    }

    void writeToFile(String pathName) throws NullPointerException, ClassNotFoundException, IOException{
        if (pathName.equals("")) throw new NullPointerException();
        if (pathName.matches(".*[+=:;«,<>|/?*].*")) throw new IOException();
        if (model == null) throw new ClassNotFoundException("You didn't choose the type of series, please, choose one");
        model.saveToFile(pathName);
    }

    String kthElement(int k) throws ClassNotFoundException, NumberFormatException {
        if (model == null) throw new ClassNotFoundException("You didn't choose the type of series, please, choose one");
        return Double.toString(model.kthElement(k));
    }

    String sum() throws ClassNotFoundException {
        if (model == null) throw new ClassNotFoundException("You didn't choose the type of series, please, choose one");
        return Double.toString(model.seriesSum());
    }

    String firstKElements() throws ClassNotFoundException {
        if (model == null) throw new ClassNotFoundException("You didn't choose the type of series, please, choose one");
        return model.toString();
    }
}
