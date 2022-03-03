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

    //region toString
    @Override
    public String toString() {
        return "Cylinder{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                ", height=" + height +
                '}';
    }
    //endregion

    @Override
    public Vector getNormal(Point point) {
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

        // otherwise the point is on the casing of the cylinder. cll super to calculate the normal
        return super.getNormal(point);
    }
}
