package raytracer.utils;

/**
 * Numeric utilities for tolerant floating-point comparisons and hashing.
 * Provides a global epsilon and helpers used by vector classes.
 */
public final class FloatEquals {
    private FloatEquals() {}

    /** Global relative/absolute tolerance used for comparisons. */
    public static final double EPS = 1e-9;

    /**
     * Returns true if a and b are nearly equal within EPS, using a mixed absolute/relative check.
     *
     * @param a first value
     * @param b second value
     * @return true if |a-b| < max(EPS, (|a|+|b|) * EPS)
     */
    public static boolean nearlyEqual(double a, double b) {
        final double diff = Math.abs(a - b);
        if (a == b) return true;
        final double norm = Math.min((Math.abs(a) + Math.abs(b)), Double.MAX_VALUE);
        return diff < Math.max(EPS, norm * EPS);
    }

    /**
     * Quantizes a double using EPS and returns a stable hash code for use in hashCode implementations.
     *
     * @param v value to quantize
     * @return hash of round(v / EPS)
     */
    public static int hashQuantized(double v) {
        long q = Math.round(v / EPS);
        return Long.hashCode(q);
    }
}
