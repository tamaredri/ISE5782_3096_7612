package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * a class that represents a sphere in the space by its center point and radius
 */
public class Sphere extends Geometry{
    private Point center;
    private double radius;

    //region constructor
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }
    //endregion

    //region toString override
    /**
     * format: "Sphere{ center= Point { xyz = (x, y, z) }, radius= radius } "
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
    //endregion

    // region getNormal function
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
    //endregion

    //region findGeoIntersectionsHelper
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = new ArrayList<>();

        if(this.center.equals(ray.getP0())){
            intersections.add(new GeoPoint(this, ray.getPoint(radius)));
            return intersections;
        }

        Vector u = this.center.subtract(ray.getP0());       // vector from ray to the sphere's center

        double tm = u.dotProduct(ray.getDir());             // projection of u on ray
        double dSquared =u.lengthSquared() - tm * tm;  // Pythagoras - distance from sphere's center to the ray

        if (alignZero(dSquared - this.radius * this.radius) >= 0)         // ray crosses outside the sphere
            return null;

        double th = alignZero(Math.sqrt(this.radius * this.radius - dSquared));
        // t1, t2 are the units to extend dir vec inorder to get the intersections
        double t1 = tm + th;
        double t2 = tm - th;

        if (alignZero(t1) <= 0 && alignZero(t2) <= 0)       // intersects on the opposite direction of ray
            return null;

        if(alignZero(t1) > 0)
            intersections.add(new GeoPoint(this, ray.getPoint(t1)));
        if(alignZero(t2) > 0)
            intersections.add(new GeoPoint(this, ray.getPoint(t2)));

        return intersections;
    }
    //endregion
}
