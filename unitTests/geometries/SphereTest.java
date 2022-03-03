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
}