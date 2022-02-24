package primitives;

/**
 * a class that represents a 3 parameter Point in space
 */
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
    /**
     * Creating a Vector using 2 Points in the (מרחב?)
     *
     * @param other the other Point of the vector.
     *              according to linear calculations:
     *              this = the head of the Vector.
     *              other = the tail of the Vector
     */
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
    /**
     * the distance squared between to Points in space
     * according the math equation of how to calculate distance
     *
     * @param other the other Point to calculate distance to
     * @return a double that represents the distance squared
     */
    //check null
    public double distanceSquared(Point other){
        return  (xyz.d1 - other.xyz.d1) * (xyz.d1 - other.xyz.d1) +
                (xyz.d2 - other.xyz.d2) * (xyz.d2 - other.xyz.d2) +
                (xyz.d3 - other.xyz.d3) * (xyz.d3 - other.xyz.d3);
    }
    //endregion

    //region distance function
    /**
     * the distance between to Points in space
     * according the math equation of how to calculate distance
     *
     * @param other the other Point to calculate distance to
     * @return a double that represents the distance
     */
    //check null
    public double distance(Point other){
        return Math.sqrt(this.distanceSquared(other));
    }
    //endregion

    //region to string function override
    /**
     * format: "Point { xyz = (x, y, z) }"
     */
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
