package operation;

import series.Series;

import java.util.Scanner;

abstract public class Operation {
    Series s;
    abstract public void operate(Scanner scanner);
}
