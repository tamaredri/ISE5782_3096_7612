package geometries;

import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public abstract class Intersectable {

    public static class GeoPoint{
        public final Geometry geometry;
        public final Point point;

        public GeoPoint(Geometry geometries, Point point) {
            this.geometry = geometries;
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

    /**
     * calculating the intersections points between a ray and an entity that implements this interface
     * using the linear calculation methods for an intersection to an intersectable geometry
     *
     * @param ray to calculate the intersections to
     * @return list of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
