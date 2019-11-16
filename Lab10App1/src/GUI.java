import javax.swing.*;
import java.awt.event.*;

public class GUI extends JFrame {
    private JButton button;
    private JLabel mouseCoordinatesLabel;

    private final int BUTTON_HEIGHT = 50;
    private final int BUTTON_WIDTH = 150;
    private final int X_OFFSET = 7;
    private final int Y_OFFSET = 30;

    public GUI() {
        super("Drag and drop");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 300, 640, 480);
        setResizable(false);

        JPanel finalPanel = new JPanel();
        finalPanel.setLayout(null);

        MouseAdapter buttonMouseAdapter = new MouseAdapter() {
            private int pressedXPos, pressedYPos;

            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                int x = button.getX() + mouseEvent.getX();
                int y = button.getY() + mouseEvent.getY();

                if (mouseEvent.isControlDown()) {
                    button.setLocation(x - pressedXPos, y - pressedYPos);
                }

                mouseCoordinatesLabel.setText("x: " + x + "; y: " + y);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressedXPos = e.getX();
                pressedYPos = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                int x = button.getX() + mouseEvent.getX();
                int y = button.getY() + mouseEvent.getY();

                mouseCoordinatesLabel.setText("x: " + x + "; y: " + y);
            }
        };

        button = new JButton("");
        button.addMouseMotionListener(buttonMouseAdapter);
        button.addMouseListener(buttonMouseAdapter);

        finalPanel.add(button);
        button.setBounds(640 / 2 - BUTTON_WIDTH / 2, 450 / 2 - BUTTON_HEIGHT / 2,
                BUTTON_WIDTH, BUTTON_HEIGHT);

        mouseCoordinatesLabel = new JLabel("x: 0; y: 0;");
        finalPanel.add(mouseCoordinatesLabel);
        mouseCoordinatesLabel.setBounds(5, 410, 600, 40);

        MouseAdapter finalPanelMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX() - X_OFFSET;
                int y = e.getY() - Y_OFFSET;

                button.setLocation(x - BUTTON_WIDTH / 2, y - BUTTON_HEIGHT / 2);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                int x = e.getX() - X_OFFSET;
                int y = e.getY() - Y_OFFSET;

                mouseCoordinatesLabel.setText("x: " + x + "; y: " + y);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                int x = e.getX() - X_OFFSET;
                int y = e.getY() - Y_OFFSET;

                mouseCoordinatesLabel.setText("x: " + x + "; y: " + y);
            }
        };

        addMouseListener(finalPanelMouseAdapter);
        addMouseMotionListener(finalPanelMouseAdapter);

        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                String buttonText = button.getText();
                if (keyEvent.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    if (buttonText.length() != 0) {
                        button.setText(buttonText.substring(0, buttonText.length() - 1));
                    }
                } else {
                    button.setText(buttonText + String.valueOf(keyEvent.getKeyChar()));
                }
            }
        });

        add(finalPanel);
    }
}
