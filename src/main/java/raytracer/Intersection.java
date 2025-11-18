package raytracer;

import raytracer.math.Point;
import raytracer.shape.Shape;

public final class Intersection {
    private final double t;
    private final Point position;
    private final Shape shape;

    public Intersection(double t, Point position, Shape shape) {
        this.t = t;
        this.position = position;
        this.shape = shape;
    }

    public double t() { return t; }
    public Point position() { return position; }
    public Shape shape() { return shape; }
}
