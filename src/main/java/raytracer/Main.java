package raytracer;

import java.io.InputStream;
import java.awt.image.BufferedImage;
import raytracer.parsing.SceneFileParser;

public class Main {
    public static void main(String[] args) {
        try {
            Scene scene;

            if (args.length > 0) {
                java.nio.file.Path p = java.nio.file.Path.of(args[0]);
                scene = new SceneFileParser().parse(p);
            } else {
                String resourcePath = "/jalon5/tp55.test";
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
