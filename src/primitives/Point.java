package primitives;

public class Point {
    Double3 xyz;

    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    //check null
    public Vector subtract(Point other){
        return new Vector(xyz.subtract(other.xyz));
    }

    //check null
    public Point add(Vector v){
        return new Point(xyz.add(v.xyz));
    }

    //check null
    public double distanceSquared(Point other){
        return  (xyz.d1 - other.xyz.d1) * (xyz.d1 - other.xyz.d1) +
                (xyz.d2 - other.xyz.d2) * (xyz.d2 - other.xyz.d2) +
                (xyz.d3 - other.xyz.d3) * (xyz.d3 - other.xyz.d3);
    }

    //check null
    public double distance(Point other){
        return Math.sqrt(this.distanceSquared(other));
    }
}
