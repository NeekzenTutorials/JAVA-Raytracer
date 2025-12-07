package raytracer.light;

import raytracer.math.Color;

/**
 * Base class for lights in the raytracer, holding the emitted color/intensity.
 * Subclasses provide position/direction specifics.
 */
public abstract class AbstractLight {
    protected final Color color;

    /**
     * Constructs a light with the given color/intensity.
     *
     * @param color the light color (radiance/intensity)
     */
    protected AbstractLight(Color color) {
        this.color = color;
    }

    /**
     * Returns the light color/intensity.
     *
     * @return light color
     */
    public Color color() { return color; }
}
