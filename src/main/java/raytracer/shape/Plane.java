package raytracer.shape;

import java.util.Optional;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.math.Point;
import raytracer.math.Vector;

public final class Plane extends Shape {
    private final Point point;
    private final Vector normal;

    public Plane(Point point, Vector normal) {
        this.point = point;
        this.normal = normal;
    }

    public Point point() { return point; }
    public Vector normal() { return normal; }

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
