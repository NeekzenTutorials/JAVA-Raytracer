package raytracer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import raytracer.math.Color;

/**
 * Renders a Scene via a RayTracer into a BufferedImage and saves it to disk.
 */
public final class Renderer {
    private final RayTracer rayTracer;

    /** Constructs a renderer bound to a ray tracer. */
    public Renderer(RayTracer rayTracer) {
        this.rayTracer = rayTracer;
    }

    /**
     * Renders the current scene to an RGB image.
     * Y axis is flipped for conventional image coordinates.
     *
     * @return rendered image
     */
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

    /**
     * Saves an image as PNG to the given filename, creating parent directories if needed.
     *
     * @param image    image to save
     * @param filename output path
     * @throws IOException if write fails
     */
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
