import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawArea extends JPanel {
    BufferedImage bufferedImage;

    private final int IMAGE_WIDTH = 1024;
    private final int IMAGE_HEIGHT = 768;

    public DrawArea() {
        super();
        bufferedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().setColor(Color.GREEN);
        bufferedImage.getGraphics().fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, null);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage image) {
        bufferedImage = image;
        this.setPreferredSize(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()));
        paintComponent(this.getGraphics());
    }
}
