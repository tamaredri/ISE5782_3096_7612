package lighting;

import primitives.*;

/**
 * a representation of a point light - an ordinary lamp
 */
public class PointLight extends Light implements LightSource{

    /**
     * the position of the light
     */
    private final Point position;
    /**
     * the attenuation factors
     */
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    //region constructor
    /**
     * constructs a point light
     * @param intensity the color of the light
     * @param position the position of the light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }
    //endregion

    //region getL
    @Override
    public Vector getL(Point point){
        return point.subtract(this.position).normalize();
    }
    //endregion

    //region getIntensity
    @Override
    public Color getIntensity(Point point) {
        double d = point.distance(this.position);                           // the distance of the point from the light source
        return this.getIntensity().scale(1 / (kC + kL * d + kQ * d * d));   // the calculated attenuation of the light relative to the distance
    }
    //endregion

    //region setters
    /**
     * set the kC attenuation factor
     * @param kC the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * set the kL attenuation factor
     * @param kL the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * set the kQ attenuation factor
     * @param kQ the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
    //endregion
}
