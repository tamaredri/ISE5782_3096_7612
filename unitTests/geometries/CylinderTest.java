package geometries;

import org.junit.jupiter.api.*;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Cylinder
 *
 * @author Tamar Edri
 *
 */
class CylinderTest {
    /**
     * Test method for {@link geometries.Cylinder#Cylinder(Ray, double, double)}.
     */
    @Test
    void testGetNormal() {
        Cylinder cy = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 0, 3)), 2, 3);

        // ============ Equivalence Partitions Tests ==============

        // TC01: normal for a point on the casing of the cylinder
        assertEquals(cy.getNormal(new Point(2, 0, 1)), new Vector(1, 0, 0), "Bad normal for a point on the cylinder");

        // TC02: normal for a point on the base of the cylinder where the axis ray starts
        assertEquals(cy.getNormal(new Point(1, 1, 0)), new Vector(0, 0, -1), "Bad normal for a point on the base of the cylinder where the axis ray starts");

        // TC03: normal for a point on the parallel base of the cylinder where the axis ray starts
        assertEquals(cy.getNormal(new Point(1, 1, 3)), new Vector(0, 0, 1), "Bad normal for a point on the parallel base of the cylinder where the axis ray starts");

        // TC04: test for a normalized vector
        assertEquals(cy.getNormal(new Point(2, 0, 1)).length(), 1, "normal vector is not normalized");

        // =============== Boundary Values Tests ==================

        // TC01: normal to a point on the corner between the casing and the base of the cylinder
        assertEquals(cy.getNormal(new Point(2, 0, 0)), new Vector(0, 0, -1),
                "Bad normal for a point on the the corner between the casing and the base of the cylinder");

        // TC02: normal to the point that represents the ray
        assertEquals(cy.getNormal(new Point(0, 0, 0)), new Vector(0, 0, -1),
                "Bad normal for a point on the the corner between the casing and the base of the cylinder");
    }
}