package geometries;

import primitives.*;

public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK; // The color f the geometry
    private Material material = new Material(); // The material of the geometry

    //region getters
    /**
     * @return the material of the geometry
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @return the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }
    //endregion

    //region setters
    /**
     *  setter for the material. builder pattern
     * @param material to set to the geometry
     * @return the geometry itself
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * setter for the emission color. builder pattern
     * @param emission to set to the geometry.
     * @return the geometry itself
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    //endregion

    //region getNormal
    /**
     * calculating the normal vector for a point on the geometry
     * using the linear calculation methods for a normal to a geometry
     * @param point a point on the geometry
     * @return new normal to the point
     */
    public abstract Vector getNormal(Point point);
    //endregion
}
