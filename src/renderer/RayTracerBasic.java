package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 *  implementation of the abstract class RayTracerBase
 */
public class RayTracerBasic extends RayTracerBase{

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    //region traceRay
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray);
        if (intersectionPoints == null)
            return this.scene.background;
        GeoPoint point = ray.findClosestGeoPoint(intersectionPoints);
        return calcColor(point, ray);
    }
    //endregion

    //region calcColor
    /**
     * calculating the color of a specific point, taking into account the lightning,
     * transparency of the point itself and other affects of the surrounding are of the point in space
     * @param geoPoint calculate the color of this point
     * @return for now - the ambient light's intensity
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission())
                .add(calcLocalEffects(geoPoint, ray));
    }
    //endregion

    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir ();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        int nShininess = intersection.geometry.getMaterial().nShininess;
        double kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
        return lightIntensity.scale(ks *alignZero (Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), nShininess)));
    }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(alignZero(Math.abs(n.dotProduct(l)) * kd));
    }


}
