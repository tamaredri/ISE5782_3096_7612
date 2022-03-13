package geometries;
import primitives.*;

import java.util.List;

/**
 * a class that represents a sphere in the space by its center point and radius
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

    //region toString override
    /**
     * format: "Sphere{ center= Point { xyz = (x, y, z) }, radius= radius } "
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
    //endregion

    // region getNormal function
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
    //endregion

    //region
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
    //endregion
}
