package elements;

import primitives.*;

/**
 *  this is a class that represents the environmental lightning in a scene
 */
public class AmbientLight {

    private Color intensity;

    //region AmbientLight
    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }
    //endregion

    //region defaultConstructor
    public AmbientLight() {
        this.intensity = new Color(0,0,0);
    }
    //endregion

    //region getIntensity
    public Color getIntensity() {
        return intensity;
    }
    //endregion
}
