package raytracer.math;

public final class Color extends AbstractVec3 {

    public Color() { super(0.0, 0.0, 0.0); }

    public Color(double r, double g, double b) {
        super(clamp01(r), clamp01(g), clamp01(b));
    }

    public double r() { return x; }
    public double g() { return y; }
    public double b() { return z; }

    private static double clamp01(double v) {
        if (v < 0.0) return 0.0;
        if (v > 1.0) return 1.0;
        return v;
    }

    private static Color ofRaw(double r, double g, double b) {
        return new Color(r, g, b);
    }

    public Color add(Color c) {
        return ofRaw(this.r() + c.r(), this.g() + c.g(), this.b() + c.b());
    }

    public Color scale(double d) {
        return ofRaw(this.r() * d, this.g() * d, this.b() * d);
    }

    public Color schur(Color c) {
        return ofRaw(this.r() * c.r(), this.g() * c.g(), this.b() * c.b());
    }

    public int toRGB() {
        int red = (int) Math.round(r() * 255);
        int green = (int) Math.round(g() * 255);
        int blue = (int) Math.round(b() * 255);
        return ((red & 0xff) << 16) + ((green & 0xff) << 8) + (blue & 0xff);
    }
}
