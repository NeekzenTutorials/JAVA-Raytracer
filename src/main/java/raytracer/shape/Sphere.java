package raytracer.shape;

import java.util.Optional;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.math.Point;
import raytracer.math.Vector;

public final class Sphere extends Shape {
    private final Point center;
    private final double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point center() { return center; }
    public double radius() { return radius; }

    public Optional<Intersection> intersect(Ray ray) {
        Point o = ray.origin();
        Vector d = ray.direction();

        Vector oc = o.sub(center);

        double a = d.dot(d);
        double b = 2.0 * oc.dot(d);
        double c = oc.dot(oc) - radius * radius;

        double discriminant = b*b - 4*a*c;
        if (discriminant < 0.0) {
            return Optional.empty();
        }

        double sqrtD = Math.sqrt(discriminant);
        double inv2a = 1.0 / (2.0 * a);

        double t1 = (-b - sqrtD) * inv2a;
        double t2 = (-b + sqrtD) * inv2a;

        double eps = 1e-6;
        double t;
        if (t1 > eps && t2 > eps) {
            t = Math.min(t1, t2);
        } else if (t1 > eps) {
            t = t1;
        } else if (t2 > eps) {
            t = t2;
        } else {
            return Optional.empty();
        }

        Point p = new Point(
            o.x() + d.x() * t,
            o.y() + d.y() * t,
            o.z() + d.z() * t
        );
        return Optional.of(new Intersection(t, p, this));
    }
}
