package lighting;

import primitives.*;

abstract class Light {

    private Color intensity;

    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }
}
