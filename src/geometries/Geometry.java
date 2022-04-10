package geometries;

import primitives.*;

public abstract class Geometry extends Intersectable {
    /**
     * the color of the geometry
     */
    protected Color emission = Color.BLACK;
    /**
     * the material the geometry is made of
     */
    private Material material = new Material();

    //region getters
    /**
     *  getter for the material
     * @return the material of the geometry
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * getter for the emission
     * @return the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }
    //endregion

    //region setters
    /**
     *  setter for the material. builder pattern
     * @param material the material for the geometry
     * @return the geometry itself
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * setter for the emission color. builder pattern
     * @param emission the geometry's color.
     * @return the geometry itself
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    //endregion

    /**
     * calculating the normal vector for a point on the geometry
     * using the linear calculation methods for a normal to a geometry
     *
     * @param point a point on the geometry
     * @return new normal to the point
     */
    public abstract Vector getNormal(Point point);
}
