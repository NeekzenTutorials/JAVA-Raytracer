package raytracer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import raytracer.math.Color;
import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.light.AbstractLight;
import raytracer.light.DirectionalLight;
import raytracer.light.PointLight;
import raytracer.shape.Shape;

public final class Scene {
    private int width;
    private int height;
    private Camera camera;
    private String output = "output.png";
    private Color ambient = new Color();
    private final List<AbstractLight> lights = new ArrayList<>();
    private final List<Shape> shapes = new ArrayList<>();

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public Camera getCamera() { return camera; }
    public void setCamera(Camera camera) { this.camera = camera; }

    public String getOutput() { return output; }
    public void setOutput(String output) { this.output = output; }

    public Color getAmbient() { return ambient; }
    public void setAmbient(Color ambient) { this.ambient = ambient; }

    public List<AbstractLight> getLights() { return lights; }
    public List<Shape> getShapes() { return shapes; }

    public void addLight(AbstractLight l) { lights.add(l); }
    public void addShape(Shape s) { shapes.add(s); }

    public Optional<Intersection> findClosestIntersection(Ray ray) {
        Intersection closest = null;

        for (Shape shape : shapes) {
            var hitOpt = shape.intersect(ray);
            if (hitOpt.isPresent()) {
                Intersection hit = hitOpt.get();
                if (closest == null || hit.t() < closest.t()) {
                    closest = hit;
                }
            }
        }
        return Optional.ofNullable(closest);
    }

    public Color shade(Intersection isect, Ray viewRay) {
        Color result = ambient;
        Vector eyeDir = viewRay.direction().scale(-1.0).normalized();

        for (AbstractLight light : lights) {
            if (isInShadow(isect, light)) {
                continue;
            }

            Color diffuseTerm = isect.lambert(light);
            Color specularTerm = isect.blinnPhong(light, eyeDir);

            result = result.add(diffuseTerm).add(specularTerm);
        }

        return result;
    }

    private boolean isInShadow(Intersection isect, AbstractLight light) {
        Vector lightDir;
        double maxT = Double.POSITIVE_INFINITY;

        if (light instanceof DirectionalLight dirLight) {
            lightDir = dirLight.direction().scale(-1.0).normalized();
        } else if (light instanceof PointLight pointLight) {
            Vector toLight = Vector.fromPoints(isect.position(), pointLight.origin());
            maxT = toLight.length();
            lightDir = toLight.normalized();
        } else {
            return false;
        }

        double eps = 1e-4;
        Point origin = isect.position().add(isect.normal().scale(eps));

        Ray shadowRay = new Ray(origin, lightDir);

        for (Shape s : shapes) {
            Optional<Intersection> hitOpt = s.intersect(shadowRay);
            if (hitOpt.isPresent()) {
                double t = hitOpt.get().t();
                if (t > eps && t < maxT - eps) {
                    return true;
                }
            }
        }

        return false;
    }
}
