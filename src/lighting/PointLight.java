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

    //region getDistance
    @Override
    public double getDistance(Point point) {
        return this.position.distance(point);
    }
    //endregion

    //region getIntensity
    @Override
    public Color getIntensity(Point point) {
        double d = point.distance(this.position);  // distance from the light source
        try {
            return this.getIntensity().reduce(kC.add(kL.scale(d)).add(kQ.scale(d * d)));
        }
        catch (Exception x){ // in case vector zero is constructed
            return this.getIntensity();
        }
    }
    //endregion

    //region setters
    /**
     * set the kC attenuation factor
     * @param kC the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKc(double kC) {
        this.kC = new Double3(kC);
        return this;
    }

    /**
     * set the kL attenuation factor
     * @param kL the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKl(double kL) {
        this.kL = new Double3(kL);
        return this;
    }

    /**
     * set the kQ attenuation factor
     * @param kQ the attenuation factor
     * @return the point light. builder pattern
     */
    public PointLight setKq(double kQ) {
        this.kQ = new Double3(kQ);
        return this;
    }
    //endregion
}
