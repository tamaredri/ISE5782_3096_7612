package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.*;

/**
 * a class that represents a camera.
 */
public class Camera {
    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

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
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }
    //endregion

    //region setViewPlane
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    //endregion

    //region setVPDistance
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }
    //endregion

    //region setRayTracer
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }
    //endregion

    //region constructor
    public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException{
        if (!isZero(vTo.dotProduct(vUp))){
            throw new IllegalArgumentException("constructor threw - vUp and vTo are not orthogonal");
        }
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
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
        if(!isZero(xJ))  PIJ = PIJ.add(vRight.scale(xJ));
        if(!isZero(yJ))  PIJ = PIJ.add(vUp.scale(yJ));

        return new Ray(p0, PIJ.subtract(p0));
    }
    //endregion

    //region renderImage
    /**
     * render the image and fill the pixels with the desired colors
     * using the ray tracer to find the colors
     * and the image writer to color the pixels
     * @throws MissingResourceException if one of the fields are uninitialized
     */
    public void renderImage() throws MissingResourceException{
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
    }
    //endregion

    //region printGrid
    /**
     *  print a grid on the image without running over the original image
     * @param interval the size of the grid squares
     * @param color the color of the grid
     * @throws MissingResourceException
     */
    public void printGrid(int interval, Color color) throws MissingResourceException{
        if (this.imageWriter == null) // the image writer is uninitialized
            throw new MissingResourceException("Camera is missing some fields", "Camera", "imageWriter");
        for (int i = 0; i< imageWriter.getNy(); i++)
            for(int j = 0; j< imageWriter.getNx(); j++)
                if(i%interval == 0 || j%interval == 0)  // color the grid
                    imageWriter.writePixel(j,i,color);
    }
    //endregion

    //region writeToImage
    /**
     * create the image file using the image writer
     */
    public void writeToImage() {
        if (this.imageWriter == null) // the image writer is uninitialized
            throw new MissingResourceException("Camera is missing some fields", "Camera", "imageWriter");
        imageWriter.writeToImage();
    }
    //endregion

}
