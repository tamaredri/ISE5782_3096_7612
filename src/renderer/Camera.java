package renderer;

import primitives.*;
import java.util.MissingResourceException;
import static primitives.Util.*;
import org.ejml.data.SimpleMatrix;

/**
 * a class that represents a camera.
 */
public class Camera {
    /**
     * the reference point of the camera
     */
    private Point p0;
    /**
     * the vector that points upwards relative to the camera
     */
    private Vector vUp;
    /**
     * the vector that points onwards relative to the camera
     */
    private Vector vTo;
    /**
     * the vector that points to the right side relative to the camera
     */
    private Vector vRight;
    /**
     * width of the view plane
     */
    private double width;
    /**
     * height of the view plane
     */
    private double height;
    /**
     * distance between the camera and the view plane
     */
    private double distance;
    /**
     * the object that writes the image
     */
    private ImageWriter imageWriter;
    /**
     * the object that generates rays from the camera to the view plane
     */
    private RayTracerBase rayTracer;

    //region constructor
    /**
     * construct a camera
     * @param p0 the reference point of the camera
     * @param vTo the onward direction of the camera
     * @param vUp the upward direction of the camera
     * @throws IllegalArgumentException throws an exception if: <ul>
     *     <li>
     *         the reference vectors (vUp, vTo) are not orthogonal
     *     </li>
     * </ul>
     */
    public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException{
        if (!isZero(vTo.dotProduct(vUp))){
            throw new IllegalArgumentException("constructor threw - vUp and vTo are not orthogonal");
        }
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize(); // TODO remove normalize??
    }
    //endregion

    //region getters
    public Point getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }
    //endregion

    //region setImageWriter
    /**
     * set the image writer
     * @param imageWriter the imageWriter object to set
     * @return the camera itself. builder pattern
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }
    //endregion

    //region setViewPlane
    /**
     * set the perimeters of the view plane
     * @param width the width of the VP
     * @param height the height of the VP
     * @return the camera itself. builder pattern
     * @return the camera itself. builder pattern
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    //endregion

    //region setVPDistance
    /**
     * set the distance of the camera from the view plane
     * @param distance the distance from the VP
     * @return the camera itself. builder pattern
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }
    //endregion

    //region setRayTracer
    /**
     * set the rey tracer
     * @param rayTracer the rayTracer object  to set
     * @return the camera itself. builder pattern
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }
    //endregion

    //region constructRay
    /**
     * constructs a ray from the camera through pixel i,j.
     * @param nX number of pixels on the width of the view plane.
     * @param nY number of pixels on the height of the view plane.
     * @param j location of the pixel in the X direction.
     * @param i location of the pixel in the Y direction.
     * @return the constructed ray - from p0 through the wanted pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        Point pc = p0.add(vTo.scale(distance));     // center of the view plane
        double Ry = height/nY;                      // Ratio - pixel height
        double Rx = width/nX;                       // Ratio - pixel width

        double yJ = alignZero(-(i - (nY - 1) / 2d) * Ry);       // move pc Yi pixels
        double xJ = alignZero((j - (nX - 1) / 2d) * Rx);        // move pc Xj pixels

        Point PIJ = pc;
        if(!isZero(xJ))  PIJ = PIJ.add(vRight.scale(xJ));       // move the point in vRight direction
        if(!isZero(yJ))  PIJ = PIJ.add(vUp.scale(yJ));          // move the point in vUp direction

        return new Ray(p0, PIJ.subtract(p0));       // the ray through the wanted pixel
    }
    //endregion

    //region renderImage
    /**
     * render the image and fill the pixels with the desired colors
     * using the ray tracer to find the colors
     * and the image writer to color the pixels
     * @throws MissingResourceException if one of the following fields are uninitialized (unable to render the image):<ul>
     *     <li> imageWriter </li>
     *     <li> reyTracer </li>
     *     <li> width </li>
     *     <li> height </li>
     *     <li> distance </li>
     * </ul>
     */
    public Camera renderImage() throws MissingResourceException{
        if (imageWriter == null || rayTracer == null || width == 0 || height == 0 || distance == 0) { //default values
            throw new MissingResourceException("Camera is missing some fields", "Camera", "field");
        }

        for (int i = 0; i < imageWriter.getNx(); i++){
            for (int j = 0; j<imageWriter.getNy(); j++){
                imageWriter.writePixel(j, i, //                                             // for each pixel (j,i)
                           rayTracer.traceRay( //                                           // find the color of the pixel using
                           constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i)));  // construction of a ray through the pixel
                                                                                            // and intersecting with the geometries
            }
        }
        return this;
    }
    //endregion

    //region printGrid
    /**
     *  print a grid on the image without running over the original image
     * @param interval the size of the grid squares
     * @param color the color of the grid
     * @throws MissingResourceException if the imageWriter is uninitialized - unable to print a grid
     */
    public void printGrid(int interval, Color color) throws MissingResourceException{
        if (this.imageWriter == null) // the image writer is uninitialized
            throw new MissingResourceException("Camera is missing some fields", "Camera", "imageWriter");

        // loop over j
        for (int i = 0;  i< imageWriter.getNy(); i++)
            for (int j = 0;j< imageWriter.getNx() ; j += interval)
                imageWriter.writePixel(j,i,color);  // color the grid

        // loop for j
        for (int i = 0;  i< imageWriter.getNy(); i+= interval)
            for (int j = 0;j< imageWriter.getNx() ; j++)
                imageWriter.writePixel(j,i,color);  // color the grid
    }
    //endregion

    //region writeToImage
    /**
     * create the image file using the imageWriter object
     * @throws MissingResourceException if the imageWriter in uninitialized - unable to generate the image
     */
    public void writeToImage() {
        if (this.imageWriter == null) // the image writer is uninitialized
            throw new MissingResourceException("Camera is missing some fields", "Camera", "imageWriter");
        imageWriter.writeToImage();
    }
    //endregion

    //region move camera for any 3 vectors - private function
    /**
     * rotating a space, represented by 3 vectors that span the space, in an angle requested
     * using the rotation matrix method
     * <ul>
     *     <li>trust that the vectors span a space, this is a private function</li>
     * </ul>
     * @param rotationVector the vector the space will rotate around
     * @param vectorToMove the vector that will be rotated and is a part of the space's span
     * @param orthogonalVector the third vector that spans the space
     * @param angle the size of the rotation - angle in degrees
     * @return vectorToMove rotated to the left with the requested angle
     */
    private Vector rotateCameraAroundVector(Vector rotationVector, Vector vectorToMove, Vector orthogonalVector, double angle) {
        // convert the angle to radians
        angle = Math.toRadians(angle);

        SimpleMatrix matrixP = new SimpleMatrix(3,3);
        // fill the P matrix -
        // the conversion matrix between the standard base (E) to our base (F)
        matrixP.set(0,0, rotationVector.getX());
        matrixP.set(1,0, rotationVector.getY());
        matrixP.set(2,0, rotationVector.getZ());
        matrixP.set(0,1, vectorToMove.getX());
        matrixP.set(1,1, vectorToMove.getY());
        matrixP.set(2,1, vectorToMove.getZ());
        matrixP.set(0,2, orthogonalVector.getX());
        matrixP.set(1,2, orthogonalVector.getY());
        matrixP.set(2,2, orthogonalVector.getZ());

        SimpleMatrix matrixA = new SimpleMatrix(3,3);
        // fill the A matrix -
        // the copy matrix in our base (F)
        matrixA.set(0,0, 1);
        matrixA.set(1,0, 0);
        matrixA.set(2,0, 0);
        matrixA.set(0,1, 0);
        matrixA.set(1,1, alignZero(Math.cos(angle)));
        matrixA.set(2,1, alignZero(Math.sin(angle)));
        matrixA.set(0,2, 0);
        matrixA.set(1,2, -alignZero(Math.sin(angle)));
        matrixA.set(2,2, alignZero(Math.cos(angle)));

        // the invertible matrix -
        // the conversion matrix on the opposite way from matrixP,
        // from our base (F) to the standard base (E)
        SimpleMatrix matrixInvertP = matrixP.invert();

        // the full copy matrix from the standard base (E) to the standard base (E)
        SimpleMatrix copyMatrix = matrixP.mult(matrixA).mult(matrixInvertP);

        // convert the vector that is going to be copied from a vector to a matrix ot execute the matrix multiplication
        SimpleMatrix matrixVectorToMove = new SimpleMatrix(3,1);
        matrixVectorToMove.set(0,0,vectorToMove.getX());
        matrixVectorToMove.set(1,0,vectorToMove.getY());
        matrixVectorToMove.set(2,0,vectorToMove.getZ());

        // the converted vector in a form of a matrix
        matrixVectorToMove = copyMatrix.mult(matrixVectorToMove);

        // @MARK - no need to check if the zero vector was constructed

        // convert the matrix to a vector
        return new Vector(matrixVectorToMove.get(0,0),
                          matrixVectorToMove.get(1,0),
                          matrixVectorToMove.get(2,0)).normalize();
    }
    //endregion

    //region rotate around vTo
    /**
     * rotate the camera around the vTo vector -
     * <ul>
     *     <li>positive angle: spin the camera on the reverse of the clockwise direction = spin the scene to clockwise</li>
     *     <li>negative angle: spin the camera clockwise = spin the scene on the reverse of the clockwise direction</li>
     * </ul>
     * @param angle the angle of the rotation
     * @return the camera itself. builder pattern
     */
    public Camera rotateAroundVTo(double angle){
        vRight = rotateCameraAroundVector(vTo, vRight, vUp, angle);
        vUp = vRight.crossProduct(vTo).normalize();
        return this;
    }
    //endregion

    //region rotate around vUp
    /**
     * rotate the camera around the vUp vector -
     * <ul>
     *     <li>positive angle: rotate the camera to the left = move the scene to the right</li>
     *     <li>negative angle: rotate the camera to the right = move the scene to the left</li>
     * </ul>
     * @param angle the angle of the rotation
     * @return the camera itself. builder pattern
     */
    public Camera rotateAroundVUp(double angle){
        vTo = rotateCameraAroundVector(vUp, vTo, vRight, angle);
        vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }
    //endregion

    //region rotate around vRight
    /**
     * rotate the camera around the vRight vector -
     * <ul>
     *     <li>positive angle: rotate the camera upward = move the scene downward</li>
     *     <li>negative angle: rotate the camera downward = move the scene upward</li>
     * </ul>
     * @param angle the angle of the rotation
     * @return the camera itself. builder pattern
     */
    public Camera rotateAroundVRight(double angle){
        vUp = rotateCameraAroundVector(vRight, vUp, vTo, angle);
        vTo = vUp.crossProduct(vRight).normalize();
        return this;
    }
    //endregion

    //region move camera's reference point
    /**
     * move the reference point of the camera using the amount of units to move in the direction of each reference vector
     * @param moveVUp on the direction of vUp
     * @param moveVTo on the direction of vTo
     * @param moveVRight on the direction of vRight
     * @return the camera itself. builder pattern
     */
    public Camera moveReferencePoint(double moveVUp, double moveVTo,double moveVRight){
        if(!isZero(moveVUp))     this.p0 = this.p0.add(vUp.scale(moveVUp));
        if(!isZero(moveVRight))  this.p0 = this.p0.add(vRight.scale(moveVRight));
        if(!isZero(moveVTo))     this.p0 = this.p0.add(vTo.scale(moveVTo));
        return this;
    }
    //endregion
}
