import raytracer.math.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColorTest {

    @Test
    void testDefaultBlackAndClamp() {
        Color black = new Color();
        assertEquals(new Color(0,0,0), black);

        // clamp > 1
        assertEquals(new Color(1,1,1), new Color(2.0, 3.0, 5.0));
        // clamp < 0
        assertEquals(new Color(0,0,0), new Color(-1.0, -0.2, -5.0));
    }

    @Test
    void testAddScaleSchurClamp() {
        Color a = new Color(0.2, 0.5, 0.8);
        Color b = new Color(0.9, 0.9, 0.9);

        // add -> clamp to 1
        assertEquals(new Color(1.0, 1.0, 1.0), a.add(b));

        // scale -> clamp
        assertEquals(new Color(0.4, 1.0, 1.0), a.scale(2.0));

        // schur within range
        assertEquals(new Color(0.18, 0.45, 0.72), a.schur(b));
    }

    @Test
    void testToRGB() {
        Color c = new Color(1.0, 0.5, 0.0); // orange-ish
        int rgb = c.toRGB();
        // 0xFF8000
        assertEquals(0xFF8000, rgb);
    }
}
