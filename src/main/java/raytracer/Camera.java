package raytracer;

import raytracer.math.Point;
import raytracer.math.Vector;

public final class Camera {
    public final Point lookFrom;
    public final Point lookAt;
    public final Vector up;
    public final double fovDeg;

    public Camera(Point lookFrom, Point lookAt, Vector up, double fovDeg) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.up = up;
        this.fovDeg = fovDeg;
    }

    public Point getLookFrom() {
        return lookFrom;
    }

    public Point getLookAt() {
        return lookAt;
    }

    public Vector getUp() {
        return up;
    }

    public double getFovDeg() {
        return fovDeg;
    }
}