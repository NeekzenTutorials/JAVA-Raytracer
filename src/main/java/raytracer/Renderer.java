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
        int width = scene.getWidth();
        int height = scene.getHeight();

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                Color c = rayTracer.getPixelColor(i, j);
                int rgb = c.toRGB();

                int yImage = height - 1 - j;
                img.setRGB(i, yImage, rgb);
            }
        }

        return img;
    }

    public void save(BufferedImage img, String filename) throws IOException {
        Path out = Paths.get(filename);
        Path parent = out.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        ImageIO.write(img, "png", out.toFile());
        System.out.println("Image écrite : " + out.toAbsolutePath());
    }
}
