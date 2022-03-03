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

        //maybe it is possible to use the distance method and the distance should be equal to the radius???

        // region using distance calculation
        // the point is on the base circle of the cylinder. at the distance of maximum tha size of radius
        if (point.distance(axisRay.getP0()) <= radius)
            return axisRay.getDir().scale(-1); // the normal is on the opposite direction of dir

        // the point is on the other base circle of the cylinder. at the distance of maximum tha size of radius
        Point basePoint1 = axisRay.getP0().add(axisRay.getDir().scale(height));
        if (point.distance(basePoint1) <= radius)
            return axisRay.getDir(); // the normal is on the direction of dir

        // otherwise, go to super. the same as the end of the function...
        //endregion

        //region using vector calculation

        // vector between point and p0
        Vector help = point.subtract(axisRay.getP0());

        // help is orthogonal to the axis ray. then point is on the first base
        if (isZero(help.dotProduct(axisRay.getDir())))
            return axisRay.getDir().scale(-1); // the normal is on the opposite direction of dir

        // projection of p0 on the second base of the cylinder
        Point basePoint = axisRay.getP0().add(axisRay.getDir().scale(height));
        // vector between point and the projection of p0
        help = point.subtract(basePoint);

        // help is orthogonal to the axis ray. then point is on the second base
        if (isZero(help.dotProduct(axisRay.getDir())))
            return axisRay.getDir(); // the normal is on the direction of dir
        //endregion

        // otherwise, the point is on the casing of the cylinder. cll super to calculate the normal
        return super.getNormal(point);
    }
    //endregion

}
