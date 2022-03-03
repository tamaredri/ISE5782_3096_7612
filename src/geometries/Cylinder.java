package geometries;
import primitives.*;

import static primitives.Util.*;

/**
 * a class that represents a cylinder geometry
 */
public class Cylinder extends Tube{

    private double height;

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

    // region getNormal function
    @Override
    public Vector getNormal(Point point) {
        // the point is on the base circle of the cylinder. at the distance of maximum tha size of radius
        if (point.distance(axisRay.getP0()) <= radius)
            return axisRay.getDir().scale(-1); // the normal is on the opposite direction of dir

        // the point is on the other base circle of the cylinder. at the distance of maximum tha size of radius
        Point basePoint1 = axisRay.getP0().add(axisRay.getDir().scale(height));
        if (point.distance(basePoint1) <= radius)
            return axisRay.getDir(); // the normal is on the direction of dir

        // otherwise, go to super. the same as the end of the function...

        // otherwise, the point is on the casing of the cylinder. cll super to calculate the normal
        return super.getNormal(point);
    }
    //endregion

}
