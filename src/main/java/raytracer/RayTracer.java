package raytracer;

import java.util.Optional;

import raytracer.math.Color;
import raytracer.math.Point;
import raytracer.math.Vector;

/**
 * Core ray tracing engine responsible for generating camera rays and shading pixels.
 */
public final class RayTracer {
    private final Scene scene;
    private final Orthonormal cameraBasis;
    private final double pixelWidth;
    private final double pixelHeight;

    /**
     * Initializes camera basis and pixel size from the scene's camera and image dimensions.
     *
     * @param scene scene to render
     */
    public RayTracer(Scene scene) {
        this.scene = scene;
        this.cameraBasis = new Orthonormal(scene.getCamera());

        int imageWidth = scene.getWidth();
        int imageHeight = scene.getHeight();
        double fieldOfViewRadians = Math.toRadians(scene.getCamera().getFovDeg());

        this.pixelHeight = 2.0 * Math.tan(fieldOfViewRadians / 2.0) / imageHeight;
        this.pixelWidth = pixelHeight * imageHeight / imageWidth;
    }

    /** @return bound scene */
    public Scene getScene() { return scene; }

    /**
     * Computes the color for a given pixel by tracing a ray and shading the closest hit.
     *
     * @param pixelX pixel column (0..width-1)
     * @param pixelY pixel row (0..height-1)
     * @return pixel color
     */
    public Color getPixelColor(int pixelX, int pixelY) {
        Ray rayThroughPixel = computeRayForPixel(pixelX, pixelY);
        Optional<Intersection> closestIntersectionOpt = scene.findClosestIntersection(rayThroughPixel);

        if (closestIntersectionOpt.isPresent()) {
            return scene.shade(closestIntersectionOpt.get(), rayThroughPixel);
        } else {
            return new Color();
        }
    }

    /**
     * Builds a primary ray through the pixel center using the camera basis and FOV.
     *
     * @param pixelX pixel column
     * @param pixelY pixel row
     * @return ray in world space
     */
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
