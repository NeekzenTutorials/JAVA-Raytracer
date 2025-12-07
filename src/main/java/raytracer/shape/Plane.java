package raytracer.shape;

import java.util.Optional;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.math.Point;
import raytracer.math.Vector;

/**
 * Infinite plane defined by a point and a (possibly non-unit) normal vector.
 */
public final class Plane extends Shape {
    private final Point point;
    private final Vector normal;

    /**
     * Constructs a plane.
     *
     * @param point  a point on the plane
     * @param normal plane normal (not necessarily normalized)
     */
    public Plane(Point point, Vector normal) {
        this.point = point;
        this.normal = normal;
    }

    /** @return point on the plane */
    public Point point() { return point; }
    /** @return plane normal vector */
    public Vector normal() { return normal; }

    /**
     * Intersects the plane with a ray.
     * Returns empty if parallel to the plane or the hit is behind the origin.
     *
     * @param ray ray to test
     * @return closest valid intersection, if any
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Point origin = ray.origin();
        Vector direction = ray.direction();

        double epsilon = 1e-6;

        double dirDotNormal = direction.dot(normal);
        if (Math.abs(dirDotNormal) < epsilon) {
            return Optional.empty();
        }

        Vector vectorOriginToPlane = point.sub(origin);
        double distanceIntersection = vectorOriginToPlane.dot(normal) / dirDotNormal;

        if (distanceIntersection < epsilon) {
            return Optional.empty();
        }

        Point intersectionPoint = new Point(
            origin.x() + direction.x() * distanceIntersection,
            origin.y() + direction.y() * distanceIntersection,
            origin.z() + direction.z() * distanceIntersection
        );

        Vector normalVector = normal.normalized();

        return Optional.of(new Intersection(distanceIntersection, intersectionPoint, normalVector, this));
    }
}
