package raytracer.parsing;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Locale;

import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.math.Color;
import raytracer.Camera;
import raytracer.Scene;
import raytracer.light.DirectionalLight;
import raytracer.light.PointLight;
import raytracer.shape.Plane;
import raytracer.shape.Sphere;
import raytracer.shape.Triangle;

/**
 * Parser for the custom scene description format.
 * Supported keywords: size, output, camera, ambient, diffuse, specular, shininess,
 * directional, point, sphere, plane, maxverts, vertex, tri.
 * Performs validations (e.g., positive sizes/radii, ambient+diffuse ≤ 1, lights sum ≤ 1).
 * Throws SceneParseException on invalid input.
 */
public final class SceneFileParser {

    /**
     * Parses a scene file from disk.
     *
     * @param file path to the scene file
     * @return populated Scene
     * @throws SceneParseException if IO error or invalid content
     */
    public Scene parse(Path file) {
        try (InputStream in = Files.newInputStream(file)) {
            return parse(in, file.toString());
        } catch (IOException e) {
            throw new SceneParseException("Erreur lecture " + file + " : " + e.getMessage(), e);
        }
    }

    /**
     * Parses a scene from an InputStream.
     * Requires at least "size" and "camera" to be present.
     *
     * @param in         input stream containing scene text (UTF-8)
     * @param sourceName source name for error reporting
     * @return populated Scene
     * @throws SceneParseException on invalid syntax or semantic errors
     */
    public Scene parse(InputStream in, String sourceName) {
        Locale.setDefault(Locale.ROOT);
        Scene scene = new Scene();

        Color currentDiffuse = new Color(0,0,0);
        Color currentSpecular = new Color(0,0,0);
        double currentShininess = 0.0;
        int maxverts = -1;
        var vertices = new ArrayList<Point>();
        double sumLr = 0, sumLg = 0, sumLb = 0;
        boolean sizeSeen = false, cameraSeen = false;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] t = line.split("\\s+");
                String key = t[0].toLowerCase(Locale.ROOT);

                try {
                    switch (key) {
                        case "size" -> {
                            requireArgs(t, 3, lineNo);
                            int w = Integer.parseInt(t[1]);
                            int h = Integer.parseInt(t[2]);
                            if (w <= 0 || h <= 0) throw err(lineNo, "size doit être > 0");
                            scene.setWidth(w);
                            scene.setHeight(h);
                            sizeSeen = true;
                        }
                        case "output" -> {
                            requireArgs(t, 2, lineNo);
                            scene.setOutput(t[1]);
                        }
                        case "camera" -> {
                            requireArgs(t, 11, lineNo);
                            double x = d(t[1]), y = d(t[2]), z = d(t[3]);
                            double u = d(t[4]), v = d(t[5]), wv = d(t[6]);
                            double m = d(t[7]), n = d(t[8]), o = d(t[9]);
                            double fov = d(t[10]);
                            var lookFrom = new Point(x,y,z);
                            var lookAt   = new Point(u,v,wv);
                            var up       = new Vector(m,n,o);
                            scene.setCamera(new Camera(lookFrom, lookAt, up, fov));
                            cameraSeen = true;
                        }
                        case "ambient" -> {
                            requireArgs(t, 4, lineNo);
                            Color amb = new Color(d(t[1]), d(t[2]), d(t[3]));
                            scene.setAmbient(amb);
                            if (!leq1PerComponent(amb, currentDiffuse)) {
                                throw err(lineNo, "(ambient + diffuse) dépasse 1 sur au moins une composante");
                            }
                        }
                        case "diffuse" -> {
                            requireArgs(t, 4, lineNo);
                            currentDiffuse = new Color(d(t[1]), d(t[2]), d(t[3]));
                            if (!leq1PerComponent(scene.getAmbient(), currentDiffuse)) {
                                throw err(lineNo, "(ambient + diffuse) dépasse 1 sur au moins une composante");
                            }
                        }
                        case "specular" -> {
                            requireArgs(t, 4, lineNo);
                            currentSpecular = new Color(d(t[1]), d(t[2]), d(t[3]));
                        }
                        case "shininess" -> {
                            requireArgs(t, 2, lineNo);
                            currentShininess = d(t[1]);
                        }
                        case "directional" -> {
                            requireArgs(t, 7, lineNo);
                            var dir = new Vector(d(t[1]), d(t[2]), d(t[3]));
                            var col = new Color(d(t[4]), d(t[5]), d(t[6]));
                            // accumulate and check
                            sumLr += col.r(); sumLg += col.g(); sumLb += col.b();
                            if (sumLr > 1.0 || sumLg > 1.0 || sumLb > 1.0) {
                                throw err(lineNo, "Somme des couleurs des lumières > 1 sur au moins une composante");
                            }
                            scene.addLight(new DirectionalLight(dir, col));
                        }
                        case "point" -> {
                            requireArgs(t, 7, lineNo);
                            var p = new Point(d(t[1]), d(t[2]), d(t[3]));
                            var col = new Color(d(t[4]), d(t[5]), d(t[6]));
                            sumLr += col.r(); sumLg += col.g(); sumLb += col.b();
                            if (sumLr > 1.0 || sumLg > 1.0 || sumLb > 1.0) {
                                throw err(lineNo, "Somme des couleurs des lumières > 1 sur au moins une composante");
                            }
                            scene.addLight(new PointLight(p, col));
                        }
                        case "sphere" -> {
                            requireArgs(t, 5, lineNo);
                            var c = new Point(d(t[1]), d(t[2]), d(t[3]));
                            double r = d(t[4]);
                            if (r <= 0) throw err(lineNo, "rayon de sphere doit être > 0");
                            var s = new Sphere(c, r);
                            // appliquer les dernières couleurs
                            s.setDiffuse(currentDiffuse);
                            s.setSpecular(currentSpecular);
                            s.setShininess(currentShininess);
                            scene.addShape(s);
                        }
                        case "plane" -> {
                            requireArgs(t, 7, lineNo);
                            var p = new Point(d(t[1]), d(t[2]), d(t[3]));
                            var n = new Vector(d(t[4]), d(t[5]), d(t[6]));
                            var s = new Plane(p, n);
                            s.setDiffuse(currentDiffuse);
                            s.setSpecular(currentSpecular);
                            s.setShininess(currentShininess);
                            scene.addShape(s);
                        }
                        case "maxverts" -> {
                            requireArgs(t, 2, lineNo);
                            maxverts = Integer.parseInt(t[1]);
                            if (maxverts < 0) throw err(lineNo, "maxverts doit être ≥ 0");
                            vertices.ensureCapacity(maxverts);
                        }
                        case "vertex" -> {
                            requireArgs(t, 4, lineNo);
                            if (maxverts < 0) throw err(lineNo, "Déclarer maxverts avant vertex");
                            if (vertices.size() >= maxverts) throw err(lineNo, "Trop de vertex (au-delà de maxverts)");
                            vertices.add(new Point(d(t[1]), d(t[2]), d(t[3])));
                        }
                        case "tri" -> {
                            requireArgs(t, 4, lineNo);
                            int a = Integer.parseInt(t[1]);
                            int b = Integer.parseInt(t[2]);
                            int c = Integer.parseInt(t[3]);
                            if (maxverts < 0) throw err(lineNo, "Déclarer maxverts/vertex avant tri");
                            if (a < 0 || b < 0 || c < 0) throw err(lineNo, "Indices tri doivent être ≥ 0");
                            if (a >= vertices.size() || b >= vertices.size() || c >= vertices.size())
                                throw err(lineNo, "Indices tri hors bornes (< maxverts et < nb vertex lus)");
                            var tri = new Triangle(vertices.get(a), vertices.get(b), vertices.get(c));
                            tri.setDiffuse(currentDiffuse);
                            tri.setSpecular(currentSpecular);
                            tri.setShininess(currentShininess);
                            scene.addShape(tri);
                        }
                        default -> throw err(lineNo, "Mot-clé inconnu: " + key);
                    }
                } catch (NumberFormatException nfe) {
                    throw err(lineNo, "Nombre invalide: " + nfe.getMessage(), nfe);
                }
            }
        } catch (IOException e) {
            throw new SceneParseException("Erreur lecture " + sourceName + " : " + e.getMessage(), e);
        }

        if (!sizeSeen)   throw new SceneParseException("size manquant (obligatoire)");
        if (!cameraSeen) throw new SceneParseException("camera manquante (obligatoire)");
        return scene;
    }

    /**
     * Ensures the token array has the expected length.
     *
     * @param t        tokens
     * @param expected expected token count
     * @param lineNo   current line number for error messages
     * @throws SceneParseException if insufficient arguments
     */
    private static void requireArgs(String[] t, int expected, int lineNo) {
        if (t.length < expected) {
            throw err(lineNo, "Arguments insuffisants (attendu " + (expected - 1) + " après le mot-clé)");
        }
    }

    /** Parses a double value from a token. */
    private static double d(String s) { return Double.parseDouble(s); }

    /**
     * Checks per-component that a + b ≤ 1 (with small tolerance).
     */
    private static boolean leq1PerComponent(Color a, Color b) {
        return a.r() + b.r() <= 1.0 + 1e-12
            && a.g() + b.g() <= 1.0 + 1e-12
            && a.b() + b.b() <= 1.0 + 1e-12;
    }

    /**
     * Builds a formatted parsing exception with line number.
     */
    private static SceneParseException err(int lineNo, String msg) {
        return new SceneParseException("Ligne " + lineNo + " : " + msg);
    }

    /**
     * Builds a formatted parsing exception with line number and cause.
     */
    private static SceneParseException err(int lineNo, String msg, Throwable cause) {
        return new SceneParseException("Ligne " + lineNo + " : " + msg, cause);
    }
}
