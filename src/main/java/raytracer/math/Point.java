package raytracer.math;

public final class Point extends AbstractVec3 {

    public Point() { super(); }

    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    public Point add(Vector v) {
        return new Point(this.x + v.x(), this.y + v.y(), this.z + v.z());
    }

    public Point sub(Vector v) {
        return new Point(this.x - v.x(), this.y - v.y(), this.z - v.z());
    }

    public Vector sub(Point p) {
        return new Vector(this.x - p.x, this.y - p.y, this.z - p.z);
    }
}
