package geometries;

import primitives.*;
import java.util.List;

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
        return null;
    }
    //endregion
}
