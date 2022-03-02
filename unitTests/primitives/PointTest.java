package primitives;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

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
        // TC01: Correct addition between point and vector
        Point p1 = new Point(1, 2, 3);
        assertEquals (p1.add(new Vector(-1, -2, -3)),
                      new Point(0, 0, 0),
                     "ERROR: Point + Vector does not work correctly");
    }
}