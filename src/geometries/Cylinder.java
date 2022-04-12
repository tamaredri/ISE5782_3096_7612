package geometries;

import primitives.*;
import java.util.List;

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

    //region
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
    //endregion
}
