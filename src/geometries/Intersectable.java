package geometries;

import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public abstract class Intersectable {

    public static class GeoPoint{
        public final Geometry geometries;
        public final Point point;

        public GeoPoint(Geometry geometries, Point point) {
            this.geometries = geometries;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return this.point.equals(geoPoint.point) && this.geometries.equals(geoPoint.geometries);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometries=" + geometries +
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
    public abstract List<Point> findIntersections(Ray ray);

    public final List<Point> findGeoIntersections(Ray ray){
        List<GeoPoint> intersections = findGeoIntersectionsHelper(ray);
        return (intersections == null) ? null: intersections.stream().map(i->i.point).toList();
    }

    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

    }

}
