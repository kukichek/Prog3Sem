package mvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.JOptionPane.showMessageDialog;

public class View extends JFrame {
    private JRadioButton linearRadioButton;
    private JRadioButton exponentialRadioButton;

    private JTextField aCoefficientTField;
    private JTextField bCoefficientTField;
    private JTextField kIndexTField;
    private JTextField fileNameTField;
    private JTextField kthElementTField;
    private JTextField sumTField;
    private JTextField firstKElemsTField;

    private JButton applyCoefsButton;
    private JButton applyKIndexButton;
    private JButton writeToFileButton;
    private JButton kThElemButton;
    private JButton sumButton;
    private JButton firstKElemsButton;

    private Controller controller;
    private Integer k;

    public View() {
        super("Series");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 300, 640, 480);
        setResizable(false);

        linearRadioButton = new JRadioButton("Linear");
        exponentialRadioButton = new JRadioButton("Exponential");
        aCoefficientTField = new JTextField();
        bCoefficientTField = new JTextField();
        kIndexTField = new JTextField();
        fileNameTField = new JTextField();
        kthElementTField = new JTextField();
        sumTField = new JTextField();
        firstKElemsTField = new JTextField();
        applyCoefsButton = new JButton("Apply");
        applyKIndexButton = new JButton("Apply");
        writeToFileButton = new JButton("Write");
        kThElemButton = new JButton("kth element");
        sumButton = new JButton("Sum");
        firstKElemsButton = new JButton("First k elements");

        JPanel radioButtonsPanel = new JPanel();
        JPanel seriesTypePanel = new JPanel();
        JPanel coefsNamePanel = new JPanel();
        JPanel textBoxesCoefsPanel = new JPanel();
        JPanel coefsPanel = new JPanel();
        JPanel southButtons = new JPanel();
        JPanel southTextFields = new JPanel();
        JPanel northPanel = new JPanel();
        JPanel westPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        JPanel southPanel = new JPanel();
        JPanel finalPanel = new JPanel();

        JLabel seriesTypeLabel = new JLabel("Choose type of series");
        JLabel setCoefficientsLabel = new JLabel("Set coefficients");
        JLabel setKIndexLabel = new JLabel("Set index k");
        JLabel setOutputFileLabel = new JLabel("Set output file (without extension)");

        ButtonGroup seriesTypeButtonGroup = new ButtonGroup();
        seriesTypeButtonGroup.add(linearRadioButton);
        seriesTypeButtonGroup.add(exponentialRadioButton);

        linearRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.initializeLinear();
            }
        });
        exponentialRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.initializeExponential();
            }
        });
        applyCoefsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String stringA = aCoefficientTField.getText();
                    String stringB = bCoefficientTField.getText();
                    controller.setCoefs(stringA, stringB);
                }
                catch (NullPointerException exception) {
                    showMessageDialog(null, "You didn't enter one of the coefficients, please, try again");
                }
                catch (NumberFormatException exception) {
                    showMessageDialog(null, "Uncorrect format of the coefficients a, b. Please, enter again");
                }
                catch (ClassNotFoundException exception) {
                    showMessageDialog(null, "You didn't choose the type of series, please, choose one");
                }
            }
        });
        applyKIndexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (kIndexTField.getText().equals("")) throw new NullPointerException();
                    k = Integer.valueOf(kIndexTField.getText());
                }
                catch (NullPointerException exception) {
                    showMessageDialog(null, "You didn't enter index k, please, try again");
                }
                catch (NumberFormatException exception) {
                    showMessageDialog(null, "Uncorrect format of the index k. Please, enter again");
                }
            }
        });
        writeToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (k == null) throw new ClassNotFoundException("You didn't enter the index k, enter please");
                    String pathName = fileNameTField.getText();
                    controller.writeToFile(pathName, k);
                }
                catch (ClassNotFoundException exception) {
                    showMessageDialog(null, exception.getMessage());
                }
                catch (NullPointerException exception) {
                    showMessageDialog(null, "You didn't enter output file name, please, try again");
                }
                catch (IOException exception) {
                    showMessageDialog(null, "Uncorrect filename, please, enter again");
                }
                catch(Exception exception) {
                    showMessageDialog(null, "Uncorrect index k, please, enter again");
                }
            }
        });
        kThElemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (k == null) throw new ClassNotFoundException("You didn't enter the index k, enter please");
                    kthElementTField.setText(controller.kthElement(k));
                }
                catch (ClassNotFoundException exception) {
                    showMessageDialog(null, exception.getMessage());
                }
                catch(Exception exception) {
                    showMessageDialog(null, "Uncorrect index k, please, enter again");
                }
            }
        });
        sumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (k == null) throw new ClassNotFoundException("You didn't enter the index k, enter please");
                    sumTField.setText(controller.sum(k));
                }
                catch (ClassNotFoundException exception) {
                    showMessageDialog(null, exception.getMessage());
                }
                catch(Exception exception) {
                    showMessageDialog(null, "Uncorrect index k, please, enter again");
                }
            }
        });
        firstKElemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (k == null) throw new ClassNotFoundException("You didn't enter the index k, enter please");
                    firstKElemsTField.setText(controller.firstKElements(k));
                }
                catch (ClassNotFoundException exception) {
                    showMessageDialog(null, exception.getMessage());
                }
                catch(Exception exception) {
                    showMessageDialog(null, "Uncorrect index k, please, enter again");
                }
            }
        });

        JLabel aCoefLabel = new JLabel("a:");
        aCoefLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel bCoefLabel = new JLabel("b:");
        bCoefLabel.setHorizontalAlignment(JLabel.CENTER);
        coefsNamePanel.setLayout(new BorderLayout());
        coefsNamePanel.add(aCoefLabel, BorderLayout.NORTH);
        coefsNamePanel.add(bCoefLabel, BorderLayout.SOUTH);

        aCoefficientTField.setColumns(5);
        bCoefficientTField.setColumns(5);
        textBoxesCoefsPanel.setLayout(new BorderLayout());
        textBoxesCoefsPanel.add(aCoefficientTField, BorderLayout.NORTH);
        textBoxesCoefsPanel.add(bCoefficientTField, BorderLayout.SOUTH);

        setCoefficientsLabel.setHorizontalAlignment(JLabel.CENTER);
        coefsPanel.setLayout(new BorderLayout());
        coefsPanel.add(setCoefficientsLabel, BorderLayout.NORTH);
        coefsPanel.add(coefsNamePanel, BorderLayout.WEST);
        coefsPanel.add(textBoxesCoefsPanel, BorderLayout.EAST);
        coefsPanel.add(applyCoefsButton, BorderLayout.SOUTH);

        radioButtonsPanel.setLayout(new BorderLayout());
        radioButtonsPanel.add(linearRadioButton, BorderLayout.NORTH);
        radioButtonsPanel.add(exponentialRadioButton, BorderLayout.SOUTH);

        seriesTypeLabel.setHorizontalAlignment(JLabel.CENTER);
        seriesTypePanel.setLayout(new BorderLayout());
        seriesTypePanel.add(seriesTypeLabel, BorderLayout.CENTER);
        seriesTypePanel.add(radioButtonsPanel, BorderLayout.SOUTH);

        BorderLayout northLayout = new BorderLayout();
        northLayout.setHgap(3);
        northLayout.setVgap(3);
        northPanel.setLayout(northLayout);
        northPanel.add(seriesTypePanel, BorderLayout.WEST);
        northPanel.add(coefsPanel, BorderLayout.EAST);

        setKIndexLabel.setHorizontalAlignment(JLabel.CENTER);
        kIndexTField.setColumns(5);
        westPanel.setLayout(new BorderLayout());
        westPanel.add(setKIndexLabel, BorderLayout.NORTH);
        westPanel.add(kIndexTField, BorderLayout.CENTER);
        westPanel.add(applyKIndexButton, BorderLayout.SOUTH);

        fileNameTField.setColumns(15);
        setOutputFileLabel.setHorizontalAlignment(JLabel.CENTER);
        eastPanel.setLayout(new BorderLayout());
        eastPanel.add(setOutputFileLabel, BorderLayout.NORTH);
        eastPanel.add(fileNameTField, BorderLayout.CENTER);
        eastPanel.add(writeToFileButton, BorderLayout.SOUTH);

        BorderLayout southButtonsLayout = new BorderLayout();
        southButtons.setLayout(southButtonsLayout);
        southButtons.add(kThElemButton, BorderLayout.NORTH);
        southButtons.add(sumButton, BorderLayout.CENTER);
        southButtons.add(firstKElemsButton, BorderLayout.SOUTH);

        southTextFields.setLayout(new BoxLayout(southTextFields, BoxLayout.Y_AXIS));
        kthElementTField.setColumns(15);
        sumTField.setColumns(15);
        firstKElemsTField.setColumns(15);
        kthElementTField.setEditable(false);
        sumTField.setEditable(false);
        firstKElemsTField.setEditable(false);
        southTextFields.add(kthElementTField);
        southTextFields.add(sumTField);
        southTextFields.add(firstKElemsTField);

        southPanel.setLayout(new BorderLayout());
        southPanel.add(southButtons, BorderLayout.WEST);
        southPanel.add(southTextFields, BorderLayout.EAST);

        BorderLayout finalLayout = new BorderLayout();
        finalLayout.setVgap(3);
        finalLayout.setHgap(3);
        finalPanel.setLayout(finalLayout);
        finalPanel.add(northPanel, BorderLayout.NORTH);
        finalPanel.add(westPanel, BorderLayout.WEST);
        finalPanel.add(eastPanel, BorderLayout.EAST);
        finalPanel.add(southPanel, BorderLayout.SOUTH);

        add(finalPanel);
        this.pack();
    }

    void setController(Controller controller) {
        this.controller = controller;
    }
}
