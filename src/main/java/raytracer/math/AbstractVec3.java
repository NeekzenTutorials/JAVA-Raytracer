package raytracer.math;

import raytracer.utils.FloatEquals;

public abstract class AbstractVec3 {
    protected final double x;
    protected final double y;
    protected final double z;

    protected AbstractVec3() {
        this(0.0, 0.0, 0.0);
    }

    protected AbstractVec3(double x, double y, double z) {
        this.x = x; this.y = y; this.z = z;
    }

    public final double x() { return x; }
    public final double y() { return y; }
    public final double z() { return z; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        AbstractVec3 other = (AbstractVec3) o;
        return FloatEquals.nearlyEqual(this.x, other.x)
            && FloatEquals.nearlyEqual(this.y, other.y)
            && FloatEquals.nearlyEqual(this.z, other.z);
    }

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

    @Override
    public String toString() {
        return "%s(%.9f, %.9f, %.9f)".formatted(
                this.getClass().getSimpleName(), x, y, z);
    }
}
