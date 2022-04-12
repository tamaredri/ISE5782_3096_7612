package primitives;

/**
 * representation of a material that a geometry can be made of
 */
public class Material {
    public double kD = 0;
    public double kS = 0;
    public int nShininess = 0;

    //region setters
    /**
     * set the diffusion factor
     * @param kD the factor
     * @return the material itself. builder pattern
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * set the speculation factor
     * @param kS the factor
     * @return the material itself. builder pattern
     */
    public Material setKs(double kS) {
        this.kS = kS;
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
    //endregion
}
