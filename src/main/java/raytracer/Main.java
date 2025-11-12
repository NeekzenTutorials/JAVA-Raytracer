package raytracer;

import java.io.InputStream;
import raytracer.parsing.SceneFileParser;
public class Main {
    public static void main(String[] args) {
        try {
            Scene scene;
            if (args.length > 0) {
                // Chemin fichier système si tu passes un chemin réel
                scene = new SceneFileParser().parse(java.nio.file.Path.of(args[0]));
            } else {
                // Lecture depuis le classpath (dans le JAR)
                String resourcePath = "/jalon2/test6.scene";
                try (InputStream in = Main.class.getResourceAsStream(resourcePath)) {
                    if (in == null) {
                        throw new IllegalArgumentException("Ressource introuvable dans le classpath: " + resourcePath);
                    }
                    System.out.println("Lecture de la ressource: " + resourcePath);
                    scene = new SceneFileParser().parse(in, resourcePath);
                }
            }

            System.out.println("Taille: " + scene.getWidth() + "x" + scene.getHeight());
            System.out.println("Output: " + scene.getOutput());
            System.out.println("Shapes: " + scene.getShapes().size());
            System.out.println("Lights: " + scene.getLights().size());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
