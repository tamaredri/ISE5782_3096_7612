package geometries;

import org.junit.jupiter.api.*;
import primitives.*;
import primitives.Vector;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.*;

/**
 * Testing Planes
 *
 * @author Tamar Edri
 *
 */
class PlaneTest {
    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct construction of a plane with different Points
        try {
            Plane p = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
            assertEquals(p.getNormal().length(), 1, "dir vector constructed is not normalized");
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }

        // =============== Boundary Values Tests ==================

        // TC02: Points generate the same direction vector
         assertThrows(IllegalArgumentException.class,
                 () -> new Plane(new Point(0, 0, 1), new Point(0, 0, 5), new Point(0, 0, 2)),
                 "Constructed a plane with two parallel vectors");

        // TC03: Co-located Points cannot generate s plane
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(3, 2, 1), new Point(3, 2, 1), new Point(3, 2, 1)),
                "Constructed a plane with two parallel vectors");

    }

    /**
     * Test method for {@link Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(), "Bad normal to plane");
    }

    /**
     * Test method for {@link Plane#findIntsersections(Ray)}.
     */
    @Test
    void testFindIntsersections() {
        Plane pl = new Plane (new Point(0, 0, 1), new Vector(0, 0, 1));
        List<Point> result;
        Ray r;
        Point p;

        // ============ Equivalence Partitions Tests ==============

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC01: Ray intersects the plane
        r = new Ray(new Point(0, 0, 2), new Vector(3, 0, -2));
        p = new Point(1.5, 0, 1);
        result = pl.findIntsersections(r);

        assertTrue(isZero(result.size() - 1), "Ray intersects the plane - wrong number of intersections");
        assertEquals(p, result.get(0), "Ray intersects the plane - wrong Point of intersection");

        // TC02: Ray does not intersect the plane
        r = new Ray(new Point(0, 0, 2), new Vector(1, 1, 1));
        result = pl.findIntsersections(r);
        assertNotEquals(result, null, "Ray does not intersect the plane - found an intersection");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC03: Ray included in the plane
        r = new Ray(new Point(1, 1, 1), new Vector(1, 1, 0));
        result = pl.findIntsersections(r);
        assertEquals(result, null, "Ray included in the plane - found and intersection");

        // TC04: Ray not included in the plane
        r = new Ray(new Point(1, 1, 2), new Vector(1, 1, 0));
        result = pl.findIntsersections(r);
        assertEquals(result, null, "Ray not included in the plane - found and intersection");


        // **** Group: Ray is orthogonal to the plane
        // TC05: Ray starts before the plane
        r = new Ray(new Point(1, 0, -1),new Vector(0, 0, 1));
        result = pl.findIntsersections(r);
        p = new Point(1, 0, 1);
        assertTrue(isZero(result.size() - 1), "Ray starts before the plane - didn't find an intersection");
        assertEquals(p, result.get(0), "Ray starts before the plane - wrong Point of intersection");


        // TC06: Ray starts on the plane
        r = new Ray(new Point(1, 0, 1),new Vector(0, 0, 1));
        result = pl.findIntsersections(r);
        assertEquals(result, null, "Ray starts on the plane - found and intersection");

        // TC07: Ray starts after the plane
        r = new Ray(new Point(1, 0, 2),new Vector(0, 0, 1));
        result = pl.findIntsersections(r);
        assertEquals(result, null, "Ray starts on the plane - found and intersection");


        // **** Group: Ray is not orthogonal nor parallel

        // TC08: Ray starts on the plane and not orthogonal nor parallel
        r = new Ray(new Point(1, 0, 1),new Vector(1, 0, -1));
        result = pl.findIntsersections(r);
        assertEquals(result, null, "Ray starts on the plane and not orthogonal nor parallel - found and intersection");

        // TC09: Ray starts on the reference point of the plane and not orthogonal nor parallel
        r = new Ray(new Point(0, 0, 1),new Vector(1, 0, -3));
        assertEquals(result, null, "Ray starts on the reference point of the plane and not orthogonal nor parallel - found and intersection");
    }
}