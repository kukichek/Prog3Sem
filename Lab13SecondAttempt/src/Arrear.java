public class Arrear {
    public String subject;
    public String studentName;

    public Arrear(String subject, String studentName) throws NullPointerException {
        if (subject == null) throw new NullPointerException("Subject is missing");
        if (studentName == null) throw new NullPointerException("Student name is missing");

        this.subject = subject;
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return subject + " " + studentName;
    }
}
