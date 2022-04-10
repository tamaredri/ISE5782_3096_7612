package lighting;

import primitives.*;

public class SpotLight extends PointLight{

    private final Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }



    @Override
    public Color getIntensity(Point point) {
        return super.getIntensity(point).scale(Math.max(0, this.direction.dotProduct(this.getL(point))));
    }
}
