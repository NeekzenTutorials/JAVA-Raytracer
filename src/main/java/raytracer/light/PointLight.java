package raytracer.light;

import raytracer.math.Point;
import raytracer.math.Color;

public final class PointLight extends AbstractLight {
    private final Point origin;

    public PointLight(Point origin, Color color) {
        super(color);
        this.origin = origin;
    }

    public Point origin() { return origin; }
}
