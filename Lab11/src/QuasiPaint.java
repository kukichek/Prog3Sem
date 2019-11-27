import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class QuasiPaint extends JFrame {
    private final int FRAME_WIDTH = 720;
    private final int FRAME_HEIGHT = 536 + 24;
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 36;
    private final int IMAGE_WIDTH = 1024;
    private final int IMAGE_HEIGHT = 768;

    private int x1, x2, y1, y2;

    private JButton saveFileButton;
    private JButton loadFileButton;
    private JButton chooseColorButton;

    private Color penColor;
    private JColorChooser colorChooser;
    private JFileChooser fileChooser;

    private File file;
    private FileNameExtensionFilter imageFileFilter;

    private JScrollPane scrollPane;
    private Graphics displayedImageGraphics;
    private Graphics bufferedImageGraphics;

    private DrawArea drawArea;

    public QuasiPaint() {
        super("QuasiPaint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 200, FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);

        penColor = Color.BLACK;
        colorChooser = new JColorChooser();
        fileChooser = new JFileChooser();
        imageFileFilter = new FileNameExtensionFilter("Image files",
                "jpg", "jpeg", "png", "JPG", "JPEG", "PNG");

        saveFileButton = new JButton("Save File");
        loadFileButton = new JButton("Load File");
        chooseColorButton = new JButton("Choose Pen Color");

        drawArea = new DrawArea();

        chooseColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                penColor = colorChooser.showDialog(null, "Select color of the pen", Color.BLACK);
            }
        });

        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int choosedOption = fileChooser.showSaveDialog(null);

                if (choosedOption == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();

                    try {
                        ImageIO.write(drawArea.getBufferedImage(), "png", file);
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(null, "Uncorrect output file");
                    }
                }
            }
        });

        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                fileChooser.setFileFilter(imageFileFilter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int choosedOption = fileChooser.showOpenDialog(null);

                if (choosedOption == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();

                    try {
                        drawArea.setBufferedImage(ImageIO.read(file));
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(null, "Uncorrect input file");
                    }
                }
            }
        });

        scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        drawArea.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        scrollPane.getViewport().add(drawArea, null);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        buttonsPanel.add(chooseColorButton);
        buttonsPanel.add(saveFileButton);
        buttonsPanel.add(loadFileButton);

        chooseColorButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        saveFileButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        loadFileButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        MouseAdapter displayedImageMsAd = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                x1 = e.getX();
                y1 = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                displayedImageGraphics = drawArea.getGraphics();
                bufferedImageGraphics = drawArea.getBufferedImage().getGraphics();
                x2 = e.getX();
                y2 = e.getY();

                displayedImageGraphics.setColor(penColor);
                displayedImageGraphics.drawLine(x1, y1, x2, y2);
                bufferedImageGraphics.setColor(penColor);
                bufferedImageGraphics.drawLine(x1, y1, x2, y2);

                x1 = x2;
                y1 = y2;
            }
        };

        drawArea.addMouseListener(displayedImageMsAd);
        drawArea.addMouseMotionListener(displayedImageMsAd);

        JPanel finalPanel = new JPanel();
        finalPanel.setLayout(new BorderLayout());

        finalPanel.add(buttonsPanel, BorderLayout.NORTH);
        finalPanel.add(scrollPane, BorderLayout.CENTER);

        add(finalPanel);
    }
}
