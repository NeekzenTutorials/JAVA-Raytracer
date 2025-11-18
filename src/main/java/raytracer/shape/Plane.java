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
        // TODO: implémenter l’intersection rayon-plan plus tard.
        return Optional.empty();
    }
}
