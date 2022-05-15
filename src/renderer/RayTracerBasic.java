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
    /**
     * for the floating point bug
     */
    private static final double DELTA = 0.1;
    /**
     * constructor accepting the scene to trace the rays trough
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    //region traceRay
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray); // intersect the ray with the geometries
        if (intersectionPoints == null)     // no intersection was found
            return this.scene.background;   // the background color

        GeoPoint point = ray.findClosestGeoPoint(intersectionPoints);   // find the closest point - the point the ray 'sees' first
        return calcColor(point, ray);                                   // return the calculated color of this point
    }
    //endregion

    //region calcColor
    /**
     * calculating the color of a specific point, taking into account the local effects on the point.
     * including the: <ul>
     *     <li>ambient light</li>
     *     <li>geometry's intensity (color)</li>
     *     <li>lighting</li>
     *     <li>shadows</li>
     *     <li>transparency of the geometry the point is on</li>
     *     <li>other affects of the surrounding are of the point in space</li>
     * </ul>
     * @param geoPoint calculate the color of this point
     * @return the calculated color of the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity()        // the intensity of the ambient light
                .add(geoPoint.geometry.getEmission())   // the intensity of the geometry
                .add(calcLocalEffects(geoPoint, ray));  // the light sources effect on the intensity
    }
    //endregion

    //region calcLocalEffects
    /**
     * calculate the effects of the lighting on the intensity of the point
     * @param geoPoint a GeoPoint object to calculate the effects on
     * @param ray the ray of the camera pointing to the GeoPoint's geometry
     * @return  the calculated color on the point in the geoPoint
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDir ();                                   // the vec from the camera to the geometry
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);     // the normal to the geometry

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)                                                 // dot product resulted zero - the vectors are orthogonal
            return Color.BLACK;                                      // the camera doesn't see the light

        // the shine, diffuse and specular factors
        int nShininess = geoPoint.geometry.getMaterial().nShininess;
        double kd = geoPoint.geometry.getMaterial().kD, ks = geoPoint.geometry.getMaterial().kS;
        Color color = Color.BLACK;                                  // the base color

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);            // vec from the lightSource to the geometry
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sing(nv) ->
                               // the camera and the light source are on the same side of the surface
                Color lightIntensity = lightSource.getIntensity(geoPoint.point);    // the base intensity from the light source
                color = color.add(
                        calcDiffusive(kd, l, n, lightIntensity),                    // add the diffusion effect
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));     // add the specular effect
            }
        }
        return color;
    }
    //endregion

    //region calcSpecular
    /**
     * the specular effect on the object according to the phong reflection model
     * @param ks specular factor
     * @param l vec from the light source to a point on the geometry
     * @param n normal vec to the point on the geometry
     * @param v vec from the camera to the geometry = the camera's eye
     * @param nShininess shininess factor
     * @param lightIntensity intensity of the light
     * @return calculated intensity with the specular effect
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n))).normalize();    // the specular ray

        // the phong model formula for the specular effect: ks ∙ ( 𝒎𝒂𝒙 (𝟎, −𝒗 ∙ 𝒓) ^ 𝒏𝒔𝒉 ) ∙ 𝑰
        return lightIntensity
                .scale(ks * alignZero( Math.pow( Math.max(0, v.scale(-1).dotProduct(r)),
                                                nShininess)));
    }
    //endregion

    //region calcDiffusive
    /**
     * the diffusion effect on the object according to the phong reflection model
     * @param kd diffusive factor
     * @param l vec from the light source to a point on the geometry
     * @param n vec from the light source to a point on the geometry
     * @param lightIntensity intensity of the light
     * @return calculated intensity with the diffusive effect
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        // the phong model formula for the diffusive effect: 𝒌𝑫 ∙| 𝒍 ∙ 𝒏 |∙ 𝑰
        return lightIntensity.scale( alignZero( kd * Math.abs(n.dotProduct(l))));
    }
    //endregion
}
