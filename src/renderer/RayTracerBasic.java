package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase{

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersectionPoints = scene.geometries.findIntersections(ray);
        if (intersectionPoints == null)
            return this.scene.background;
        Point point = ray.findClosestPoint(intersectionPoints);
        return calcColor(point);
    }

    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
