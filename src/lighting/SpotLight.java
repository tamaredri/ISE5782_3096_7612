package lighting;

import primitives.*;

/**
 * a representation of a spotlight - a lamp with a direction and an opening angle of the light beam
 */
public class SpotLight extends PointLight{
    /**
     * the direction of the light
     */
    private final Vector direction;

    //region constructor
    /**
     * construct a spotlight - default angle of opening is 180 deg
     * @param intensity the intensity of the light
     * @param position  the position of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
    //endregion

    //region
    @Override
    public Color getIntensity(Point point) {
        return super.getIntensity(point)                                            // the intensity from the point light
                .scale( Math.max(0, this.direction.dotProduct(this.getL(point))));  // add the effect of the spotlight - the angle of the beam
    }
    //endregion
}
