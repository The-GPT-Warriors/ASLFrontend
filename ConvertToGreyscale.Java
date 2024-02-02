import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageConverter {

    public static void main(String[] args) throws IOException {
        // Load an image from a file
        File inputFile = new File("/Users/anthony/Downloads/LetterA.jpg");
        BufferedImage originalImage = ImageIO.read(inputFile);

        // Convert to grayscale and resize
        BufferedImage processedImage = processImage(originalImage, 0.5f); // Adjust brightness by 0.5

        // Save the processed image to a new file
        ImageIO.write(processedImage, "jpg", new File("/Users/anthony/Downloads/LetterAOutput.jpg"));
    }

    private static BufferedImage processImage(BufferedImage originalImage, float brightnessFactor) {
        // Convert to grayscale
        BufferedImage grayImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImage.getGraphics();
        g.drawImage(originalImage, 0, 0, null);
        g.dispose();

        // Resize the image
        Image tmp = grayImage.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        // Adjust brightness
        return changeBrightness(resizedImage, brightnessFactor);
    }

    private static BufferedImage changeBrightness(BufferedImage image, float factor) {
        RescaleOp op = new RescaleOp(factor, 0, null);
        return op.filter(image, null);
    }
}