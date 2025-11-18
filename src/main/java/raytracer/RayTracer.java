package raytracer;

import java.util.Optional;

import raytracer.math.Color;
import raytracer.math.Vector;
import raytracer.math.Point;

public final class RayTracer {
    private final Scene scene;
    private final Orthonormal basis;
    private final double pixelWidth;
    private final double pixelHeight;

    public RayTracer(Scene scene) {
        this.scene = scene;
        this.basis = new Orthonormal(scene.getCamera());

        int width = scene.getWidth();
        int height = scene.getHeight();
        double fovRad = Math.toRadians(scene.getCamera().getFovDeg());

        this.pixelHeight = 2.0 * Math.tan(fovRad / 2.0) / height;
        this.pixelWidth = pixelHeight * height / width;
    }

    public Scene getScene() { return scene; }

    public Color getPixelColor(int i, int j) {
        Ray ray = computeRayForPixel(i, j);
        Optional<Intersection> hitOpt = scene.findClosestIntersection(ray);

        if (hitOpt.isPresent()) {
            return scene.shade(hitOpt.get());
        } else {
            return new Color();
        }
    }

    private Ray computeRayForPixel(int i, int j) {
        int width = scene.getWidth();
        int height = scene.getHeight();

        double a = pixelWidth  * (i - width  / 2.0 + 0.5);
        double b = pixelHeight * (j - height / 2.0 + 0.5);

        Vector u = basis.u();
        Vector v = basis.v();
        Vector w = basis.w();

        Vector dir = u.scale(a)
                      .add(v.scale(b))
                      .sub(w)
                      .normalized();

        Point origin = scene.getCamera().getLookFrom();
        return new Ray(origin, dir);
    }
}
