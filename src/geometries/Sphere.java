package geometries;
import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * a class that represents a sphere in the space by its center point and radius
 */
public class Sphere implements Geometry{
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
    public List<Point> findIntersections(Ray ray) {
        if( this.center.equals(ray.getP0()))
        {

        }

        Vector u = this.center.subtract(ray.getP0());
        double tm = u.dotProduct(ray.getDir());
        double d = Math.sqrt(u.lengthSquared() - tm * tm);
        if(isZero(d - this.radius))
            return null;

        double th = Math.sqrt(this.radius * this.radius - d * d);
        double t1 = tm + th;
        double t2 = tm - th;

        if( t1 > 0 || t2 > 0 )
        {
            List<Point> intersections = new ArrayList<>();
            if(t1 > 0)
                intersections.add(ray.getP0().add(ray.getDir().scale(t1)));
            if(t2 > 0)
                intersections.add(ray.getP0().add(ray.getDir().scale(t2)));
            return intersections;
        }

        return null;
    }
    //endregion
}
