package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.*;
import static primitives.Util.isZero;

/**
 *  implementation of the abstract class RayTracerBase
 *  adding the local and global effects of the objects presented in the scene
 */
public class RayTracerBasic extends RayTracerBase{
    private static final int MAX_CALC_COLOR_LEVEL = 3;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = new Double3(1.0);

    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    //region traceRay
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint gp = scene.geometries.findClosestIntersection(ray); // intersect the ray with the geometries
        if (gp == null)     // no intersection was found
            return this.scene.background;   // the background color
        return calcColor(gp, ray);                                   // return the calculated color of this point
    }
    //endregion

    // region transparency
    /**
     *  calculate the amount of shade covering the point
     * @param geopoint a point to check the shadow on
     * @param n normal to the geometry
     * @param lightSource the shading light
     * @return the amount of the shade
     */
    private Double3 transparency(GeoPoint geopoint,  Vector n, LightSource lightSource){
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
     * cover function for the recursive function that calculates the color
     * @param geoPoint calculate the color of this point
     * @param ray the ray of intersection that 'hit' the point
     * @return  the color including all the effects
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity()               // the intensity of the ambient light
                .add(calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));  // recursive call
    }
    //endregion

    //region calcColor - recursive function
    /**
     * calculating the local (diffuse + specular) and the global (reflection + refraction) effects
     * @param geoPoint  to calculate its color
     * @param ray that intersected the point
     * @param level the depth of the recursion for the global effects
     * @param k the volume of the color
     * @return the color of the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray, k)
                        .add(geoPoint.geometry.getEmission());
        return 1 == level ? color :
                            color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }
    //endregion

    //region calcGlobalEffects - level
    /**
     * calculate the next level of the global effects if there are more intersections to check
     * @param ray the is used to intersect the geometries
     * @param level the current level
     * @param kx a color factor to reduce the color (according to the current level of recursion)
     * @param kkx the color factor for the next level of recursion
     * @return the new calculated color
     */
    private Color calcGlobalEffects(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = scene.geometries.findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }
    //endregion

    //region calcGlobalEffects - k
    /**
     * calculate the color according to the k factor for the reflection and refraction effects
     * @param gp calculate the color of this point
     * @param ray the ray of intersection that 'hit' the point
     * @param level of the recursion
     * @param k the volume of the color
     * @return the calculated color
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Double3 kkr = k.product(material.kR);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) { // the color is effected by the reflection
            Ray ray1 = constructReflectedRay(gp.point, ray, n);
            double glossiness = material.glossiness;

            if (!isGlossy(gp)) // glossiness = glossy reflection
                color = calcGlobalEffects(ray1, level, material.kR, kkr);
            else {
                RayBeam rayBeam = new RayBeam(ray1, glossiness, glossiness);
                List<Ray> rayList = rayBeam.constructRayBeam();
                int beamSize = rayList.size();

                for (Ray r : rayList) {
                    //if((alignZero(n.dotProduct(r.getDir())) > 0)) // the ray has to be in the normal direction to be reflected correctly
                        color = color.add(calcGlobalEffects(r, level, material.kR, kkr));
                    //else
                    //    beamSize--;
                }
                color = color.reduce(beamSize);
            }
        }


        Double3 kkt = k.product(material.kT);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {// the color is effected due to the transparency
            Ray ray1 = constructRefractedRay(gp.point, ray, n);
            double diffuseness = material.diffuseness;

            if (!isDiffusive(gp)) // diffuseness = diffusive refraction
                color = calcGlobalEffects(ray1, level, material.kT, kkt);
            else {
                RayBeam rayBeam = new RayBeam(ray1, diffuseness, diffuseness);
                List<Ray> rayList = rayBeam.constructRayBeam();
                int beamSize = rayList.size();
                for (Ray r : rayList) {
                    //if(alignZero(n.dotProduct(r.getDir())) < 0) // the ray has to be in the opposite direction of the normal refracted correctly
                        color = color.add(calcGlobalEffects(r, level, material.kT, kkt));
                    //else
                    //    beamSize--;
                }
                color = color.reduce(beamSize); // average the color
            }
        }

        return color;
    }

    private boolean isDiffusive(GeoPoint gp) {
        return !isZero(gp.geometry.getMaterial().diffuseness);
    }

    private boolean isGlossy(GeoPoint gp) {
        return !isZero(gp.geometry.getMaterial().glossiness);
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

                Double3 ktr = transparency(geoPoint, n, lightSource);
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
        Vector r = reflectionVector(l, n);    // the specular ray
        // the phong model formula for the specular effect: ks ∙ ( 𝒎𝒂𝒙 (𝟎, −𝒗 ∙ 𝒓) ^ 𝒏𝒔𝒉 ) ∙ 𝑰
        return lightIntensity
                .scale(mat.kS.scale( alignZero( Math.pow( Math.max(0, v.scale(-1).dotProduct(r)),
                        mat.nShininess))));
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

    //region secondary rays - reflection / refraction

    /**
     * construct the refraction ray according to the physics law of refraction
     * @param point reference point of the new ray
     * @param ray the ray to refract
     * @param n the normal to the geometry the point belongs to
     * @return the refraction ray
     */
    private Ray constructRefractedRay(Point point, Ray ray, Vector n) {
        return new Ray(point, ray.getDir(), n);
    }

    /**
     * construct the reflection ray according to the physics law of reflection
     * @param point reference point of the new ray
     * @param ray the ray to reflect
     * @param n the normal to the geometry the point belongs to
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Point point, Ray ray, Vector n) {
        return new Ray(point, reflectionVector(ray.getDir(), n), n);
    }
    //endregion

    //region reflectionVector
    /**
     * calculate the reflected vector using linear algebra and projection on a normal vector
     * @param l the vector to reflect
     * @param n the normal vector to reflect by
     * @return the reflected vector
     */
    private Vector reflectionVector(Vector l, Vector n) {
        return l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
    }
    //endregion
}
