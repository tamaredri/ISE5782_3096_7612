package geometries;

import primitives.*;

public class Cylinder extends Tube{
    private double height;

    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "axisRay=" + axisRay.toString() +
                ", radius=" + radius +
                ", height=" + height +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
