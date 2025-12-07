import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import raytracer.Ray;
import raytracer.math.Point;
import raytracer.math.Vector;
import raytracer.shape.Triangle;

public class TriangleTest {

    @Test
    void hitTriangleCenter() {
        Point a = new Point(-1, -1, -5);
        Point b = new Point(1, -1, -5);
        Point c = new Point(0, 1, -5);
        Triangle tri = new Triangle(a, b, c);

        Ray r = new Ray(new Point(0,0,0), new Vector(0,0,-1));
        var hit = tri.intersect(r);
        assertTrue(hit.isPresent());

        var isect = hit.get();
        assertEquals(new Point(0,0,-5), ((raytracer.Intersection)isect).position());
    }

    @Test
    void missTriangleOutside() {
        Point a = new Point(-1, -1, -5);
        Point b = new Point(1, -1, -5);
        Point c = new Point(0, 1, -5);
        Triangle tri = new Triangle(a, b, c);

        Ray r = new Ray(new Point(2,2,0), new Vector(0,0,-1));
        assertTrue(tri.intersect(r).isEmpty());
    }
}
