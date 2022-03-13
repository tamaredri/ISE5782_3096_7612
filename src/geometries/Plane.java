package geometries;

import primitives.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;

/**
 * a class that represents a plane in the space (by a point on the plane and the normal to the plane)
 */
public class Plane implements Geometry{
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

    //region getNormal functions
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
    //endregion

    // region toString override
    /**
     * format: "Plane { q0= Point { xyz = (x, y, z) }, normal= Vector { xyz = (x, y, z) } }"
     */
    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }
    //endregion

    //region findIntersections function
    @Override
    public List<Point> findIntersections(Ray ray) {
        if(this.q0.equals(ray.getP0())) //ray starts at the reference point of the plane
            return null;

        Vector p0p1 = this.q0.subtract(ray.getP0());
        double numerator = this.normal.dotProduct(p0p1);
        if(isZero(numerator)) // if ray starts on the plane -> dot product returns 0
            return null;

        double denominator = this.normal.dotProduct(ray.getDir());
        if(isZero(denominator)) // if ray parallel to the plane -> dot product returns 0
            return null;

        double t = numerator / denominator;
        if(t < 0) // if starts after the plane -> move the point in the opposite direction of dir vector
            return null;

        List<Point> intersections = new LinkedList<>();
        intersections.add(ray.getP0().add(ray.getDir().scale(t)));
        return intersections;
    }
    //endregion
}
