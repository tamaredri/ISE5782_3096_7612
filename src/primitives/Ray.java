package primitives;

import java.util.List;

/**
 * a class that represents an infinite Ray in space
 * using a Point for the location in space and a direction Vector
 */
public class Ray {
    private final Point p0;
    private final Vector dir;

    //region constructor
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }
    //endregion

    // region getters
    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    public Point getPoint(double t){
        return p0.add(dir.scale(t));
    }
    //endregion

    //region findClosestPoint
    /** Try to think of a better solution
     *  find the point that is the closet one to the head of the ray
     *
     * @param points
     * @return the closest point to the head of the ray
     */
    public Point findClosestPoint(List<Point> points){
        if (points.isEmpty()) // no close point is available
            return null;

        Point closestPoint = points.get(0);                         // save the first point
        double distance = closestPoint.distanceSquared(this.p0);    // the distance between the first point and the start of the ray
        for (Point point : points) {
            double d = point.distanceSquared(this.p0);
            if(distance > d)                                        // if there is a closer point then 'point', replace the values
            {
                closestPoint = point;
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
