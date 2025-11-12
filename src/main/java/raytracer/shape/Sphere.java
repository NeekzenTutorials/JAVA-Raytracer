package raytracer.shape;

import raytracer.math.Point;

public final class Sphere extends Shape {
    private final Point center;
    private final double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point center() { return center; }
    public double radius() { return radius; }
}
