package geometries;

import org.junit.jupiter.api.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Tube
 *
 * @author Tamar Edri
 *
 */
class TubeTest {
    /**
     * Test method for {@link geometries.Tube#Tube(Ray, double)}.
     */
    @Test
    void testGetNormal() {
        Tube tb = new Tube(
                new Ray(
                        new Point(0, 0, 0),
                        new Vector(0, 0, 1)),
                2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        assertEquals(tb.getNormal(new Point(2, 2, 10)).dotProduct(new Vector(0, 0, 1)), 0, "Bad normal to tube");

        // =============== Boundary Values Tests ==================
        // TC02: Normal is orthogonal to the head of the axis Ray
        assertEquals(tb.getNormal(new Point(2, 2, 0)).dotProduct(new Vector(0, 0, 1)), 0, "Bad normal to tube");
    }
}