import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class View extends JFrame {
    private List<Exam> exams = new ArrayList<>();
    private List<Arrear> arrears;

    private JTextArea examListTextArea;
    private JTextArea arrearListTestArea;

    private JTextField filterField;

    private JButton filterButton;
    private JButton saveXMLFormatButton;

    private XMLHandler handler = new XMLHandler();
    SAXParserFactory factory = SAXParserFactory.newInstance();

    FileNameExtensionFilter xmlFileFilter = new FileNameExtensionFilter("XML Files", "xml");

    final int BUTTON_WIDTH = 100;
    final int BUTTON_HEIGHT = 40;

    public View() {
        super("Students");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(550, 60, 520, 680);

        JPanel finalPanel = new JPanel();
        finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));

        filterButton = getFilterJButton();
        saveXMLFormatButton = getSaveXMLButton();

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(getLoadButton());
        buttonPanel.add(getAddDataButton());
        buttonPanel.add(filterButton);
        buttonPanel.add(saveXMLFormatButton);

        examListTextArea = getTextArea();
        arrearListTestArea = getTextArea();

        filterField = getFilterField();

        finalPanel.add(new Label("Список экзаменов"));
        finalPanel.add(examListTextArea);
        finalPanel.add(new Label("Строка для фильтрации (номер семестра, предмет 1, предмет 2 ...)"));
        finalPanel.add(filterField);
        finalPanel.add(buttonPanel);
        finalPanel.add(new Label("Список должников"));
        finalPanel.add(arrearListTestArea);

        add(finalPanel);
    }

    private JTextArea getTextArea() {
        JTextArea area = new JTextArea();
        area.setPreferredSize(new Dimension(480, 250));
        area.setEditable(false);
        area.setHighlighter(null);

        return area;
    }

    private JTextField getFilterField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(480, 25));
        field.setEnabled(false);

        return field;
    }

    private JButton getLoadButton() {
        JButton button = new JButton("Load File");
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        button.addActionListener(new ActionListener() {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter textFileFilter = new FileNameExtensionFilter("Text files", "txt");

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser.addChoosableFileFilter(textFileFilter);
                fileChooser.addChoosableFileFilter(xmlFileFilter);

                fileChooser.setAcceptAllFileFilterUsed(false);
                int choosedOption = fileChooser.showOpenDialog(null);

                if (choosedOption == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fileChooser.getSelectedFile();
                        String fileName = file.getName();

                        if (fileName.substring(fileName.lastIndexOf(".") + 1).equals("txt")) {
                            exams = getExamsFromTXT(file);
                        } else {
                            exams = getExamsFromXML(file);
                        }

                        setText(exams, examListTextArea);

                        filterButton.setEnabled(true);
                        filterField.setEnabled(true);

                        saveXMLFormatButton.setEnabled(true);
                    }
                    catch (NumberFormatException | NullPointerException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                    catch (IOException exception) {
                        JOptionPane.showMessageDialog(null, "Input/Output exception occurred");
                    }
                    catch (SAXException | ParserConfigurationException exception) {
                        JOptionPane.showMessageDialog(null, "Parse error occurred");
                    }
                }
            }
        });

        return button;
    }

    private JButton getAddDataButton() {
        JButton button = new JButton("Add Data");
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String val = JOptionPane.showInputDialog(null,
                        "Введите экзамен (номер зачетки, фамилия, семестр, название предмета, оценка)",
                        "Ввод дополнительных значений", JOptionPane.QUESTION_MESSAGE);
                if (val != null) {
                    try {
                        Exam exam = Exam.getExam(val);
                        exams.add(exam);

                        examListTextArea.append(exam.toString());
                        examListTextArea.append("\n");

                        filterButton.setEnabled(true);
                        filterField.setEnabled(true);

                        saveXMLFormatButton.setEnabled(true);
                    }
                    catch (NumberFormatException | NullPointerException | NoSuchElementException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage());
                    }
                }
            }
        });

        return button;
    }

    private JButton getFilterJButton() {
        JButton button = new JButton("Filter");
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setEnabled(false);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    arrears = getArrears(filterField.getText());

                    setText(arrears, arrearListTestArea);
                }
                catch (IOException | NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });

        return button;
    }

    private JButton getSaveXMLButton() {
        JButton button = new JButton("Save in XML format");
        button.setPreferredSize(new Dimension((int) Math.round(1.5 * BUTTON_WIDTH), BUTTON_HEIGHT));
        button.setEnabled(false);

        button.addActionListener(new ActionListener() {
            JFileChooser fileChooser = new JFileChooser();
            File file;

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser.setFileFilter(xmlFileFilter);
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        file = fileChooser.getSelectedFile();
                        printXMLToFile(file);
                    }
                    catch (IOException exception) {
                        JOptionPane.showMessageDialog(null, "Input/Output exception occurred");
                    }
                }
            }
        });

        return button;
    }

    private List<Exam> getExamsFromTXT(File file) throws IOException, NumberFormatException, NullPointerException  {
        List<Exam> exams = new ArrayList<>();

        Scanner scanner = new Scanner(file);

        if (!scanner.hasNext()) throw new IOException("File is empty");

        while (scanner.hasNext()) {
            exams.add(Exam.getExam(scanner.nextLine()));
        }

        return exams;
    }

    private List<Exam> getExamsFromXML(File file) throws IOException, NumberFormatException, NullPointerException, SAXException, ParserConfigurationException {
        exams.clear();

        SAXParser parser = factory.newSAXParser();
        parser.parse(file, handler);

        return exams;
    }

    private List<Arrear> getArrears(String filterString) throws IOException, NumberFormatException {
        Scanner scanner = new Scanner(filterString);

        List<Arrear> arrears = new ArrayList<>();
        List<String> subjects = new ArrayList<>();

        String semesterString;
        int semester;

        if (!scanner.hasNext()) throw new IOException("String is empty");
        semesterString = scanner.next();
        semester = Integer.valueOf(semesterString);

        while (scanner.hasNext()) {
            subjects.add(scanner.next());
        }

        Iterator<Exam> iterator = exams.iterator();
        while (iterator.hasNext()) {
            Exam curExam = iterator.next();

            if ((curExam.getSemester() == semester) && (subjects.contains(curExam.getSubject())) && (curExam.getMark() < 4)) {
                arrears.add(new Arrear(curExam.getSubject(), curExam.getName()));
            }

            Collections.sort(arrears, new ArrearComplarator());
        }

        return arrears;
    }

    void printXMLToFile(File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF-8"));

        StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlString.append("<session>\n");

        Iterator<Exam> iterator = exams.iterator();
        while (iterator.hasNext()) {
            Exam exam = iterator.next();
            xmlString.append(String.format("<exam semester=\"%d\" subject=\"%s\" mark=\"%d\" studentName=\"%s\" id=\"%d\" />\n",
                    exam.getSemester(), exam.getSubject(), exam.getMark(), exam.getName(), exam.getStudentID()));
        }
        xmlString.append("</session>\n");

        bufferedWriter.write(xmlString.toString());
        bufferedWriter.close();
    }

    private void setText(Collection collection, JTextArea area) {
        area.setText("");

        Iterator iterator = collection.iterator();
        while(iterator.hasNext()) {
            area.append(iterator.next().toString());
            area.append("\n");
        }
    }

    public static void main(String[] args) {
        View view = new View();
        view.setVisible(true);
    }

    private class XMLHandler extends DefaultHandler {
        String semester;
        String subject;
        String mark;
        String student;
        String studentID;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("exam")) {
                studentID = attributes.getValue("id");
                student = attributes.getValue("studentName");
                semester = attributes.getValue("semester");
                subject = attributes.getValue("subject");
                mark = attributes.getValue("mark");

                exams.add(new Exam(Integer.valueOf(studentID), student, Integer.valueOf(semester), subject, Integer.valueOf(mark)));
            }
        }
    }
}
