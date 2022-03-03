package primitives;

/**
 * a class that represents an infinite Ray in space
 * using a Point for the location in space and a direction Vector
 */
public class Ray {
    private final Point p0;
    private final Vector dir;

    //region constructor
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }
    //endregion

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }


    //region to string override
    /**
     * format: " Ray { p0 = Point : xyz = {x, y, z)}, dir = Vector : xyz = (x, y, z) } "
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
    //endregion

    //region equals function override
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir) ;
    }
    //endregion
}
