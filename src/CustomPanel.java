import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class CustomPanel extends JPanel {
    private Image backgroundImage;

    public CustomPanel(String imagePath) {
        try {
            // Load the image
            backgroundImage = ImageIO.read(new File("C:\\Users\\shank\\OneDrive\\Desktop\\VS code for java\\back.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
