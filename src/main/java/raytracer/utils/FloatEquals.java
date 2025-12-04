package raytracer.utils;

public final class FloatEquals {
    private FloatEquals() {}

    public static final double EPS = 1e-9;

    public static boolean nearlyEqual(double a, double b) {
        final double diff = Math.abs(a - b);
        if (a == b) return true;
        final double norm = Math.min((Math.abs(a) + Math.abs(b)), Double.MAX_VALUE);
        return diff < Math.max(EPS, norm * EPS);
    }

    public static int hashQuantized(double v) {
        long q = Math.round(v / EPS);
        return Long.hashCode(q);
    }
}
