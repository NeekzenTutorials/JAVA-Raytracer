package raytracer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;

import javax.imageio.ImageIO;

import raytracer.math.Color;

public final class Renderer {
    private final RayTracer rayTracer;

    public Renderer(RayTracer rayTracer) {
        this.rayTracer = rayTracer;
    }

    public BufferedImage render() {
        Scene scene = rayTracer.getScene();
        int imageWidth = scene.getWidth();
        int imageHeight = scene.getHeight();

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        for (int pixelY = 0; pixelY < imageHeight; pixelY++) {
            for (int pixelX = 0; pixelX < imageWidth; pixelX++) {
                Color pixelColor = rayTracer.getPixelColor(pixelX, pixelY);
                int rgb = pixelColor.toRGB();

                int flippedY = imageHeight - 1 - pixelY;
                image.setRGB(pixelX, flippedY, rgb);
            }
        }

        return image;
    }

    public void save(BufferedImage image, String filename) throws IOException {
        Path outputPath = Paths.get(filename);
        Path parentDirectory = outputPath.getParent();
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }
        ImageIO.write(image, "png", outputPath.toFile());
        System.out.println("Image écrite : " + outputPath.toAbsolutePath());
    }
}
