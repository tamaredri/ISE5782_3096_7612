package geometries;

import org.junit.jupiter.api.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        assertEquals(tb.getNormal(new Point(2, 2, 10)).dotProduct(new Vector(0, 0, 1)), 0,
                "Bad normal to tube");

        // =============== Boundary Values Tests ==================
        // TC02: Normal is orthogonal to the head of the axis Ray
        assertEquals(tb.getNormal(new Point(2, 2, 0)).dotProduct(new Vector(0, 0, 1)), 0,
                "Bad normal to tube when the normal is orthogonal to the head of the axisRay");
    }

    @Test
    void testFindIntsersections() {
        Tube tube = new Tube(new Ray(new Point(-600,0,0), new Vector(1,0,0)), 200);

        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================

        // TC01: ray is parallel to axisRay and is inside the tube
        Ray ray = new Ray(new Point(-600,100,0), new Vector(1,0,0));
        assertNull(tube.findIntersections(ray), "ray is parallel to axisRay and is inside the tube- found an intersection");

        // TC02: ray is parallel to axisRay and is on the tube
        ray = new Ray(new Point(-200,-200,0), new Vector(1,0,0));
        assertNull(tube.findIntersections(ray), "ray is parallel to axisRay and is on the tube- found an intersection");

        // TC03: ray is parallel to axisRay and is outside the tube
        ray = new Ray(new Point(600,0,5), new Vector(1,0,0));
        assertNull(tube.findIntersections(ray), "ray is parallel to axisRay and is outside the tube- found an intersection");

        // TC04: ray is orthogonal to axisRay and intersects the tube
        ray = new Ray(new Point(0,600,0), new Vector(0,-1,0));
        List<Point> intersectionsList = tube.findIntersections(ray);
        assertEquals(2, intersectionsList.size(), "ray is orthogonal to axisRay and intersects the tube- found wrong number of intersection");
        if (intersectionsList.get(0).getY() > intersectionsList.get(1).getY())
            intersectionsList = List.of(intersectionsList.get(1), intersectionsList.get(0));
        assertEquals(new Point(0,-200,0), intersectionsList.get(0), "ray is orthogonal to axisRay and intersects the tube- found wrong intersection");
        assertEquals(new Point(0,200,0), intersectionsList.get(1), "ray is orthogonal to axisRay and intersects the tube- found wrong intersection");
    }
}