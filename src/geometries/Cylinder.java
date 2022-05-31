package geometries;

import primitives.*;
import java.util.ArrayList;
import java.util.List;
import static primitives.Util.*;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> helpIntersections = super.findGeoIntersectionsHelper(ray, maxDistance);

        List<GeoPoint> pointList = new ArrayList<>();

        if(helpIntersections != null) {
            for (GeoPoint geoPoint : helpIntersections) {
                Point point = geoPoint.point;
                double projection = point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir());
                if (alignZero(projection) > 0 && alignZero(projection - this.height) < 0)
                    pointList.add(new GeoPoint(this, point));
            }
        }

        // intersect with base
        Circle base = new Circle(axisRay.getP0(), radius, axisRay.getDir());
        helpIntersections = base.findGeoIntersectionsHelper(ray, maxDistance);
        if(helpIntersections != null)
            pointList.add(new GeoPoint(this, helpIntersections.get(0).point));

        base = new Circle(axisRay.getPoint(height), radius, axisRay.getDir());
        helpIntersections = base.findGeoIntersectionsHelper(ray, maxDistance);
        if(helpIntersections != null)
            pointList.add(new GeoPoint(this, helpIntersections.get(0).point));

        if (pointList.size() == 0)
            return null;
        return pointList;
    }
    //endregion

}
