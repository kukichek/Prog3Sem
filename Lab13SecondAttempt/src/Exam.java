import java.util.NoSuchElementException;
import java.util.Scanner;

public class Exam {
    private int studentID;
    private String name;
    private int semester;
    private String subject;
    private int mark;

    public Exam(int studentID, String name, int semester, String subject, int mark) throws NumberFormatException, NullPointerException {
        if (studentID < 1) throw new NumberFormatException("Student's record book number is incorrect");
        if (name == null) throw new NullPointerException("Name is missing");
        if (semester < 1) throw new NumberFormatException("Semester is incorrect");
        if (subject == null) throw new NullPointerException("Subject is missing");
        if (mark < 1) throw new NumberFormatException("Mark is incorrect");

        this.studentID = studentID;
        this.name = name;
        this.semester = semester;
        this.subject = subject;
        this.mark = mark;
    }

    public static Exam getExam(String line) throws NumberFormatException, NullPointerException, NoSuchElementException {
        String studentID;
        String name;
        String semester;
        String subject;
        String mark;

        Scanner lineScanner = new Scanner(line);
        studentID = lineScanner.next();
        name = lineScanner.next();
        semester = lineScanner.next();
        subject = lineScanner.next();
        mark = lineScanner.next();

        return new Exam(Integer.valueOf(studentID), name, Integer.valueOf(semester), subject, Integer.valueOf(mark));
    }

    @Override
    public String toString() {
        return studentID + " " + name + " " + semester + " " + subject + " " + mark;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public int getSemester() {
        return semester;
    }

    public int getMark() {
        return mark;
    }
}
