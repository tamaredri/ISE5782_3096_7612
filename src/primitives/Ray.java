package primitives;

import geometries.Intersectable;

import java.util.List;
import geometries.Intersectable.GeoPoint;
import static primitives.Util.*;

/**
 * a class that represents an infinite Ray in space
 * using a Point for the location in space and a direction Vector
 */
public class Ray {
    private static final double DELTA = 0.1;

    private final Point p0;
    private final Vector dir;

    //region constructors
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Ray(Point head, Vector direction, Vector normal){
        this.p0 = head.add(normal.scale(normal.dotProduct(direction) > 0 ? DELTA : -DELTA));
        this.dir = direction.normalize();
    }
    //endregion

    // region getters
    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    /**
     * returns the point on the ray that is in a given distance from the head of the ray
     * @param t the distance from the head of the ray to point
     * @return the point that is in the given distance from the head of the ray
     */
    public Point getPoint(double t){
        try {
            return p0.add(dir.scale(t));
        }
        catch(Exception e){
            return p0;
        }
    }
    //endregion

    //region findClosestPoint
    /**
     *  find the point that is closest to the head of the ray
     * @param points list of points
     * @return the closest point to the head of the ray out of the points list
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }
    //endregion

    //region findClosestGeoPoint
    /**
     * find the closest GeoPoint to the head of the ray
     * @param points a list of GeoPoints
     * @return the closest GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null)
            return null;

        GeoPoint closestPoint = points.get(0);
        double distance = closestPoint.point.distanceSquared(this.p0);

        for (GeoPoint geoPoint : points) {
            double d = geoPoint.point.distanceSquared(this.p0);
            if(distance > d)    // if there is a closer point then 'point', replace the values
            {
                closestPoint = geoPoint;
                distance = d;
            }
        }
        return closestPoint;
    }
    //endregion

    //region to string override
    /**
     * format: " Ray { p0 = Point : xyz = {x, y, z)}, dir = Vector : xyz = (x, y, z) } "
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
    //endregion

    //region equals function override
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir) ;
    }
    //endregion
}
