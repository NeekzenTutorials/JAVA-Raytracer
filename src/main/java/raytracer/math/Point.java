package raytracer.math;

/**
 * 3D point (position). Immutable. Supports translation by vectors and
 * difference between points as a vector.
 */
public final class Point extends AbstractVec3 {

    /**
     * Constructs the origin point (0,0,0).
     */
    public Point() { super(); }

    /**
     * Constructs a point with coordinates (x, y, z).
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     */
    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Translates this point by the given vector.
     *
     * @param v displacement
     * @return this + v
     */
    public Point add(Vector v) {
        return new Point(this.x + v.x(), this.y + v.y(), this.z + v.z());
    }

    /**
     * Translates this point by the opposite of the given vector.
     *
     * @param v displacement
     * @return this - v
     */
    public Point sub(Vector v) {
        return new Point(this.x - v.x(), this.y - v.y(), this.z - v.z());
    }

    /**
     * Returns the vector from point p to this point (this - p).
     *
     * @param p other point
     * @return vector this - p
     */
    public Vector sub(Point p) {
        return new Vector(this.x - p.x, this.y - p.y, this.z - p.z);
    }
}
