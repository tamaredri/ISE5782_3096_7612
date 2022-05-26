package renderer;

import primitives.*;

import java.util.List;

/**
 * create a beam of points in a relative radius from a Ray in space
 */
public class PointBeam {
    private final Ray centerRay;
    private Vector vUp;
    private Vector vRight;
    private final double radius;
    private final int numOfRay;

    //region constructors
    public PointBeam(Ray centerRay, Vector vUp, Vector vRight, double radius, int numOfRay) {
        // check orthogonal
        this.centerRay = centerRay;
        this.vUp = vUp;
        this.vRight = vRight;
        this.radius = radius;
        this.numOfRay = numOfRay;
    }

    public PointBeam(Ray centerRay, double radius, int numOfRay) {
        // make orthogonal vectors
        this.centerRay = centerRay;
        this.radius = radius;
        this.numOfRay = numOfRay;
    }
    //endregion

    // A -> C
    // input = ray, radius ( glossiness parameter ).
    // output = collection of 3D points
    List<Point> constructPointBeam(){
        return null;
    }

    // C
    // input = collection of 2D points - index to move in vUp and vRight,
    // output = collection of a 3D points - the actual points in space
    Point makePoint(double x, double y){
        return null;
    }
}
