import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class View extends JFrame {
    final public static int N = 10;
    final public static int M = 7;

    final public static int BUTTON_AMOUNT = 4;

    private DefaultListModel leftListModel = new DefaultListModel();
    private DefaultListModel rightListModel = new DefaultListModel();

    JList<String> leftList = new JList<>();
    JList<String> rightList = new JList<>();

    public View() {
        super("Tabbed application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 300, 640, 480);

        JTabbedPane finalPane = new JTabbedPane();

        finalPane.addTab("1", initFirstPanel());
        finalPane.addTab("2", initSecondPanel());
        finalPane.addTab("3", initThirdPanel());

        add(finalPane);
    }

    private JPanel initFirstPanel() {
        JPanel firstPanel = new JPanel(new BorderLayout());
        JPanel centralPanel = new JPanel(new BorderLayout());
        JButton toLeftListButton = new JButton("<");
        JButton toRightListButton = new JButton(">");

        leftListModel.addElement("Cow goes moo");
        leftListModel.addElement("Dog goes woof");
        leftListModel.addElement("Cat goes meow");
        leftListModel.addElement("Frog goes croak");
        leftListModel.addElement("Bird goes tweet");
        leftListModel.addElement("Mouse goes squeek");
        leftListModel.addElement("Elephant goes toot");
        leftListModel.addElement("What does the fox say?");

        rightListModel.addElement("Fish go blub");
        rightListModel.addElement("Seal goes ow ow ow");
        rightListModel.addElement("Duck says quack");
        rightListModel.addElement("Elmo says cookie");

        leftList.setModel(leftListModel);
        leftList.setPreferredSize(new Dimension(200, 400));
        rightList.setModel(rightListModel);
        rightList.setPreferredSize(new Dimension(200, 400));

        toLeftListButton.setPreferredSize(new Dimension(50, 40));
        toRightListButton.setPreferredSize(new Dimension(50, 40));
        centralPanel.add(toRightListButton, BorderLayout.NORTH);
        centralPanel.add(toLeftListButton, BorderLayout.SOUTH);

        firstPanel.add(leftList, BorderLayout.WEST);
        firstPanel.add(rightList, BorderLayout.EAST);
        firstPanel.add(centralPanel, BorderLayout.CENTER);

        toLeftListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                moveLines(rightList.getSelectedIndices(), rightListModel, leftListModel);
            }
        });
        toRightListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                moveLines(leftList.getSelectedIndices(), leftListModel, rightListModel);
            }
        });

        return firstPanel;
    }

    private JPanel initSecondPanel() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private String buttonLabel;
            private Color buttonColor;

            @Override
            public void mouseEntered(MouseEvent e) {
                buttonColor = ((JButton) e.getSource()).getBackground();
                Color invertedColor = new Color(255 - buttonColor.getRed(), 255 - buttonColor.getGreen(), 255 - buttonColor.getBlue());
                ((JButton) e.getSource()).setBackground(invertedColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton) e.getSource()).setBackground(buttonColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                buttonLabel = ((JButton) e.getSource()).getText();
                ((JButton) e.getSource()).setText("Clicked!");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ((JButton) e.getSource()).setText(buttonLabel);
            }
        };

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(N, M));

        int x = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                JButton button = new JButton(Integer.toString(++x));

                int r = (int) Math.round(255 * Math.sin(x * Math.PI / N / M / 2));
                int g = 255 - (int) Math.round(255 * Math.sin(x * Math.PI / N / M));
                int b = (int) Math.round(255 * Math.cos(x * Math.PI / N / M - Math.PI / 2));
                Color color = new Color(r, g, b);

                button.setBackground(color);
                button.addMouseListener(mouseAdapter);
                secondPanel.add(button);
            }
        }

        return secondPanel;
    }

    private JPanel initThirdPanel() {
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(new BoxLayout(thirdPanel, BoxLayout.PAGE_AXIS));

        Icon notClickedIcon = new ImageIcon("notClicked.jpg");
        Icon enteredIcon = new ImageIcon("entered.jpg");
        Icon clickedIcon = new ImageIcon("clicked.jpg");
        Icon pressedIcon = new ImageIcon("pressed.jpg");

        ButtonGroup buttonGroup = new ButtonGroup();

        for (int i = 0; i < BUTTON_AMOUNT; ++i) {
            JRadioButton button = new JRadioButton(Integer.toString(i + 1));
            buttonGroup.add(button);
            thirdPanel.add(button);

            button.setIcon(notClickedIcon);
            button.setRolloverIcon(enteredIcon);
            button.setSelectedIcon(clickedIcon);
            button.setPressedIcon(pressedIcon);
        }

        return thirdPanel;
    }

    private void moveLines(int[] indicies, DefaultListModel depart, DefaultListModel dest) {
        int m = indicies.length;

        for (int i = 0; i < m; ++i) {
            dest.addElement(depart.elementAt(indicies[i]));
        }

        for (int i = m - 1; i >= 0; --i) {
            depart.remove(indicies[i]);
        }
    }
}
