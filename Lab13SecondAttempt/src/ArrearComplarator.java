import java.util.Comparator;

public class ArrearComplarator implements Comparator<Arrear> {
    @Override
    public int compare(Arrear arrea1, Arrear arrear2) {
        int diff = arrea1.subject.compareTo(arrear2.subject);
        return (diff == 0) ? arrea1.studentName.compareTo(arrear2.studentName) : diff;
    }
}
