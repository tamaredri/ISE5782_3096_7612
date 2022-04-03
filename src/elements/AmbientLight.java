package elements;

import primitives.*;

public class AmbientLight {

    private Color intensity;

    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }

    public AmbientLight() {
        this.intensity = new Color(0,0,0);
    }

    public Color getIntensity() {
        return intensity;
    }
}
