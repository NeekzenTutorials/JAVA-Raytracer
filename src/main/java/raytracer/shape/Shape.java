package raytracer.shape;

import java.util.Optional;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.math.Color;

/**
 * Base class for renderable shapes.
 * Holds material properties (diffuse, specular, shininess) and defines the intersection contract.
 */
public abstract class Shape {
    protected Color diffuse = new Color();
    protected Color specular = new Color();
    protected double shininess = 0.0;

    /** @return diffuse color component */
    public Color getDiffuse() { return diffuse; }
    /** Sets diffuse color component. */
    public void setDiffuse(Color diffuse) { this.diffuse = diffuse; }

    /** @return specular color component */
    public Color getSpecular() { return specular; }
    /** Sets specular color component. */
    public void setSpecular(Color specular) { this.specular = specular; }

    /** @return specular exponent (shininess) */
    public double getShininess() { return shininess; }
    /** Sets specular exponent (shininess). */
    public void setShininess(double shininess) { this.shininess = shininess; }

    /**
     * Computes intersection of this shape with the given ray.
     *
     * @param ray input ray in world space
     * @return Optional containing the closest valid intersection, or empty if no hit
     */
    public abstract Optional<Intersection> intersect(Ray ray);
}
