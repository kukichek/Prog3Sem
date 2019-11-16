import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GUI extends JFrame {
    private JButton notRunningButton;
    private JButton runningButton;

    private final int BUTTON_WIDTH = 100;
    private final int BUTTON_HEIGHT = 60;

    public GUI() {
        super("Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 300, 640, 480);
        setResizable(false);

        JPanel finalPanel = new JPanel();
        finalPanel.setLayout(null);

        JLabel questionLabel = new JLabel("Пойдете ли вы после БГУ в тестировщики?");
        finalPanel.add(questionLabel);
        questionLabel.setBounds(200, 20, 300, 20);

        notRunningButton = new JButton("Да");
        finalPanel.add(notRunningButton);
        notRunningButton.setBounds(140, 180, BUTTON_WIDTH, BUTTON_HEIGHT);

        notRunningButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                questionLabel.setText("ITransition уже вычислили вас по IP");
            }
        });

        runningButton = new JButton("Нет");
        finalPanel.add(runningButton);
        runningButton.setBounds(400, 180, BUTTON_WIDTH, BUTTON_HEIGHT);

        MouseAdapter runningButtonMouseAdapter = new MouseAdapter() {
            private Random rand = new Random();

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                int absoluteMouseXPos = runningButton.getX() + e.getX();
                int absoluteMouseYPos = runningButton.getY() + e.getY();
                int x, y;

                do {
                    x = rand.nextInt(560) + 40;
                    y = rand.nextInt(360) + 40;
                } while (x <= absoluteMouseXPos && absoluteMouseXPos <= x + BUTTON_WIDTH &&
                        y <= absoluteMouseYPos && absoluteMouseYPos <= y + BUTTON_HEIGHT);

                runningButton.setLocation(x, y);
            }
        };

        runningButton.addMouseListener(runningButtonMouseAdapter);

        add(finalPanel);
    }
}
