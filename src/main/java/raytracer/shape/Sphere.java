package raytracer.shape;

import java.util.Optional;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.math.Point;
import raytracer.math.Vector;

/**
 * Sphere defined by a center point and radius.
 * Intersection uses quadratic solution and returns the closest positive hit.
 */
public final class Sphere extends Shape {
    private final Point center;
    private final double radius;

    /**
     * Constructs a sphere.
     *
     * @param center sphere center
     * @param radius sphere radius (> 0)
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /** @return sphere center */
    public Point center() { return center; }
    /** @return sphere radius */
    public double radius() { return radius; }

    /**
     * Intersects the sphere with a ray.
     * Returns empty if no real positive solution or intersections behind the origin.
     *
     * @param ray ray to test
     * @return closest valid intersection, if any
     */
    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Point origin = ray.origin();
        Vector direction = ray.direction();

        Vector originToCenterVector = origin.sub(center);

        double quadraticA = direction.dot(direction);
        double quadraticB = 2.0 * originToCenterVector.dot(direction);
        double quadraticC = originToCenterVector.dot(originToCenterVector) - radius * radius;

        double discriminant = quadraticB * quadraticB - 4 * quadraticA * quadraticC;
        if (discriminant < 0.0) {
            return Optional.empty();
        }

        double sqrtDiscriminant = Math.sqrt(discriminant);
        double inverseTwoA = 1.0 / (2.0 * quadraticA);

        double t1 = (-quadraticB - sqrtDiscriminant) * inverseTwoA;
        double t2 = (-quadraticB + sqrtDiscriminant) * inverseTwoA;

        double epsilon = 1e-6;
        double distanceIntersection;
        if (t1 > epsilon && t2 > epsilon) {
            distanceIntersection = Math.min(t1, t2);
        } else if (t1 > epsilon) {
            distanceIntersection = t1;
        } else if (t2 > epsilon) {
            distanceIntersection = t2;
        } else {
            return Optional.empty();
        }

        Point intersectionPoint = new Point(
            origin.x() + direction.x() * distanceIntersection,
            origin.y() + direction.y() * distanceIntersection,
            origin.z() + direction.z() * distanceIntersection
        );

        Vector normalVector = intersectionPoint.sub(center).normalized();

        return Optional.of(new Intersection(distanceIntersection, intersectionPoint, normalVector, this));
    }
}
