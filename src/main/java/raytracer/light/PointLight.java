package raytracer.light;

import raytracer.math.Color;
import raytracer.math.Point;

/**
 * Point light emitting from a specific origin in space.
 */
public final class PointLight extends AbstractLight {
    private final Point origin;

    /**
     * Constructs a point light.
     *
     * @param origin the light position
     * @param color  the emitted color/intensity
     */
    public PointLight(Point origin, Color color) {
        super(color);
        this.origin = origin;
    }

    /**
     * Returns the light origin (position).
     *
     * @return origin point
     */
    public Point origin() { return origin; }
}
