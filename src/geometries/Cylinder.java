package geometries;
import primitives.*;

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

    public double getHeight() {
        return height;
    }

    //region toString
    @Override
    public String toString() {
        return "Cylinder{" +
                "axisRay=" + axisRay.toString() +
                ", radius=" + radius +
                ", height=" + height +
                '}';
    }
    //endregion

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
