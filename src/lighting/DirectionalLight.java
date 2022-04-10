package lighting;

import primitives.*;

public class DirectionalLight extends Light implements LightSource{

    private final Vector direction;

    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point point){
        return this.direction;
    }
}
