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

    //region
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = new ArrayList<>();
        Vector u;

        try {
            u = this.center.subtract(ray.getP0()); // vector from p0 to the center of the sphere
        }
        catch (IllegalArgumentException e){ // start of ray is on the center of the sphere.
            // the vector u will be the zero vector, so there will be an exception
            intersections.add(new GeoPoint(this,ray.getP0().add(ray.getDir().scale(radius))));
            return intersections;
        }

        double tm = u.dotProduct(ray.getDir());       // projection on the ray
        double d = Math.sqrt(u.lengthSquared() - tm * tm); //distance from ray to the center of the sphere
        if (alignZero(d - this.radius) >= 0)        //distance is bigger or equals the radius,
            // then there are no intersections
            return null;

        double th = alignZero(Math.sqrt(this.radius * this.radius - d * d));
        // t1, t2 the distances to extend dir to get to the intersections
        double t1 = tm + th;
        double t2 = tm - th;

        if (alignZero(t1) <= 0 && alignZero(t2) <= 0)
            return null; // no intersection point is excepted

        if(t1 > 0)
            intersections.add(new GeoPoint(this,ray.getPoint(t1)));
        if(t2 > 0)
            intersections.add(new GeoPoint(this,ray.getPoint(t2)));

        return intersections;    }
    //endregion
}
