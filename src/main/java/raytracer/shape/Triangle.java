package raytracer.shape;

import raytracer.math.Point;

public final class Triangle extends Shape {
    private final Point a, b, c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a; this.b = b; this.c = c;
    }

    public Point a() { return a; }
    public Point b() { return b; }
    public Point c() { return c; }
}
