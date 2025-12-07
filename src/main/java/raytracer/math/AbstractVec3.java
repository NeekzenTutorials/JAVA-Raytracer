package raytracer.math;

import raytracer.utils.FloatEquals;

/**
 * Immutable base for 3D value types (Vector, Point, Color).
 * Provides components accessors, near-equality and hash based on quantized floats.
 */
public abstract class AbstractVec3 {
    protected final double x;
    protected final double y;
    protected final double z;

    /**
     * Constructs a zero vector-like value (0,0,0).
     */
    protected AbstractVec3() {
        this(0.0, 0.0, 0.0);
    }

    /**
     * Constructs a value with components (x, y, z).
     *
     * @param x X component
     * @param y Y component
     * @param z Z component
     */
    protected AbstractVec3(double x, double y, double z) {
        this.x = x; this.y = y; this.z = z;
    }

    /**
     * @return X component
     */
    public final double x() { return x; }

    /**
     * @return Y component
     */
    public final double y() { return y; }

    /**
     * @return Z component
     */
    public final double z() { return z; }

    /**
     * Near-equality based on FloatEquals.nearlyEqual for each component,
     * only between same concrete types.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        AbstractVec3 other = (AbstractVec3) o;
        return FloatEquals.nearlyEqual(this.x, other.x)
            && FloatEquals.nearlyEqual(this.y, other.y)
            && FloatEquals.nearlyEqual(this.z, other.z);
    }

    /**
     * Hash code consistent with near-equality using quantized components.
     */
    @Override
    public int hashCode() {
        int hx = FloatEquals.hashQuantized(x);
        int hy = FloatEquals.hashQuantized(y);
        int hz = FloatEquals.hashQuantized(z);
        int h = 17;
        h = 31*h + hx;
        h = 31*h + hy;
        h = 31*h + hz;
        return h;
    }

    /**
     * Human-readable representation: ClassName(x, y, z) with 9 decimals.
     */
    @Override
    public String toString() {
        return "%s(%.9f, %.9f, %.9f)".formatted(
                this.getClass().getSimpleName(), x, y, z);
    }
}
