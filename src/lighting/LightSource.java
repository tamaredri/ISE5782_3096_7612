package lighting;

import primitives.*;

/**
 * representation of a light source - an object that generates light
 */
public interface LightSource {

    /**
     *  gets the light intensity on a specific point in space
     * @param p the wanted point
     * @return the light intensity on 'p'
     */
    Color getIntensity(Point p);

    /**
     * gets a vector from the light source to a point in space
     * @param p the destination point of the vector
     * @return a vector from the reference point of the light source to 'p'
     */
    Vector getL(Point p);

}
