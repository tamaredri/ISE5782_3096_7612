package geometries;
import primitives.*;

/**
 * a class that represents a tube in the space by the axis of it's center and it's radius
 */

public class Tube implements Geometry{
    protected Ray axisRay; //the center axis
    protected double radius;

    //region constructor
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }
    //endregion

    //region getters
    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }
    //endregion

    //region to string Override
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay.toString() +
                ", radius=" + radius +
                '}';
    }
    //endregion

    //region get normal function
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
    //endregion
}
