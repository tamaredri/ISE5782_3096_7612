package geometries;

import org.junit.jupiter.api.*;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
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
     * Test method for {@link Plane#getNormal()}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(), "Bad normal to plane");
    }
}