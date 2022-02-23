package primitives;

public class Vector extends Point{
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot construct the zero vector");

        xyz = normalize().xyz;
    }

    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot construct the zero vector");
    }

    public Vector add(Vector other){
        return new Vector(xyz.add(other.xyz));
    }

    //check scale by zero
    public Vector scale(double scalar){
        return new Vector(xyz.scale(scalar));
    }

    public double dotProduct(Vector other){
        Double3 product = xyz.product(other.xyz);
        return product.d1 + product.d2 + product.d3;
    }

    public Vector crossProduct(Vector other){
        return new Vector(xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2,
                          xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3,
                          xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1);
    }

    public double lengthSquared() {
        return distanceSquared(new Point(Double3.ZERO));
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize(){
        return this.scale(1/length());
    }
}
