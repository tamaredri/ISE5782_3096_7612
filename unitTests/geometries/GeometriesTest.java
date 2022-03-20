package geometries;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
import primitives.Vector;
import java.util.*;


class GeometriesTest {
    /**
     * Test method for {@link Geometries#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Sphere sp = new Sphere(new Point(2,0,0), 2);
        Plane p = new Plane(new Point(1,1,0.5), new Vector(0, 0, 1));
        Triangle tr = new Triangle(new Point(0, 1, 0), new Point(0, -1, 0), new Point(3,0,0));
        Geometries geometries = new Geometries(sp, p, tr);
        List<Point> expected = new LinkedList<>();

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects only sphere and plane (3 points)
        Ray r = new Ray(new Point(1,1,-2), new Vector(0,0,1));
        assertEquals(3, geometries.findIntersections(r).size(), "Ray intersects only sphere and plane (3 points)- wrong points of intersection");


        // =============== Boundary Values Tests ==================

        // TC02: Ray intersects all the geometries (4 points)
        r = new Ray(new Point(0.5, 0.5, -2), new Vector(0, 0, 1));
        assertEquals(4, geometries.findIntersections(r).size(), "Ray intersects all the geometries (4 points) - wrong points of intersection");

        // TC03: Ray intersects one the geometries (1 points)
        r = new Ray(new Point(5, 0, -2), new Vector(0, 0, 1));
        assertEquals(1, geometries.findIntersections(r).size(), "Ray intersects one the geometries (1 points) - wrong points of intersection");

        // TC04: Ray doesn't intersect any geometries (0 points)
        r = new Ray(new Point(2.5,-5.5,-2), new Vector(6.5,10.5,-3));
        assertNull(geometries.findIntersections(r),
                "Ray doesn't intersect any geometries (0 points) - found a point of intersection");

        // TC05: Empty collection of geometries
        geometries = new Geometries();
        assertNull(geometries.findIntersections(r), "Empty collection of geometries - found an intersection");

    }
}