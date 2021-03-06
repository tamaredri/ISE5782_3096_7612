package geometries;

import primitives.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;
import geometries.Intersectable.GeoPoint;

/**
 * a class that represents a plane in the space (by a point on the plane and the normal to the plane)
 */
public class Plane extends Geometry{
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

    //region constructor by a point and a normal
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

    //region findIntersections
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if(this.q0.equals(ray.getP0())) //ray starts at the reference point of the plane
            return null;

        Vector vecFromRayToNormal = this.q0.subtract(ray.getP0());
        double numerator = this.normal.dotProduct(vecFromRayToNormal);
        if(isZero(numerator)) // ray starts on the plane
            return null;

        double denominator = this.normal.dotProduct(ray.getDir());
        if(isZero(denominator)) // ray is parallel to the plane
            return null;

        double t = numerator / denominator;
        if(t < 0 || alignZero(t - maxDistance) > 0) // ray starts after the plane
            return null;

        List<GeoPoint> intersections = new LinkedList<>();
        intersections.add(new GeoPoint(this, ray.getPoint(t)));
        return intersections;
    }
    //endregion
}
