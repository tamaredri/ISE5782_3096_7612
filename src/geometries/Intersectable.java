package geometries;

import primitives.*;
import java.util.List;

/**
 *
 */
public interface Intersectable {
    /**
     * calculating the intersections points between a ray and an entity that implements this interface
     * using the linear calculation methods for an intersection to an intersectable geometry
     *
     * @param ray to calculate the intersections to
     * @return list of intersection points
     */
    public List<Point> findIntersections(Ray ray);
}
