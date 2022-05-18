package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.*;

/**
 *  implementation of the abstract class RayTracerBase
 */
public class RayTracerBasic extends RayTracerBase{
    /**
     * for the floating point bug
     */

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = new Double3(1.0);


    /**
     * constructor accepting the scene to trace the rays trough
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    //region traceRay
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint gp = findClosestIntersection(ray); // intersect the ray with the geometries
        if (gp == null)     // no intersection was found
            return this.scene.background;   // the background color
        return calcColor(gp, ray);                                   // return the calculated color of this point
    }
    //endregion

    //region unshaded
    private boolean unshaded(GeoPoint geopoint, Vector n, LightSource lightSource) {
        Vector lightDirection = lightSource.getL(geopoint.point).scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);

        List<GeoPoint> intersections = scene.geometries.
                                       findGeoIntersections(lightRay,
                                                 lightSource.getDistance(geopoint.point));
        if(intersections == null) return true;

        Double3 transparent = Double3.ONE; // full transparency
        for (GeoPoint gp : intersections) {
            transparent = transparent.product(gp.geometry.getMaterial().kT);
            if(transparent.subtract(new Double3(MIN_CALC_COLOR_K)).lowerThan(0))
                return false;
        }
        return true;
    }
    //endregion

    // region unshaded
    private Double3 transparency(GeoPoint geopoint,  Vector n, LightSource lightSource, Vector l){
        Vector lightDirection = lightSource.getL(geopoint.point).scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);

        List<GeoPoint> intersections = scene.geometries.
                                       findGeoIntersections(lightRay,
                                                 lightSource.getDistance(geopoint.point));
        if(intersections == null) return Double3.ONE;

        Double3 transparent = Double3.ONE; // full transparency
        for (GeoPoint gp : intersections) {
            transparent = transparent.product(gp.geometry.getMaterial().kT);
            if(transparent.subtract(new Double3(MIN_CALC_COLOR_K)).lowerThan(0))
                return Double3.ZERO;
        }
        return transparent;
    }
    //endregion
    //region


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
                //.add(geoPoint.geometry.getEmission())   // the intensity of the geometry
                .add( calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));  // the light sources effect on the intensity
    }
    //endregion

    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k).add(geoPoint.geometry.getEmission())  ;
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    private Ray constructRefractedRay(Point point, Ray ray, Vector n) {
        return new Ray(point, ray.getDir(), n);
    }

    private Ray constructReflectedRay(Point point, Ray ray, Vector n) {
        Vector v = ray.getDir();
        Vector r = reflectionRay(v, n);
        return new Ray(point, r, n);
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Double3 kkr = k.product(material.kR);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
            color = calcGlobalEffects(constructReflectedRay(gp.point, ray, n),
                                        level, material.kR, kkr);

        Double3 kkt = k.product(material.kT);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffects(constructRefractedRay(gp.point, ray, n), level, material.kT, kkt));
        return color;
    }

    private Color calcGlobalEffects(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray); // intersect the ray with the geometries
        return ray.findClosestGeoPoint(intersectionPoints);   // find the closest point - the point the ray 'sees' first
    }


    //region calcLocalEffects
    /**
     * calculate the effects of the lighting on the intensity of the point
     * @param geoPoint a GeoPoint object to calculate the effects on
     * @param ray the ray of the camera pointing to the GeoPoint's geometry
     * @return  the calculated color on the point in the geoPoint
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Vector v = ray.getDir ();                                   // the vec from the camera to the geometry
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);     // the normal to the geometry

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)                                                 // dot product resulted zero - the vectors are orthogonal
            return Color.BLACK;                                      // the camera doesn't see the light

        Material mat = geoPoint.geometry.getMaterial();
        Color color = Color.BLACK;                                  // the base color

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);            // vec from the lightSource to the geometry
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0 ) { // sign(nl) == sing(nv) ->
                // the camera and the light source are on the same side of the surface
                Double3 ktr = transparency(geoPoint, n, lightSource, l);

                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);    // the base intensity from the light source
                    color = color.add(
                            calcDiffusive(mat, l, n, lightIntensity),                    // add the diffusion effect
                            calcSpecular(mat, l, n, v, lightIntensity));     // add the specular effect
                }
            }
        }
        return color;
    }
    //endregion

    //region calcSpecular
    /**
     * the specular effect on the object according to the phong reflection model
     * @param mat the geometry's material
     * @param l vec from the light source to a point on the geometry
     * @param n normal vec to the point on the geometry
     * @param v vec from the camera to the geometry = the camera's eye
     * @param lightIntensity intensity of the light
     * @return calculated intensity with the specular effect
     */
    private Color calcSpecular(Material mat, Vector l, Vector n, Vector v, Color lightIntensity) {
        Vector r = reflectionRay(l, n);    // the specular ray

        // the phong model formula for the specular effect: ks ∙ ( 𝒎𝒂𝒙 (𝟎, −𝒗 ∙ 𝒓) ^ 𝒏𝒔𝒉 ) ∙ 𝑰
        return lightIntensity
                .scale(mat.kS.scale( alignZero( Math.pow( Math.max(0, v.scale(-1).dotProduct(r)),
                        mat.nShininess))));
    }

    //TODO change name
    private Vector reflectionRay(Vector l, Vector n) {
        return l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
    }
    //endregion

    //region calcDiffusive
    /**
     * the diffusion effect on the object according to the phong reflection model
     * @param mat the geometry's material
     * @param l vec from the light source to a point on the geometry
     * @param n vec from the light source to a point on the geometry
     * @param lightIntensity intensity of the light
     * @return calculated intensity with the diffusive effect
     */
    private Color calcDiffusive(Material mat, Vector l, Vector n, Color lightIntensity) {
        // the phong model formula for the diffusive effect: 𝒌𝑫 ∙| 𝒍 ∙ 𝒏 |∙ 𝑰
        return lightIntensity.scale(mat.kD.scale(Math.abs(n.dotProduct(l))));
    }
    //endregion
}
