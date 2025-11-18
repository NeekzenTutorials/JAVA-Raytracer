package raytracer;

import raytracer.math.Point;
import raytracer.math.Vector;

public final class Ray {
    private final Point origin;
    private final Vector direction;

    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Point origin() { return origin; }
    public Vector direction() { return direction; }
}
