package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * create a beam of points in a relative radius from a Ray in space
 */
public class RayBeam {
    private final Ray centerRay;
    private final Vector vUp;
    private final Vector vRight;
    private final double height;
    private final double width;
    private final double DISTANCE = 200;
    private int AMOUNT = 10; //TODO calculate by height * width

    //region getters
    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }
    //endregion

    //region constructors
    public RayBeam(Ray centerRay, Vector vUp, Vector vRight, double height, double width) {
        if(!isZero(vUp.dotProduct(vRight)))
            throw new IllegalArgumentException("vectors are not orthogonal");
        this.vUp = vUp.normalize();
        this.vRight = vRight.normalize();
        this.centerRay = centerRay;
        this.height = height;
        this.width = width;
    }

    public RayBeam(Ray centerRay, double height, double width) {
        this.centerRay = centerRay;
        this.height = height;
        this.width = width;
        // make orthogonal vectors
        Vector dir = centerRay.getDir();
        if(!isZero(dir.getX()) || !isZero(dir.getY()))
            this.vUp = new Vector(dir.getY(),-dir.getX(), 0);
        else
            this.vUp = new Vector(0,-dir.getZ(), dir.getY());

        this.vUp.normalize();
        this.vRight = this.vUp.crossProduct(dir).normalize();
    }
    //endregion

    //region constructRayBeam
    List<Ray> constructRayBeam(){
        Point center = centerRay.getPoint(DISTANCE);

        List<Ray> list = new LinkedList<>();
        list.add(centerRay);

        BlackBoard blackBoard = new BlackBoard(this.width, this.height);

        for (BlackBoard.Point2D point: blackBoard.generatePoints(AMOUNT)) {
            Point endPoint = center;
            if(!isZero(point.getX()))  endPoint = endPoint.add(vRight.scale(point.getX()));       // move the point in vRight direction
            if(!isZero(point.getY()))  endPoint = endPoint.add(vUp.scale(point.getY()));          // move the point in vUp direction

            list.add(new Ray(centerRay.getP0(), endPoint.subtract(centerRay.getP0()).normalize()));
        }
        return list;
    }
    //endregion
}
