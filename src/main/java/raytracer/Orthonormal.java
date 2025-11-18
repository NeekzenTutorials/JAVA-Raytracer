package raytracer;

import raytracer.math.Point;
import raytracer.math.Vector;

public final class Orthonormal {
    private final Vector u;
    private final Vector v;
    private final Vector w;

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

    public Vector u() { return u; }
    public Vector v() { return v; }
    public Vector w() { return w; }
}
