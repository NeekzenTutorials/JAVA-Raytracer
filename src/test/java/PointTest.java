import raytracer.math.Point;
import raytracer.math.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    @Test
    void testSubPointsGivesVector() {
        Point a = new Point(1,2,3);
        Point b = new Point(-1, 2, 7);
        Vector v = b.sub(a);
        assertEquals(new Vector(-2,0,4), v);
    }

    @Test
    void testAddSubVector() {
        Point p = new Point(0,0,0);
        Vector v = new Vector(1,2,3);
        assertEquals(new Point(1,2,3), p.add(v));
        assertEquals(new Point(-1,-2,-3), p.sub(v));
    }
}