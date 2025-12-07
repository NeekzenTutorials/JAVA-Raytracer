import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import raytracer.Ray;
import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.shape.Plane;

public class PlaneTest {

    @Test
    void intersectPlaneFacingCamera() {
        Plane p = new Plane(new Point(0,0,-5), new Vector(0,0,1));
        Ray r = new Ray(new Point(0,0,0), new Vector(0,0,-1));

        var hit = p.intersect(r);
        assertTrue(hit.isPresent());

        var isect = hit.get();
        assertEquals(5.0, ((raytracer.Intersection)isect).t(), 1e-6);
        assertEquals(new Point(0,0,-5), ((raytracer.Intersection)isect).position());
        assertEquals(new Vector(0,0,1), ((raytracer.Intersection)isect).normal());
    }

    @Test
    void parallelPlaneNoHit() {
        Plane p = new Plane(new Point(0,0,-5), new Vector(0,0,1));
        Ray r = new Ray(new Point(0,0,0), new Vector(1,0,0));

        assertTrue(p.intersect(r).isEmpty());
    }
}
