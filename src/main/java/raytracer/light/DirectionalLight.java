package raytracer.light;

import raytracer.math.Color;
import raytracer.math.Vector;

/**
 * Directional light with a fixed direction (infinitely distant source).
 */
public final class DirectionalLight extends AbstractLight {
    private final Vector direction;

    /**
     * Constructs a directional light.
     *
     * @param direction the normalized direction the light travels
     * @param color     the emitted color/intensity
     */
    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = direction;
    }

    /**
     * Returns the light direction.
     *
     * @return direction vector
     */
    public Vector direction() { return direction; }
}
