import raytracer.math.Vector;
import raytracer.math.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {

    @Test
    void testAddSubScale() {
        Vector a = new Vector(1,2,3);
        Vector b = new Vector(-2,0,5);
        assertEquals(new Vector(-1,2,8), a.add(b));
        assertEquals(new Vector(3,2,-2), a.sub(b));
        assertEquals(new Vector(2,4,6), a.scale(2));
    }

    @Test
    void testDotCrossLengthNormalize() {
        Vector a = new Vector(1,2,3);
        Vector b = new Vector(4,5,6);
        assertEquals(1*4 + 2*5 + 3*6, a.dot(b), 1e-9);

        Vector cx = a.cross(b);
        assertEquals(new Vector(-3,6,-3), cx);
        // non commutatif
        assertEquals(cx.scale(-1), b.cross(a));

        assertEquals(Math.sqrt(14), a.length(), 1e-12);
        assertEquals(1.0, a.normalized().length(), 1e-12);
    }

    @Test
    void testPointVectorOps() {
        Point p = new Point(1, 1, 1);
        Vector v = new Vector(2, -1, 0.5);
        assertEquals(new Point(3, 0, 1.5), p.add(v));
        assertEquals(new Point(-1, 2, 0.5), p.sub(v));
        assertEquals(v, new Point(3,0,1.5).sub(p));
        assertEquals(v.addTo(p), p.add(v));
        assertEquals(v.subtractFrom(p), p.sub(v));
    }
}
