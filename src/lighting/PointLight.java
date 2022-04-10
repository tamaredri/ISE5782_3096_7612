package lighting;

import primitives.*;

public class PointLight extends Light implements LightSource{

    private final Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    @Override
    public Vector getL(Point point){
        return point.subtract(this.position).normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        double d = point.distance(this.position);
        return this.getIntensity().scale(1/(kC + kL * d + kQ * d * d));
    }

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    //region setters
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
    //endregion
}
