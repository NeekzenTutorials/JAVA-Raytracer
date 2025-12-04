package raytracer;

import java.util.Optional;

import raytracer.math.Color;
import raytracer.math.Vector;
import raytracer.math.Point;

public final class RayTracer {
    private final Scene scene;
    private final Orthonormal cameraBasis;
    private final double pixelWidth;
    private final double pixelHeight;

    public RayTracer(Scene scene) {
        this.scene = scene;
        this.cameraBasis = new Orthonormal(scene.getCamera());

        int imageWidth = scene.getWidth();
        int imageHeight = scene.getHeight();
        double fieldOfViewRadians = Math.toRadians(scene.getCamera().getFovDeg());

        this.pixelHeight = 2.0 * Math.tan(fieldOfViewRadians / 2.0) / imageHeight;
        this.pixelWidth = pixelHeight * imageHeight / imageWidth;
    }

    public Scene getScene() { return scene; }

    public Color getPixelColor(int pixelX, int pixelY) {
        Ray rayThroughPixel = computeRayForPixel(pixelX, pixelY);
        Optional<Intersection> closestIntersectionOpt = scene.findClosestIntersection(rayThroughPixel);

        if (closestIntersectionOpt.isPresent()) {
            return scene.shade(closestIntersectionOpt.get(), rayThroughPixel);
        } else {
            return new Color();
        }
    }

    private Ray computeRayForPixel(int pixelX, int pixelY) {
        int imageWidth = scene.getWidth();
        int imageHeight = scene.getHeight();

        double screenX = pixelWidth  * (pixelX - imageWidth  / 2.0 + 0.5);
        double screenY = pixelHeight * (pixelY - imageHeight / 2.0 + 0.5);

        Vector cameraRight  = cameraBasis.u();
        Vector cameraUp     = cameraBasis.v();
        Vector cameraForward = cameraBasis.w();

        Vector rayDirection = cameraRight.scale(screenX)
                                         .add(cameraUp.scale(screenY))
                                         .sub(cameraForward)
                                         .normalized();

        Point rayOrigin = scene.getCamera().getLookFrom();
        return new Ray(rayOrigin, rayDirection);
    }
}
