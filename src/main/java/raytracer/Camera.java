package raytracer;

import raytracer.math.Point;
import raytracer.math.Vector;

/**
 * Pinhole camera model with lookFrom, lookAt, up vector, and vertical field of view in degrees.
 */
public final class Camera {
    public final Point lookFrom;
    public final Point lookAt;
    public final Vector up;
    public final double fovDeg;

    /**
     * Constructs a camera.
     *
     * @param lookFrom camera origin
     * @param lookAt   target point
     * @param up       up direction
     * @param fovDeg   vertical field of view in degrees
     */
    public Camera(Point lookFrom, Point lookAt, Vector up, double fovDeg) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fovDeg = fovDeg;
    }

    /** @return camera origin */
    public Point getLookFrom() {
        return lookFrom;
    }

    /** @return target point */
    public Point getLookAt() {
        return lookAt;
    }

    /** @return up direction */
    public Vector getUp() {
        return up;
    }

    /** @return vertical FOV in degrees */
    public double getFovDeg() {
        return fovDeg;
    }
}