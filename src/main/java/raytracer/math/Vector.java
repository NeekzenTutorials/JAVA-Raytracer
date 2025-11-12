package raytracer.math;

public final class Vector extends AbstractVec3 {

    public Vector() { super(); }

    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector add(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public Vector sub(Vector v) {
        return new Vector(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public Vector scale(double d) {
        return new Vector(d * this.x, d * this.y, d * this.z);
    }

    public Vector schur(Vector v) {
        return new Vector(this.x * v.x, this.y * v.y, this.z * v.z);
    }

    public double dot(Vector v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    public Vector cross(Vector v) {
        double cx = this.y * v.z - this.z * v.y;
        double cy = this.z * v.x - this.x * v.z;
        double cz = this.x * v.y - this.y * v.x;
        return new Vector(cx, cy, cz);
    }

    public double length() {
        return Math.sqrt(this.dot(this));
    }

    public Vector normalized() {
        double len = length();
        if (len == 0.0) return new Vector(0, 0, 0);
        return this.scale(1.0 / len);
    }

    public Point addTo(Point p) {
        return new Point(p.x + this.x, p.y + this.y, p.z + this.z);
    }

    public Point subtractFrom(Point p) {
        return new Point(p.x - this.x, p.y - this.y, p.z - this.z);
    }

    public static Vector fromPoints(Point a, Point b) {
        return new Vector(b.x - a.x, b.y - a.y, b.z - a.z);
    }
}
