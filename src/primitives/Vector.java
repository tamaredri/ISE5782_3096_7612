package primitives;

/**
 * a class that represents a Vector in space.
 */
public class Vector extends Point{

    //region constructor 3 doubles
    public Vector(double x, double y, double z) throws IllegalArgumentException {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot construct the zero vector");
    }
    //endregion

    //region constructor Double3
    public Vector(Double3 xyz) throws IllegalArgumentException {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot construct the zero vector");
    }
    //endregion

    //region getter for xyz
    public Point getXYZ(){
        return new Point(this.xyz);
    }
    //endregion

    //region add function
    /**
     * add a Vector to another Vector.
     * generating a new Vector using the linear calculation method of how to add two Vectors
     *
     * @param other the other Vector to add
     * @return new Vector in the combined direction
     */
    @Override
    public Vector add(Vector other) throws IllegalArgumentException {
        return new Vector(xyz.add(other.xyz));
    }
    //endregion

    //region scale function
    /**
     * scaling a Vector
     *
     * @param scalar double number to scale the vector
     * @return the scaled Vector
     */
    //check scale by zero
    public Vector scale(double scalar) throws IllegalArgumentException {
        return new Vector(xyz.scale(scalar));
    }
    //endregion

    //region dot product function
    /**
     * the dot-product between two Vectors in space
     * according the math equation of how to calculate dot-product
     *
     * @param other the other Vector to calculate  the dot-product
     * @return the dot-product result (a double)
     */
    public double dotProduct(Vector other){
        Double3 product = xyz.product(other.xyz);
        return product.d1 + product.d2 + product.d3;
    }
    //endregion

    //region cross product function
    /**
     * the cross-product between two Vectors in space
     * according the math equation of how to calculate cross-product
     *
     * @param other the other Point to calculate the cross-product
     * @return the result of the cross-product (a Vector)
     */
    public Vector crossProduct(Vector other) throws IllegalArgumentException {
        return new Vector(xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2,
                          xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3,
                          xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1);
    }
    //endregion

    //region length squared function
    /**
     * the length-squared of a Vector in space
     * according the math equation of how to calculate length-squared of a Vector
     *
     * @return a double that represents the length-squared of the vector
     */
    public double lengthSquared() {
        return distanceSquared(new Point(Double3.ZERO));
    }
    //endregion

    //region length function
    /**
     * the length of a Vector in space
     * according the math equation of how to calculate length
     *
     * @return a double that represents the length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }
    //endregion

    //region normalize function
    /**
     * normalizing a vector.
     * changing the length of the vector to be only 1
     * according the math equation of how to normalize a Vector
     *
     * @return the scaled Vector
     */
    public Vector normalize(){
        return this.scale(1/length());
    }
    //endregion

    //region to string override
    /**
     * format: " Vector { xyz = (x, y, z) } "
     */
    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz.toString() +
                '}';
    }
    //endregion
}
