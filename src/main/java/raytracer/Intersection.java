package raytracer;

import raytracer.light.AbstractLight;
import raytracer.light.DirectionalLight;
import raytracer.light.PointLight;
import raytracer.math.Color;
import raytracer.math.Point;
import raytracer.shape.Shape;
import raytracer.math.Vector;

public final class Intersection {
    private final double t;
    private final Point position;
    private final Vector normal;
    private final Shape shape;

    public Intersection(double t, Point position, Vector normal, Shape shape) {
        this.t = t;
        this.position = position;
        this.normal = normal;
        this.shape = shape;
    }

    public double t() { return t; }
    public Point position() { return position; }
    public Vector normal() { return normal; }
    public Shape shape() { return shape; }

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

        Vector h = lightDir.add(eyeDir).normalized();
        double cosNH = Math.max(0.0, h.dot(normal));

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
