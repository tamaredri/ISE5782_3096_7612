package lighting;

import primitives.*;

/**
 * representation of a light in general
 */
abstract class Light {

    /**
     * the intensity of the light
     */
    private Color intensity;

    /**
     * construct a light
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * gets the intensity of the light
     * @return the intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
