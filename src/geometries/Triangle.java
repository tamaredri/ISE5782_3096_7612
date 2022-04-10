package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        // intersections on the plane
        List<Point> pointListFromPlane = this.plane.findIntersections(ray);

        if(pointListFromPlane == null) //the ray doesn't intersect the plane
            return null;

        Vector v1 = this.vertices.get(0).subtract(ray.getP0());
        Vector v2 = this.vertices.get(1).subtract(ray.getP0());
        Vector v3 = this.vertices.get(2).subtract(ray.getP0());

        try {
            Vector n1 = v1.crossProduct(v2).normalize();
            Vector n2 = v2.crossProduct(v3).normalize();
            Vector n3 = v3.crossProduct(v1).normalize();

            double d1 = alignZero(ray.getDir().dotProduct(n1));
            double d2 = alignZero(ray.getDir().dotProduct(n2));
            double d3 = alignZero(ray.getDir().dotProduct(n3));

            // if the d's have the same sign -> the intersection is inside the triangle
            if( (d1 > 0 && d2 > 0 && d3 > 0) || ( d1 < 0 && d2 < 0 && d3 < 0)) {
                List<GeoPoint> geoPointsTriangle = new ArrayList<GeoPoint>();
                for (Point point : pointListFromPlane) {
                    geoPointsTriangle.add(new GeoPoint(this, point));
                }
                return geoPointsTriangle;
            }
        }
        catch (Exception x)
        {
            // one of the cross products constructed the zero vector -> intersect the vertex or the edge of the triangle
        }
        return null;

    }
    //endregion
}
