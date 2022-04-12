package scene;

import geometries.*;
import lighting.*;
import primitives.*;
import java.util.*;

/**
 * representing a full 3D scene
 * this class is implemented using the Builder design pattern
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights;


    //region constructor
    /**
     * construct a scene. giving default values to all the fields
     */
    public Scene(String name) {
        this.geometries = new Geometries();
        this.name = name;
        this.background = Color.BLACK;
        this.ambientLight = new AmbientLight();
        this.lights = new LinkedList<>();
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

    //region setLights
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
    //endregion
}
