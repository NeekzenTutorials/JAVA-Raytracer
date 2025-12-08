package raytracer;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import raytracer.parsing.SceneFileParser;

/**
 * Entry point for the raytracer executable.
 * Parses a scene file (from CLI path or bundled resource), renders, and saves the PNG.
 */
public class Main {
    /**
     * Main application entry. If no argument is provided, loads a default resource scene.
     *
     * @param args CLI arguments: [0] optional path to scene file
     */
    public static void main(String[] args) {
        try {
            Scene scene;

            if (args.length > 0) {
                java.nio.file.Path p = java.nio.file.Path.of(args[0]);
                scene = new SceneFileParser().parse(p);
            } else {
                String resourcePath = "/final/final_avec_bonus.scene";
                System.out.println("Lecture de la ressource: " + resourcePath);
                try (InputStream in = Main.class.getResourceAsStream(resourcePath)) {
                    if (in == null) {
                        throw new IllegalArgumentException("Ressource introuvable: " + resourcePath);
                    }
                    scene = new SceneFileParser().parse(in, resourcePath);
                }
            }

            RayTracer rayTracer = new RayTracer(scene);
            Renderer renderer = new Renderer(rayTracer);

            BufferedImage img = renderer.render();
            renderer.save(img, scene.getOutput());

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
