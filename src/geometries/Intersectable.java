package geometries;

import primitives.*;
import java.util.List;

/**
 * represents an objects that can be intersected with a ray in space
 */
public abstract class Intersectable {

    //region GeoPoint
    /**
     * an internal class to represent a geometry and the intersection point on this geometry.
     * in purpose of finding the color on the specific point of intersection.
     */
    public static class GeoPoint{
        public final Geometry geometry;
        public final Point point;

        /**
         * constructor for a GeoPoint
         * @param geometry a geometry
         * @param point a point on the geometry
         */
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

    //region find intersections
    /**
     * calculating the intersections points between a ray and an entity that implements this interface
     * using the linear calculation methods for an intersection to an intersectable geometry
     *
     * @param ray to calculate the intersections to
     * @return list of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ?
                null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * for now - find intersections between a ray and a geometry
     * @param ray the ray to find intersections with
     * @return a list with GeoPoints that represent the intersections between the ray and the geometry
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * find intersections between a ray and a geometry
     * @param ray the ray to find intersections with
     * @return a list with GeoPoints that represent the intersections between the ray and the geometry
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
    //endregion
}
