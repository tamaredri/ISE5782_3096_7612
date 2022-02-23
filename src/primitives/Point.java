package primitives;

import java.util.Objects;

public class Point {
    Double3 xyz;

    //region constructor 3 doubles
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }
    //endregion

    //region constructor Double3
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }
    //endregion

    //region subtract function
    //check null
    public Vector subtract(Point other){
        return new Vector(xyz.subtract(other.xyz));
    }
    //endregion

    //region add function
    //check null
    /**
     * Adds to the point the dir vector
     *
     * @param v direction vector to move the point
     */
    public Point add(Vector v){
        return new Point(xyz.add(v.xyz));
    }
    //endregion

    //region distance squared function
    //check null
    public double distanceSquared(Point other){
        return  (xyz.d1 - other.xyz.d1) * (xyz.d1 - other.xyz.d1) +
                (xyz.d2 - other.xyz.d2) * (xyz.d2 - other.xyz.d2) +
                (xyz.d3 - other.xyz.d3) * (xyz.d3 - other.xyz.d3);
    }
    //endregion

    //region distance function
    //check null
    public double distance(Point other){
        return Math.sqrt(this.distanceSquared(other));
    }
    //endregion

    //region to string function override
    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz.toString() +
                '}';
    }
    //endregion

    //region equals function override
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }
    //endregion

}
