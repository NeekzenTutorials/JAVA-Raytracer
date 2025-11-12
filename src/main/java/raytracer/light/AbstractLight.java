package raytracer.light;

import raytracer.math.Color;

public abstract class AbstractLight {
    protected final Color color;

    protected AbstractLight(Color color) {
        this.color = color;
    }

    public Color color() { return color; }
}
