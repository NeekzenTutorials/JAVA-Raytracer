package raytracer.math;

/**
 * RGB color stored as doubles in [0,1]. Immutable and clamped on construction.
 * Provides add, scale, Schur product, and conversion to 24-bit RGB int.
 */
public final class Color extends AbstractVec3 {

    /**
     * Constructs a black color (0,0,0).
     */
    public Color() { super(0.0, 0.0, 0.0); }

    /**
     * Constructs a color; each component is clamped to [0,1].
     *
     * @param r red
     * @param g green
     * @param b blue
     */
    public Color(double r, double g, double b) {
        super(clamp01(r), clamp01(g), clamp01(b));
    }

    /** @return red in [0,1] */
    public double r() { return x; }
    /** @return green in [0,1] */
    public double g() { return y; }
    /** @return blue in [0,1] */
    public double b() { return z; }

    /**
     * Clamps a value to [0,1].
     */
    private static double clamp01(double v) {
        if (v < 0.0) return 0.0;
        if (v > 1.0) return 1.0;
        return v;
    }

    /**
     * Internal factory using the public constructor (keeps clamping guarantees).
     */
    private static Color ofRaw(double r, double g, double b) {
        return new Color(r, g, b);
    }

    /**
     * Component-wise addition with clamping via constructor.
     *
     * @param c other color
     * @return this + c
     */
    public Color add(Color c) {
        return ofRaw(this.r() + c.r(), this.g() + c.g(), this.b() + c.b());
    }

    /**
     * Uniform scaling of all components (then clamped).
     *
     * @param d scalar
     * @return d * this
     */
    public Color scale(double d) {
        return ofRaw(this.r() * d, this.g() * d, this.b() * d);
    }

    /**
     * Component-wise (Schur/Hadamard) multiplication (then clamped).
     *
     * @param c other color
     * @return this ⊙ c
     */
    public Color schur(Color c) {
        return ofRaw(this.r() * c.r(), this.g() * c.g(), this.b() * c.b());
    }

    /**
     * Converts to 24-bit packed RGB int (0xRRGGBB), rounding each component to [0..255].
     *
     * @return packed RGB
     */
    public int toRGB() {
        int red = (int) Math.round(r() * 255);
        int green = (int) Math.round(g() * 255);
        int blue = (int) Math.round(b() * 255);
        return ((red & 0xff) << 16) + ((green & 0xff) << 8) + (blue & 0xff);
    }
}
