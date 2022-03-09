package geometries;
import primitives.*;

import java.util.List;

/**
 * a class that represents a tube in the space by the axis of it's center. and it's radius
 */

public class Tube implements Geometry{
    protected Ray axisRay; //the center axis
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
        // distance between p0 of the tube to the projection of point on the tube's ray
        double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));

        // projection of point on the tube's ray
        Point o = axisRay.getP0();
        if (t != 0) {
            o = o.add(axisRay.getDir().scale(t));
        }

        // the normalized vector from o to point
        return point.subtract(o).normalize();
    }
    //endregion

    //region
    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
    //endregion
}
