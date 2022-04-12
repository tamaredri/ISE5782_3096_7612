package geometries;

import primitives.*;
import java.util.*;

/**
 * a collection of geometries. using the composite design pattern
 */
public class Geometries extends Intersectable{
    List<Intersectable> intersectableGeometries;

    //region default constructor
    public Geometries() {
        intersectableGeometries = new LinkedList<>();
    }
    //endregion

    //region constructor with Intersectable parameter
    public Geometries(Intersectable... geometries) {
        this.intersectableGeometries = new LinkedList<>();
        this.add(geometries);
    }
    //endregion

    //region add function
    /**
     * concatenate a collection of geometries to the existing collection
     * @param geometries the collection to concat
     */
    public void add(Intersectable... geometries){
        for (int i =0; i<geometries.length; i++)
            this.intersectableGeometries.add(geometries[i]);
    }
    //endregion

    //region findGeoIntersectionsHelper
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> Intersections = new LinkedList<>();

        for (Intersectable intersectable: this.intersectableGeometries) {
            List<GeoPoint> currentIntersection = intersectable.findGeoIntersectionsHelper(ray);
            if(currentIntersection != null) // intersection was found
                Intersections.addAll(currentIntersection);
        }

        if(Intersections.size() == 0)
            return null;
        return Intersections;
    }
    //endregion
}
