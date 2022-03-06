package primitives;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.*;

/**
 * Unit tests for primitives.Point class
 * @author Tamar Edri
 */
class PointTest {
    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
     void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct subtraction between 2 points
        Point p1 = new Point(1, 2, 3);
        assertEquals(new Vector(1, 1, 1),
                     new Point(2, 3, 4).subtract(p1),
                    "ERROR: Point - Point does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct addition between point and vector
        Point p1 = new Point(1, 2, 3);
        assertEquals (p1.add(new Vector(-1, -2, -3)),
                      new Point(0, 0, 0),
                     "ERROR: Point + Vector does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: a regular distance squared
        assertEquals(new Point(1, 0, 0).distanceSquared(new Point(3, 0,0)), 4,
                "wrong calculation of distanceSquared() in Point");

        // TC02: Co-located points
        assertEquals(isZero(new Point(1, 2, 3).distanceSquared(new Point(1, 2, 3))), true,
                "wrong calculation of distanceSquared() for co-located points");
    }

    /**
     * Test method for {@link primitives.Point#distance(Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: a regular distance
        assertEquals(new Point(1, 0, 0).distance(new Point(3, 0,0)), 2,
                "wrong calculation of distance() in Point");

        // TC02: Co-located points
        assertEquals(isZero(new Point(1, 2, 3).distance(new Point(1, 2, 3))), true,
                "wrong calculation of distanceSquared() for co-located points");

    }
}