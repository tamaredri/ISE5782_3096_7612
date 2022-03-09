package geometries;

import org.junit.jupiter.api.*;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Triangle
 *
 * @author Tamar Edri
 *
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#Triangle(Point, Point, Point)} .
     */
    @Test
    void constructorTest(){
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct creation of a triangle
        try {
            new Triangle(new Point(-5, 2, 0), new Point(5, 2, 3), new Point(0, 4, 3));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct triangle");
        }

        // =============== Boundary Values Tests ==================

        // TC02: Vertex on a same line
        assertThrows(IllegalArgumentException.class, //
                () -> new Triangle(new Point(0, 0, 1), new Point(0, 0, 0), new Point(0, 0, 5)),
                "Constructed a triangle with vertex on a single line");

        // TC03: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a triangle with vertices on a side");

        // TC045: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a triangle with vertices on a side");

    }

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), tr.getNormal(new Point(0, 0, 1)), "Bad normal to triangle");
    }

    @Test
    void testFindIntsersections() {

        // ============ Equivalence Partitions Tests ==============
        Triangle tr = new Triangle(new Point(1, 0, 0), new Point(0, 1,0 ), new Point(-1, 0, 0));
        Ray r;
        Point p;
        List<Point> result;

        // TC01: Ray intersects inside the triangle
        r = new Ray(new Point(0.5, 0.5, -1), new Vector(0, 0, 1));
        p = new Point(0.5, 0.5, 0);
        result = tr.findIntsersections(r);
        assertTrue(isZero(result.size() - 1), "Ray intersects inside the triangle - wrong number of intersections");
        assertEquals(p, result.get(0), "Ray intersects inside the triangle - wrong Point of intersection");

        // TC02: Ray outside the triangle against edge
        r = new Ray(new Point(8, 8, -1), new Vector(0, 0, 1));
        result = tr.findIntsersections(r);
        assertEquals(p, result.get(0), "Ray intersects inside the triangle - wrong Point of intersection");

        // TC03: Ray outside the triangle against vertex
        r = new Ray(new Point(10, 0.5, 0), new Vector(0, 0, 1));
        result = tr.findIntsersections(r);
        assertEquals(p, result.get(0), "Ray intersects inside the triangle - wrong Point of intersection");

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