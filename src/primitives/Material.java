package primitives;

import geometries.Intersectable;

import static primitives.Util.isZero;

/**
 * representation of a material that a geometry can be made of
 */
public class Material {
    public Double3 kD = new Double3(0);
    public Double3 kS = new Double3(0);
    public int nShininess = 0;
    /**
     * transparency factor
     */
    public Double3 kT = new Double3(0);
    /**
     * reflection factor
     */
    public Double3 kR = new Double3(0);

    public double glossiness = 0;
    public double diffuseness = 0;
    //region setters
    /**
     * set the diffusion factor
     * @param kD the factor
     * @return the material itself. builder pattern
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setGlossiness(double glossiness) {
        this.glossiness = glossiness;
        return this;
    }

    public Material setDiffuseness(double diffuseness) {
        this.diffuseness = diffuseness;
        return this;
    }

    /**
     * set the speculation factor
     * @param kS the factor
     * @return the material itself. builder pattern
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * set the shininess factor
     * @param nShininess the factor
     * @return the material itself. builder pattern
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
    //endregion

    public boolean isDiffusive() {
        return !isZero(diffuseness);
    }

    public boolean isGlossy() {
        return !isZero(glossiness);
    }


}
