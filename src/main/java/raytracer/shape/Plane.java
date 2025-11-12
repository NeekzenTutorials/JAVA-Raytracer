package raytracer.shape;

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
}
