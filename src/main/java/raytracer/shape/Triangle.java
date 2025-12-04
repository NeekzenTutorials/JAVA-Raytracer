package raytracer.shape;

import java.util.Optional;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.math.Point;
import raytracer.math.Vector;

public final class Triangle extends Shape {
    private final Point a, b, c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a; this.b = b; this.c = c;
    }

    public Point a() { return a; }
    public Point b() { return b; }
    public Point c() { return c; }

    @Override
    public Optional<Intersection> intersect(Ray ray) {
        Point origin = ray.origin();
        Vector direction = ray.direction();

        double epsilon = 1e-6;

        Vector edge1 = b.sub(a);
        Vector edge2 = c.sub(a);

        Vector directionCrossEdge2 = direction.cross(edge2);

        double determinant = edge1.dot(directionCrossEdge2);
        if (Math.abs(determinant) < epsilon) {
            return Optional.empty();
        }

        double inverseDeterminant = 1.0 / determinant;

        Vector originToVertex = origin.sub(a);

        double u = originToVertex.dot(directionCrossEdge2) * inverseDeterminant;
        if (u < 0.0 || u > 1.0) {
            return Optional.empty();
        }

        Vector originToVertexCrossEdge1 = originToVertex.cross(edge1);

        double v = direction.dot(originToVertexCrossEdge1) * inverseDeterminant;
        if (v < 0.0 || u + v > 1.0) {
            return Optional.empty();
        }

        double distanceIntersection = edge2.dot(originToVertexCrossEdge1) * inverseDeterminant;
        if (distanceIntersection < epsilon) {
            return Optional.empty();
        }

        Point intersectionPoint = new Point(
            origin.x() + direction.x() * distanceIntersection,
            origin.y() + direction.y() * distanceIntersection,
            origin.z() + direction.z() * distanceIntersection
        );

        Vector normalVector = edge1.cross(edge2).normalized();

        return Optional.of(new Intersection(distanceIntersection, intersectionPoint, normalVector, this));
    }
}
