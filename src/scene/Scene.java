package scene;

import elements.AmbientLight;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.*;

/**
 * representing a full 3D scene
 * this class is implemented using the Builder design pattern
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    //region constructor
    public Scene(String name) {
        this.geometries = new Geometries();
        this.name = name;
    }
    //endregion

    //region setBackground
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }
    //endregion

    //region setGeometries
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
    //endregion

    //region addGeometries
    public Scene addGeometry(Intersectable geometry){
        geometries.add(geometry);
        return this;
    }
    //endregion

    //region setAmbientLight
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
    //endregion

}
