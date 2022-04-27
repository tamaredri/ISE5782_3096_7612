package geometries;

import org.junit.jupiter.api.*;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Polygons
 *
 * @author Dan
 *
 */
class PolygonTest {

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertex on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertices on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertices on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to triangle");
    }

    @Test
    void testFindIntsersections() {
        Polygon polygon = new Polygon(new Point(0,-2,0), new Point(0,1,0),
                                      new Point(1,1,1), new Point(3,-2,3));
        // ============ Equivalence Partitions Tests ==============
        // TC01: ray intersects inside the polygon.
        Ray ray = new Ray(new Point(0,0,2), new Vector(0.5, -0.8, -1.5));
        Point p = new Point(0.5, -0.8, 0.5);
        assertEquals(p, polygon.findIntersections(ray).get(0),"ray intersects inside the polygon- wrong intersection" );

        //TC02: ray doesn't intersect the polygon, point is against the side of the polygon.
        ray = new Ray (new Point(0,0,2), new Vector(2,-3,0));
        assertNull(polygon.findIntersections(ray), "didn't return null- ray doesn't intersect the polygon, point is against the side of the polygon.");

        //TC03: ray doesn't intersect the polygon, point is against the vertex of the polygon.
        ray = new Ray(new Point(0,0,2), new Vector(-3,-4,-5));
        assertNull(polygon.findIntersections(ray), "didn't return null- ray doesn't intersect the polygon, point is against the vertex of the polygon.");

        // =============== Boundary Values Tests ==================
        //TC04: ray doesn't intersect the polygon, point on a vertex of the polygon.
        ray = new Ray(new Point(0,0,2), new Vector(0,1,-2));
        assertNull(polygon.findIntersections(ray), "didn't return null- ray doesn't intersect the polygon, point on a vertex of the polygon.");

        //TC05: ray doesn't intersect the polygon, point on a side of the polygon.
        ray = new Ray(new Point(0,0,2), new Vector(0.5, 1, -1.5));
        assertNull(polygon.findIntersections(ray), "didn't return null- ray doesn't intersect the polygon, point on a side of the polygon.");

        //TC06: ray doesn't intersect the polygon, point is on the continuation of a side of the polygon.
        ray = new Ray(new Point(0,0,2), new Vector(0,2,-2));
        assertNull(polygon.findIntersections(ray), "didn't return null- ray doesn't intersect the polygon, point is on the continuation of a side of the polygon.");

        //may have to add more tests depending on the implementation algorithm.
        //TC06: ray doesn't intersect the polygon, point is on the continuation of a side of the polygon.
        polygon = new Polygon(new Point(0,-2,0), new Point(0,1,0), new Point(3,-2,3));
        ray = new Ray(new Point(0,0,2), new Vector(0.5,-0.8,-1.5));
        p = new Point(0.5, -0.8, 0.5);
        assertEquals(p, polygon.findIntersections(ray).get(0),"ray intersects inside the polygon- wrong intersection" );
    }
}