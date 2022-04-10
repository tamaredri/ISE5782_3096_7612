package lighting;

import primitives.*;

/**
 *  this is a class that represents the environmental lightning in a scene
 */
public class AmbientLight extends Light{

    //region AmbientLight
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }
    //endregion

    //region defaultConstructor
    public AmbientLight() {
        super(new Color(0,0,0));
    }
    //endregion
}
