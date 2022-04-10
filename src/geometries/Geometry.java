package geometries;

import primitives.*;

public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    private Material material = new Material();

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getter
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * sets the emission color
     * @param emission- the geometry's color.
     * @return the geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }


    /**
     * calculating the normal vector for a point on the geometry
     * using the linear calculation methods for a normal to a geometry
     *
     * @param point a point on the geometry
     * @return new normal to the point
     */
    public abstract Vector getNormal(Point point);
}
