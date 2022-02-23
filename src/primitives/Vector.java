package primitives;

public class Vector extends Point{

    //region constructor 3 doubles
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot construct the zero vector");

        xyz = normalize().xyz;
    }
    //endregion

    //region constructor Double3
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot construct the zero vector");
    }
    //endregion

    //region add function
    public Vector add(Vector other){
        return new Vector(xyz.add(other.xyz));
    }
    //endregion

    //region scale function
    //check scale by zero
    public Vector scale(double scalar){
        return new Vector(xyz.scale(scalar));
    }
    //endregion

    //region dot product function
    public double dotProduct(Vector other){
        Double3 product = xyz.product(other.xyz);
        return product.d1 + product.d2 + product.d3;
    }
    //endregion

    //region cross product function
    public Vector crossProduct(Vector other){
        return new Vector(xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2,
                          xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3,
                          xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1);
    }
    //endregion

    //region length squared function
    public double lengthSquared() {
        return distanceSquared(new Point(Double3.ZERO));
    }
    //endregion

    //region length function
    public double length() {
        return Math.sqrt(lengthSquared());
    }
    //endregion

    //region normalize function
    public Vector normalize(){
        return this.scale(1/length());
    }
    //endregion

    //region to string override
    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz.toString() +
                '}';
    }
    //endregion
}
