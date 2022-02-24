package geometries.*;
package primitives.*;

public class Plane {

    private Point q0;
    private Vector normal;

    public Plane(Point p1, Point p2, Point p3) {
        Vector v1 = new vector (p1.subtract(p2));
        Vector v2 = new Vector (p2.subtract(p3));
        this.normal = v1.crossProduct(v2).normalize;
        this.q0 = p1;
    }

    public Plane(Point p, Vector normal) {
        this.q0 = p;
        this.normal = normal;
    }

    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }


}
