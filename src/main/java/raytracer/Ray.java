package raytracer;

import raytracer.math.Point;
import raytracer.math.Vector;

/**
 * Ray defined by an origin point and a (typically normalized) direction vector.
 */
public final class Ray {
    private final Point origin;
    private final Vector direction;

    /** Constructs a ray with given origin and direction. */
    public Ray(Point origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    /** @return ray origin */
    public Point origin() { return origin; }
    /** @return ray direction */
    public Vector direction() { return direction; }
}
