import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import raytracer.Ray;
import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.shape.Sphere;

public class SphereTest {

    @Test
    void hitCenterOfSphere() {
        Sphere s = new Sphere(new Point(0,0,-5), 1.0);
        Ray r = new Ray(new Point(0,0,0), new Vector(0,0,-1));

        Optional<?> hit = s.intersect(r);
        assertTrue(hit.isPresent());

        var isect = hit.get();
        // t should be ~4.0 (from origin to surface at z=-4)
        double t = ((raytracer.Intersection)isect).t();
        assertEquals(4.0, t, 1e-6);

        Point pos = ((raytracer.Intersection)isect).position();
        assertEquals(new Point(0,0,-4), pos);

        Vector normal = ((raytracer.Intersection)isect).normal();
        assertEquals(new Vector(0,0,1), normal);
    }

    @Test
    void missSphere() {
        Sphere s = new Sphere(new Point(0,0,-5), 1.0);
        Ray r = new Ray(new Point(0,0,0), new Vector(0,1,0));

        assertTrue(s.intersect(r).isEmpty());
    }
}
