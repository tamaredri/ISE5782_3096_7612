package lighting;

import primitives.*;

/**
 * presentation of a light source - an object that generates light
 */
public interface LightSource {

    /**
     * returns the light intensity on a specific point in space
     * @param p the wanted point
     * @return the light intensity on 'p'
     */
    Color getIntensity(Point p);

    /**
     * returns a vector from the light source to a point in space
     * @param p the destination point of the vector
     * @return a vector from the reference point of the light source to 'p'
     */
    Vector getL(Point p);

    /**
     * returns the distance between a given point and the light source
     * @param point the point to calculate distance from
     * @return distance between point and light source
     */
    double getDistance(Point point);

}
