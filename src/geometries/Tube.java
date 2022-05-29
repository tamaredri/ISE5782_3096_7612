package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * a class that represents a tube in the space by the axis of it's center. and it's radius
 */
public class Tube extends Geometry{
    protected Ray axisRay;
    protected double radius;

    //region constructor ray and a radius
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }
    //endregion

    //region toString Override
    /**
     * format: "Tube { axisRay=  Ray { p0 = Point : xyz = (x, y, z), dir = Vector : xyz = (x, y, z) }, radius= radius}"
     */
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
    //endregion

    //region getNormal function
    @Override
    public Vector getNormal(Point point) {
        // projection of p0p vector on the axisRay
        double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));

        Point projectionPointOfP = axisRay.getP0();
        if (t != 0) {
            projectionPointOfP = axisRay.getPoint(t);
        }

        return point.subtract(projectionPointOfP).normalize();
    }
    //endregion

    //region findGeoIntersectionsHelper
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point o = ray.getP0();
        Vector d = ray.getDir();

        // vectors that span a space with the axis ray
        Vector local_z = axisRay.getDir().normalize();
        Vector local_x;
        try{
            local_x = d.crossProduct(local_z).normalize();
        } catch (IllegalArgumentException x){ // ray.dir == axisRay.dir
            return null;
        }
        Vector local_y = local_z.crossProduct(local_x).normalize();

        Vector w = o.subtract(axisRay.getP0());

        // projection of o and d on all 3 spanning vectors
        Vector o_local = new Vector(w.dotProduct(local_x), w.dotProduct(local_y), w.dotProduct(local_z));
        Vector d_local = new Vector(d.dotProduct(local_x), d.dotProduct(local_y), d.dotProduct(local_z));

        // parameters for the root formula
        double a = d_local.getY() * d_local.getY();
        double b = 2 * d_local.getY() * o_local.getY();
        double c = o_local.getY() * o_local.getY() + o_local.getX() * o_local.getX() - radius * radius;


        if (a == 0) {   // ray is parallel to axis ray - check the bases!!
            return null;
        }

        // root parameters
        double e = b * b - 4 * a * c;

        // complex solution - what does it mean??
        if (e < 0) {
            return null;
        }
        e = Math.sqrt(e);

        List<GeoPoint> pointList = new ArrayList<>();
        double t1 = (-b - e) / (2 * a);
        double t2 = (-b + e) / (2 * a);

        if (alignZero(t1) > 0) {
            pointList.add(new GeoPoint(this , ray.getPoint(t1)));
        }
        if (alignZero(t2) > 0) {
            pointList.add(new GeoPoint(this , ray.getPoint(t2)));
        }

        if(pointList.size() == 0)
            return null;
        return pointList;
    }
    //endregion
}
