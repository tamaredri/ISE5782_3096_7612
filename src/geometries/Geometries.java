package geometries;

import primitives.*;
import java.util.*;

/**
 * a collection of geometries. using the composite design pattern
 */
public class Geometries extends Intersectable{
    List<Intersectable> geometries;

    //region default constructor
    /**
     * generates an empty collection of geometries
     */
    public Geometries() {
        geometries = new LinkedList<Intersectable>();
    }
    //endregion

    //region constructor with Intersectable parameter
    /**
     *  create a collection of geometries from the given collection
     * @param geometries a collection of geometries
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = new LinkedList<Intersectable>();
        this.add(geometries);
    }
    //endregion

    //region add function
    /**
     * add a collection of geometries to the existing collection
     * @param geometries a collections to add to the current collection
     */
    public void add(Intersectable... geometries){
        for (int i =0; i<geometries.length; i++)
            this.geometries.add(geometries[i]);
    }
    //endregion

    //region findGeoIntersectionsHelper
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> Intersection = new LinkedList<>();

        //go threw all the geometries and add their intersections
        for (Intersectable intersectable: geometries) {
            List<GeoPoint> currentIntersection = intersectable.findGeoIntersectionsHelper(ray);
            if(currentIntersection != null) //no intersection was found
                Intersection.addAll(currentIntersection);
        }

        if(Intersection.size() == 0)
            return null;
        return Intersection;
    }
    //endregion
}
