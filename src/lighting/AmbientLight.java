package lighting;

import primitives.*;

/**
 *  this is a class that represents the environmental lightning in a scene
 */
public class AmbientLight extends Light{

    //region constructor
    /**
     * construct the ambient light using a color, and it's attenuation factor
     * @param Ia the base intensity of the light
     * @param Ka the attenuation factor of the intensity for each rgb color
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }
    //endregion

    //region default constructor
    public AmbientLight() {
        super(new Color(0,0,0));
    }
    //endregion
}
