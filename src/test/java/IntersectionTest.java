import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import raytracer.Intersection;
import raytracer.light.PointLight;
import raytracer.math.Color;
import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.shape.Sphere;

public class IntersectionTest {

    @Test
    void lambertWithPointLight() {
        Sphere s = new Sphere(new Point(0,0,-5), 1.0);
        s.setDiffuse(new Color(0.5, 0.0, 0.0));

        Point hitPos = new Point(0,0,-4);
        Vector normal = new Vector(0,0,1);

        Intersection isect = new Intersection(1.0, hitPos, normal, s);

        PointLight light = new PointLight(new Point(0,0,0), new Color(1,1,1));

        Color diffuse = isect.lambert(light);
        assertEquals(new Color(0.5, 0.0, 0.0), diffuse);
    }

    @Test
    void blinnPhongSimpleSpecular() {
        Sphere s = new Sphere(new Point(0,0,-5), 1.0);
        s.setSpecular(new Color(1.0, 1.0, 1.0));
        s.setShininess(10.0);

        Point hitPos = new Point(0,0,-4);
        Vector normal = new Vector(0,0,1);

        Intersection isect = new Intersection(1.0, hitPos, normal, s);

        PointLight light = new PointLight(new Point(0,0,0), new Color(1,1,1));
        Vector eyeDir = new Vector(0,0,1);

        Color spec = isect.blinnPhong(light, eyeDir);
        // With aligned vectors and normalized halfway vector, expect specular to be light*specular
        assertEquals(new Color(1.0,1.0,1.0), spec);
    }
}
