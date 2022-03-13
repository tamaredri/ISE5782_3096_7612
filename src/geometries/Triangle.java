package geometries;

import primitives.*;
import java.util.List;

public class Triangle extends Polygon{

    //region constructor
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
    //endregion

    //region to string Override
    /**
     * format: "Triangle {vertices= {}, plane= }"
     */
    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
    //endregion

    //region
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
    //endregion
}
