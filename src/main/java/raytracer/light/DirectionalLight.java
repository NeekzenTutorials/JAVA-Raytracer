package raytracer.light;

import raytracer.math.Vector;
import raytracer.math.Color;

public final class DirectionalLight extends AbstractLight {
    private final Vector direction;

    public DirectionalLight(Vector direction, Color color) {
        super(color);
        this.direction = direction;
    }

    public Vector direction() { return direction; }
}
