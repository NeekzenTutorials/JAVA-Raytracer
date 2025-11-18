package raytracer.shape;

import java.util.Optional;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.math.Color;

public abstract class Shape {
    protected Color diffuse = new Color();
    protected Color specular = new Color();
    protected double shininess = 0.0;

    public Color getDiffuse() { return diffuse; }
    public void setDiffuse(Color diffuse) { this.diffuse = diffuse; }

    public Color getSpecular() { return specular; }
    public void setSpecular(Color specular) { this.specular = specular; }

    public double getShininess() { return shininess; }
    public void setShininess(double shininess) { this.shininess = shininess; }

    public abstract Optional<Intersection> intersect(Ray ray);
}
