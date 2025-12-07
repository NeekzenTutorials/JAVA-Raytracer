package raytracer.math;

/**
 * Immutable 3D vector supporting common operations used by the raytracer:
 * add, subtract, scale, component-wise multiplication, dot, cross, length and normalization.
 * All methods return new Vector instances and do not mutate state.
 */
public final class Vector extends AbstractVec3 {

    /**
     * Constructs a zero vector (0, 0, 0).
     */
    public Vector() { super(); }

    /**
     * Constructs a vector with components (x, y, z).
     *
     * @param x X component
     * @param y Y component
     * @param z Z component
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Component-wise sum of this vector and v.
     *
     * @param v vector to add
     * @return this + v
     */
    public Vector add(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    /**
     * Component-wise difference of this vector and v.
     *
     * @param v vector to subtract
     * @return this - v
     */
    public Vector sub(Vector v) {
        return new Vector(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    /**
     * Scales this vector by the scalar d.
     *
     * @param d scalar multiplier
     * @return d * this
     */
    public Vector scale(double d) {
        return new Vector(d * this.x, d * this.y, d * this.z);
    }

    /**
     * Component-wise (Hadamard/Schur) multiplication.
     *
     * @param v vector to multiply
     * @return (this.x*v.x, this.y*v.y, this.z*v.z)
     */
    public Vector schur(Vector v) {
        return new Vector(this.x * v.x, this.y * v.y, this.z * v.z);
    }

    /**
     * Dot product between this vector and v.
     *
     * @param v other vector
     * @return this·v
     */
    public double dot(Vector v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    /**
     * Cross product (right-hand rule) between this vector and v.
     *
     * @param v other vector
     * @return this × v, orthogonal to both
     */
    public Vector cross(Vector v) {
        double cx = this.y * v.z - this.z * v.y;
        double cy = this.z * v.x - this.x * v.z;
        double cz = this.x * v.y - this.y * v.x;
        return new Vector(cx, cy, cz);
    }

    /**
     * Euclidean length (magnitude).
     *
     * @return sqrt(x^2 + y^2 + z^2)
     */
    public double length() {
        return Math.sqrt(this.dot(this));
    }

    /**
     * Unit-length vector in the same direction.
     * Returns the zero vector if length == 0.
     *
     * @return normalized vector or (0,0,0) if zero-length
     */
    public Vector normalized() {
        double len = length();
        if (len == 0.0) return new Vector(0, 0, 0);
        return this.scale(1.0 / len);
    }

    /**
     * Translates the given point by this vector (p + v).
     *
     * @param p point to translate
     * @return new point p + this
     */
    public Point addTo(Point p) {
        return new Point(p.x + this.x, p.y + this.y, p.z + this.z);
    }

    /**
     * Translates the given point by -this vector (p - v).
     *
     * @param p point to translate
     * @return new point p - this
     */
    public Point subtractFrom(Point p) {
        return new Point(p.x - this.x, p.y - this.y, p.z - this.z);
    }

    /**
     * Creates the vector from point a to point b: b - a.
     *
     * @param a origin point
     * @param b destination point
     * @return b - a
     */
    public static Vector fromPoints(Point a, Point b) {
        return new Vector(b.x - a.x, b.y - a.y, b.z - a.z);
    }
}
