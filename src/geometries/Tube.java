package geometries;

import primitives.*;

public class Tube implements Geometry{
    protected Ray axisRay;
    protected double radius;

    //region constructor
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }
    //endregion

    //region getters
    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }
    //endregion

    //region to string Override
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay.toString() +
                ", radius=" + radius +
                '}';
    }
    //endregion

    //region get normal function
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
    //endregion
}
