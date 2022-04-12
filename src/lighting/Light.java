package lighting;

import primitives.*;

/**
 * representation of a light in general
 */
abstract class Light {
    private Color intensity;

    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }
}
