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
    /**
     * true is the point is unshaded, false if the shape is shaded
     * @param geopoint
     * @param n normal to the geometry
     * @param lightSource the shaded light
     * @return shaded / unshaded
     */
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

    // region transparency
    /**
     *  the amount of shade covers the light in the requested point
     * @param geopoint
     * @param n normal to the geometry
     * @param lightSource the shaded light
     * @return the size pf the shade
     */
    private Double3 transparency(GeoPoint geopoint,  Vector n, LightSource lightSource /*, Vector l*/){
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
                return Double3.ZERO; // no transparency
        }
        return transparent;
    }
    //endregion

    //region calcColor - base function
    /**
     * calculate the color of a point infected by a light ray
     * @param geoPoint
     * @param ray the ray of intersection that 'hit' the point
     * @return  the color including all the effects
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity()        // the intensity of the ambient light
                .add( calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));  // recursive call
    }
    //endregion

    //region calcColor - recursive function
    /**
     *
     * @param geoPoint  calc color of this point
     * @param ray   that intersected the point
     * @param level the current depth of the recursion
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k).add(geoPoint.geometry.getEmission())  ;
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }
    //endregion

    //region secondary rays - reflection / refraction
    private Ray constructRefractedRay(Point point, Ray ray, Vector n) {
        return new Ray(point, ray.getDir(), n);
    }

    private Ray constructReflectedRay(Point point, Ray ray, Vector n) {
        Vector r = reflectionRay(ray.getDir(), n);
        return new Ray(point, r, n);
    }
    //endregion

    //region calcGlobalEffects - level
    /**
     * calculate the next level of the global effects
     * @param ray
     * @param level the current level
     * @param kx
     * @param kkx
     * @return the new calculated color
     */
    private Color calcGlobalEffects(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }
    //endregion

    //region calcGlobalEffects - k
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Double3 kkr = k.product(material.kR);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) // the color is effected by the reflection
            color = calcGlobalEffects(constructReflectedRay(gp.point, ray, n), level, material.kR, kkr);

        Double3 kkt = k.product(material.kT);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) // the color is effected due to the transparency
            color = color.add(calcGlobalEffects(constructRefractedRay(gp.point, ray, n), level, material.kT, kkt));

        return color;
    }
    //endregion

    //region findClosestIntersection
    /**
     * intersects the ray with the scene and finds the closest point the ray intersects
     * @param ray
     * @return the closest point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray); // intersect the ray with the geometries
        return ray.findClosestGeoPoint(intersectionPoints);   // find the closest point - the point the ray 'sees' first
    }
    //endregion

    //region calcLocalEffects
    /**
     * calculate the effects of the lighting on the intensity of the point
     * @param geoPoint a GeoPoint object to calculate the effects on
     * @param ray the ray of the camera pointing to the GeoPoint's geometry
     * @return  the calculated color on the point in the geoPoint
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Vector v = ray.getDir ();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;                                      // the camera doesn't see the light

        Material mat = geoPoint.geometry.getMaterial();
        Color color = Color.BLACK;

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);            // from the lightSource to the geometry
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0 ) { // sign(nl) == sing(nv) ->
                                // the camera and the light source are on the same side of the surface

                Double3 ktr = transparency(geoPoint, n, lightSource /*, l*/);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {  // the light is not shaded by other geometries
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(
                            calcDiffusive(mat, l, n, lightIntensity),
                            calcSpecular(mat, l, n, v, lightIntensity));
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
