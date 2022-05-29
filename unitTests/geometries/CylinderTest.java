package geometries;

import org.junit.jupiter.api.*;
import primitives.*;

import java.util.List;

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
        assertEquals(cy.getNormal(new Point(2, 0, 1)), new Vector(1, 0, 0),
                "Bad normal for a point on the cylinder");

        // TC02: normal for a point on the base of the cylinder where the axis ray starts
        assertEquals(cy.getNormal(new Point(1, 1, 0)), new Vector(0, 0, -1),
                "Bad normal for a point on the base of the cylinder where the axis ray starts");

        // TC03: normal for a point on the parallel base of the cylinder where the axis ray starts
        assertEquals(cy.getNormal(new Point(1, 1, 3)), new Vector(0, 0, 1),
                "Bad normal for a point on the parallel base of the cylinder where the axis ray starts");

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

    @Test
    void testFindIntsersections() {
        Cylinder cylinder = new Cylinder(new Ray(new Point(-600,0,0), new Vector(1,0,0)), 200, 1400);

        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================

        // TC01: ray is parallel to axisRay and is inside the cylinder
        Ray ray = new Ray(new Point(-600,100,0), new Vector(1,0,0));
        assertNull(cylinder.findIntersections(ray), "ray is parallel to axisRay and is inside the cylinder- found an intersection");

        // TC02: ray is parallel to axisRay and is on the cylinder
        ray = new Ray(new Point(-200,-200,0), new Vector(1,0,0));
        assertNull(cylinder.findIntersections(ray), "ray is parallel to axisRay and is on the cylinder- found an intersection");

        // TC03: ray is parallel to axisRay and is outside the cylinder
        ray = new Ray(new Point(600,0,5), new Vector(1,0,0));
        assertNull(cylinder.findIntersections(ray), "ray is parallel to axisRay and is outside the cylinder- found an intersection");

        // TC04: ray is orthogonal to axisRay and intersects the cylinder
        ray = new Ray(new Point(0,600,0), new Vector(0,-1,0));
        assertEquals(2, cylinder.findIntersections(ray).size(), "ray is orthogonal to axisRay and intersects the cylinder- found wrong number of intersection");

        // TC05: ray is orthogonal to axisRay and does not intersects the cylinder
        ray = new Ray(new Point(-800,0,0), new Vector(0,-1,0));
        assertNull(cylinder.findIntersections(ray), "ray is orthogonal to axisRay and intersects the cylinder- found wrong number of intersection");
    }
}