package geometries;

import org.junit.jupiter.api.*;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Sphere
 *
 * @author Tamar Edri
 *
 */
class SphereTest {
    private Point p1;

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)} .
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(new Point(0, 0, 0), 5);
        assertEquals(new Vector(0, 0, 1), sp.getNormal(new Point(0, 0, 5)), "Bad normal to triangle");
    }

    @Test
    void testFindIntsersections() {
        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        // TC12: Ray starts at sphere and goes outside (0 points)

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        // TC14: Ray starts at sphere and goes inside (1 points)
        // TC15: Ray starts inside (1 points)
        // TC16: Ray starts at the center (1 points)
        // TC17: Ray starts at sphere and goes outside (0 points)
        // TC18: Ray starts after sphere (0 points)

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        // TC20: Ray starts at the tangent point
        // TC21: Ray starts after the tangent point

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line


    }
}