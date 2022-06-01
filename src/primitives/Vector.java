package primitives;

import org.ejml.data.SimpleMatrix;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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

    //region add
    /**
     * add a Vector to another Vector.
     * generating a new Vector using the linear calculation method of how to add two Vectors
     * @param other the other Vector to add
     * @return new Vector in the combined direction
     */
    @Override
    public Vector add(Vector other) throws IllegalArgumentException {
        return new Vector(xyz.add(other.xyz));
    }
    //endregion

    //region scale
    /**
     * scales a Vector
     * @param scalar double number to scale the vector
     * @throws IllegalArgumentException when trying to scale with zero
     * @return the Vector scaled
     */
    public Vector scale(double scalar) throws IllegalArgumentException {
            return new Vector(xyz.scale(scalar));
    }
    //endregion

    //region dot product function
    /**
     * the dot-product between two Vectors in space
     * according the math equation of how to calculate dot-product
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
     * @param other the other Point to calculate the cross-product
     * @throws IllegalArgumentException when the vectors are parallel. trying to construct the zero vector
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
     * @return the scaled Vector
     */
    public Vector normalize(){
        return new Vector(xyz.reduce(length()));
    }
    //endregion

    //region getOrthogonal
    public Vector getOrthogonal(){
        Vector orthogonal;
        if(!isZero(this.getX()) || !isZero(this.getY()))
            orthogonal = new Vector(this.getY(),-this.getX(), 0);
        else
            orthogonal = new Vector(0,-this.getZ(), this.getY());
        return orthogonal;
    }
    //endregion

    //region moveClockwiseAround - rotate a vector
    /**
     * rotating a vector around a requested vector, in an angle requested
     * using the rotation matrix method
     * the calling vector is the vector that will be moved due to the rotation
     * @param rotationVector the vector the space will rotate around, orthogonal to the calling vector
     * @param angle the size of the rotation - angle in degrees
     * @return vectorToMove normalized and rotated to the left with the requested angle
     */
    public Vector moveClockwiseAround(Vector rotationVector, double angle) {
        if (!isZero(this.dotProduct(rotationVector)))
            throw new IllegalArgumentException("the vectors are not orthogonal");

        // convert the angle to radians
        angle = Math.toRadians(angle);
        // the third vector to span the space to rotate
        Vector orthogonalVector = this.crossProduct(rotationVector);

        SimpleMatrix matrixP = new SimpleMatrix(3,3);
        // fill the P matrix -
        // the conversion matrix between the standard base (E) to our base (F)
        matrixP.set(0,0, rotationVector.getX());
        matrixP.set(1,0, rotationVector.getY());
        matrixP.set(2,0, rotationVector.getZ());
        matrixP.set(0,1, this.getX());
        matrixP.set(1,1, this.getY());
        matrixP.set(2,1, this.getZ());
        matrixP.set(0,2, orthogonalVector.getX());
        matrixP.set(1,2, orthogonalVector.getY());
        matrixP.set(2,2, orthogonalVector.getZ());

        SimpleMatrix matrixA = new SimpleMatrix(3,3);
        // fill the A matrix -
        // the copy matrix in our base (F)
        matrixA.set(0,0, 1);
        matrixA.set(1,0, 0);
        matrixA.set(2,0, 0);
        matrixA.set(0,1, 0);
        matrixA.set(1,1, alignZero(Math.cos(angle)));
        matrixA.set(2,1, alignZero(Math.sin(angle)));
        matrixA.set(0,2, 0);
        matrixA.set(1,2, -alignZero(Math.sin(angle)));
        matrixA.set(2,2, alignZero(Math.cos(angle)));

        // the invertible matrix -
        // the conversion matrix on the opposite way from matrixP,
        // from our base (F) to the standard base (E)
        SimpleMatrix matrixInvertP = matrixP.invert();

        // the full copy matrix from the standard base (E) to the standard base (E)
        SimpleMatrix copyMatrix = matrixP.mult(matrixA).mult(matrixInvertP);

        // convert the vector that is going to be copied from a vector to a matrix ot execute the matrix multiplication
        SimpleMatrix matrixVectorToMove = new SimpleMatrix(3,1);
        matrixVectorToMove.set(0,0,this.getX());
        matrixVectorToMove.set(1,0,this.getY());
        matrixVectorToMove.set(2,0,this.getZ());

        // the converted vector in a form of a matrix
        matrixVectorToMove = copyMatrix.mult(matrixVectorToMove);

        // convert the matrix to a vector
        return new Vector(matrixVectorToMove.get(0,0),
                          matrixVectorToMove.get(1,0),
                          matrixVectorToMove.get(2,0)).normalize();
    }
    //endregion

    //region toString override
    /**
     * format: " Vector { xyz = (x, y, z) } "
     */
    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }
    //endregion
}
