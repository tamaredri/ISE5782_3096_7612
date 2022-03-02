package geometries;
import primitives.*;

/**
 * a class that represents a plane in the space (by a point on the plane and the normal to the plane)
 */

public class Plane {

    private Point q0;
    private Vector normal;

    //region constructor by 3 points
    public Plane(Point p1, Point p2, Point p3) {
        Vector v1 = p1.subtract(p2);
        Vector v2 = p2.subtract(p3);
        this.normal = (v1.crossProduct(v2)).normalize();
        this.q0 = p1;
    }
    //endregion

    //region constructor by point and normal
    public Plane(Point p, Vector normal) {
        this.q0 = p;
        this.normal = normal;
    }
    //endregion

    public Vector getNormal() {
        return normal;
    }


}
