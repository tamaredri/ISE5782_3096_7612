package lighting;

import primitives.*;

/**
 * a representation of a point light - an ordinary lamp
 */
public class PointLight extends Light implements LightSource{
    private final Point position;

    // the attenuation factors
    private Double3 kC = new Double3(1);
    private Double3 kL = new Double3(0);
    private Double3 kQ = new Double3(0);

    //region constructor
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
        double d = point.distance(this.position);                           // distance from the light source
        return this.getIntensity().reduce (kC.add(kL.scale(d)).add(kQ.scale(d*d)));
    }
    //endregion

    //region setters
    /**
     * set the kC attenuation factor
     * @param kC the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKc(Double3 kC) {
        this.kC = kC;
        return this;
    }

    /**
     * set the kL attenuation factor
     * @param kL the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKl(Double3 kL) {
        this.kL = kL;
        return this;
    }

    /**
     * set the kQ attenuation factor
     * @param kQ the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKq(Double3 kQ) {
        this.kQ = kQ;
        return this;
    }
    //endregion
}
