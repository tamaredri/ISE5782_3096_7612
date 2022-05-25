package geometries;

import primitives.*;
import java.util.List;

/**
 * represents an objects that can be intersected with a ray in space
 */
public abstract class Intersectable {

    //region GeoPoint
    /**
     * an internal class to represent a geometry and an intersection point on the geometry.
     * need this in order to find the color on the specific point on the geometry.
     */
    public static class GeoPoint{
        public final Geometry geometry;
        public final Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return this.point.equals(geoPoint.point) && this.geometry.equals(geoPoint.geometry);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometries=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
    //endregion

    //region findIntersections
    /**
     * calculating the intersection points between a ray and an entity that implements this interface
     * using the linear calculation methods for an intersection to an intersectable geometry
     * used only in tests
     * @param ray to calculate the intersections to
     * @return list of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ?
                null
                : geoList.stream().map(gp -> gp.point).toList();
    }
    //endregion

    //region findGeoIntersections
    /**
     * find intersections between a ray and all geometries
     * default use for infinite distance from the head of the ray
     * @param ray the ray to intersect with
     * @return a list of GeoPoints- every intersection point with the geometry it's on
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * find intersections between a ray and all geometries
     * @param ray the ray to intersect with
     * @param maxDistance the maximum distance needed between the head of the ray and the intersection points
     * @return a list of GeoPoints- every intersection point with the geometry it's on
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    //endregion

    //region findIntersectionsHelper
    /**
     * find intersections between a ray and a geometry
     * @param ray the ray to intersect with
     * @return a list with GeoPoints that represent the intersections between the ray and the geometry
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
    //endregion

}
