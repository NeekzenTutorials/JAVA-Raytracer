package raytracer.utils;

public final class FloatEquals {
    private FloatEquals() {}

    /** Tolérance par défaut pour les doubles (adaptable selon l’échelle de ton projet). */
    public static final double EPS = 1e-9;

    public static boolean nearlyEqual(double a, double b) {
        final double diff = Math.abs(a - b);
        if (a == b) return true; // couvre +Inf, -Inf, NaN==NaN reste false (souhaité)
        final double norm = Math.min((Math.abs(a) + Math.abs(b)), Double.MAX_VALUE);
        return diff < Math.max(EPS, norm * EPS);
    }

    /** Hash stable : on “quantize” avec EPS pour rester cohérent avec equals. */
    public static int hashQuantized(double v) {
        long q = Math.round(v / EPS);
        return Long.hashCode(q);
    }
}
