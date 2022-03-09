package geometries;

import primitives.*;

public interface Geometry extends Intersectable {
    /**
     * calculating the normal vector for a point on the geometry
     * using the linear calculation methods for a normal to a geometry
     *
     * @param point a point on the geometry
     * @return new normal to the point
     */
    Vector getNormal(Point point);
}
