package geometries;
import primitives.*;

/**
 * a class that represents a sphere in the space by it's center point and radius
 */
public class Sphere implements Geometry{
    private Point center;
    private double radius;

    //region constructor
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }
    //endregion

    //region getters
    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
    //endregion

    //region toString override
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
    //endregion

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
