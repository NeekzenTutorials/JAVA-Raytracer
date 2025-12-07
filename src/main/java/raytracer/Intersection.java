package raytracer;

import raytracer.light.AbstractLight;
import raytracer.light.DirectionalLight;
import raytracer.light.PointLight;
import raytracer.math.Color;
import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.shape.Shape;

/**
 * Intersection record holding distance t, position, surface normal, and hit shape.
 * Provides Lambert and Blinn-Phong contributions for a given light.
 */
public final class Intersection {
    private final double t;
    private final Point position;
    private final Vector normal;
    private final Shape shape;

    /** Constructs an intersection record. */
    public Intersection(double t, Point position, Vector normal, Shape shape) {
        this.t = t;
        this.position = position;
        this.normal = normal;
        this.shape = shape;
    }

    /** @return ray parameter t at intersection */
    public double t() { return t; }
    /** @return hit position */
    public Point position() { return position; }
    /** @return surface normal (normalized) */
    public Vector normal() { return normal; }
    /** @return hit shape */
    public Shape shape() { return shape; }

    /**
     * Lambertian diffuse term: lightColor ⊙ diffuse * max(0, N·L).
     *
     * @param light light source
     * @return diffuse color contribution
     */
    public Color lambert(AbstractLight light) {
        Vector lightDir;

        if (light instanceof DirectionalLight dirLight) {
            lightDir = dirLight.direction().scale(-1.0).normalized();
        } else if (light instanceof PointLight pointLight) {
            lightDir = Vector.fromPoints(position, pointLight.origin()).normalized();
        } else {
            return new Color();
        }

        double cos = lightDir.dot(normal);
        if (cos <= 0.0) {
            return new Color();
        }

        Color lightColor = light.color();
        Color diffuse = shape.getDiffuse();

        return lightColor.schur(diffuse).scale(cos);
    }

    /**
     * Blinn-Phong specular term: lightColor ⊙ specular * (max(0, N·H) ^ shininess),
     * computed only if N·L > 0.
     *
     * @param light  light source
     * @param eyeDir direction from hit towards eye (normalized)
     * @return specular color contribution
     */
    public Color blinnPhong(AbstractLight light, Vector eyeDir) {
        Vector lightDir;

        if (light instanceof DirectionalLight dirLight) {
            lightDir = dirLight.direction().scale(-1.0).normalized();
        } else if (light instanceof PointLight pointLight) {
            lightDir = Vector.fromPoints(position, pointLight.origin()).normalized();
        } else {
            return new Color();
        }

        double cosNL = lightDir.dot(normal);
        if (cosNL <= 0.0) {
            return new Color();
        }

        Vector halfwayVector = lightDir.add(eyeDir).normalized();
        double cosNH = Math.max(0.0, halfwayVector.dot(normal));

        double shininess = shape.getShininess();
        if (shininess <= 0.0) {
            return new Color();
        }

        double specFactor = Math.pow(cosNH, shininess);

        Color lightColor = light.color();
        Color specColor = shape.getSpecular();

        return lightColor.schur(specColor).scale(specFactor);
    }
}
