package raytracer;

import raytracer.math.Point;
import raytracer.math.Vector;

/**
 * Builds an orthonormal camera basis (u, v, w) from camera parameters.
 * w points from lookAt to lookFrom, u = up × w, v = w × u.
 */
public final class Orthonormal {
    private final Vector u;
    private final Vector v;
    private final Vector w;

    /**
     * Constructs the basis using the camera look vectors.
     *
     * @param camera camera
     */
    public Orthonormal(Camera camera) {
        Point lookFrom = camera.getLookFrom();
        Point lookAt   = camera.getLookAt();
        Vector up      = camera.getUp();

        Vector w = Vector.fromPoints(lookAt, lookFrom).normalized();
        Vector u = up.cross(w).normalized();
        Vector v = w.cross(u).normalized();

        this.u = u;
        this.v = v;
        this.w = w;
    }

    /** @return right vector u */
    public Vector u() { return u; }
    /** @return up vector v */
    public Vector v() { return v; }
    /** @return forward vector w */
    public Vector w() { return w; }
}
