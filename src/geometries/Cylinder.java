package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

public class Cylinder extends Tube{

    private final double height;

    //region constructor
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }
    //endregion

    //region toString override
    /**
     * format: "Cylinder{ axisRay= Ray { p0 = Point : xyz = {(x, y, z)}, dir = Vector : xyz = (x, y, z) }, radius= radius, height= height } "
     */
    @Override
    public String toString() {
        return "" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                ", height=" + height +
                '}';
    }
    //endregion

    // region getNormal
    @Override
    public Vector getNormal(Point point) {
        Point axisRayP0 = axisRay.getP0();
        if (point.distance(axisRayP0) <= radius)    // on the base circle of the cylinder.
            return axisRay.getDir().scale(-1);

        Vector heightVector = axisRay.getDir().scale(height);
        axisRayP0 = axisRayP0.add(heightVector);

        if (point.distance(axisRayP0) <= radius)    // on the second base circle of the cylinder.
            return axisRay.getDir();

        return super.getNormal(point);              // on the casing of the cylinder.
    }
    //endregion

    //region findGeoIntersectionsHelper
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double masDistance) {
        Point o = ray.getP0();
        Vector d = ray.getDir();
        Vector local_z = axisRay.getDir().normalize();
        Vector local_x = d.crossProduct(local_z).normalize();
        Vector local_y = local_z.crossProduct(local_x).normalize();
        Vector w = o.subtract(axisRay.getP0());
        Vector o_local = new Vector(w.dotProduct(local_x), w.dotProduct(local_y), w.dotProduct(local_z));
        Vector d_local = new Vector(d.dotProduct(local_x), d.dotProduct(local_y), d.dotProduct(local_z));
        double a = d_local.getY() * d_local.getY();
        double b = 2 * d_local.getY() * o_local.getY();
        double c = o_local.getY() * o_local.getY() + o_local.getX() * o_local.getX() - radius * radius;
        if (a == 0) {
            return null;
        }
        double e = b * b - 4 * a * c;
        if (e < 0) {
            return null;
        }

        e = Math.sqrt(e);

        List<GeoPoint> pointList = new ArrayList<>();

        double t1 = (-b - e) / (2 * a);
        double t2 = (-b + e) / (2 * a);
        Point p2 = axisRay.getP0().add(axisRay.getDir().scale(height));
        if (alignZero(t1) > 0d) {
            Point i = ray.getPoint(t1);
            double f = i.subtract(axisRay.getP0()).dotProduct(local_z);
            if (f > 0 && f < p2.subtract(axisRay.getP0()).length()) {
                pointList.add(new GeoPoint(this , i));
            }
        }
        if (alignZero(t2) > 0d) {
            Point i = ray.getPoint(t2);
            double f = i.subtract(axisRay.getP0()).dotProduct(local_z);
            if (f > 0 && f < p2.subtract(axisRay.getP0()).length()) {
                pointList.add(new GeoPoint(this , i));
            }
        }
        if(pointList.size() == 0)
            return null;
        return pointList;
    }
    //endregion
}
