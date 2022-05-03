package primitives;

import org.junit.jupiter.api.*;
import renderer.Camera;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.*;

/**
 * Testing Vector
 *
 * @author Yael Shamai
 *
 */
class VectorTest {

    /**
     * Test method for {@link Vector#Vector(double, double, double)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: only one regular test to check
        assertDoesNotThrow(() -> new Vector(1,2,3), "Does not construct a Vector");

        //=============== Boundary Values Tests ==================

        // TC01: Constructor of (0,0,0) vector throws an exception
        assertThrows(IllegalArgumentException.class, ()-> new Vector(0, 0, 0),
        "Constructed (0,0,0) vector");
    }

    /**
     * Test method for {@link Vector#add(Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Add two regular vector one to another
        assertEquals(new Vector(1,2,3).add(new Vector(2,3,4)), new Vector(3,5,7),
                "Add function does not return the right vector");
    }

    /**
     * Test method for {@link Vector#scale(double)}
     */
    @Test
    void testScale() {
        //=============== Boundary Values Tests ==================
        //TC01: scale a vector by 0 throws an exception
        assertThrows(IllegalArgumentException.class, ()->new Vector(1,3,6).scale(0),
                "Scale a vector by zero does not throw an exception");

        // ============ Equivalence Partitions Tests ==============
        //TC02: scale a vector by a regular scalar
        assertEquals(new Vector(1, 1, 1).scale(2), new Vector(2, 2, 2),
                "scale does not return the right vector");
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct calculation of dotProduct() of orthogonal vectors
        assertTrue(isZero(new Vector(1, 2, 3).dotProduct(new Vector(0, 3, -2))), "ERROR: dotProduct() for orthogonal vectors is not zero");


        // TC02: Correct calculation of dotProduct() of 2 regular vectors
        assertTrue(isZero(new Vector(1, 2, 3).dotProduct(new Vector(-2, -4, -6)) + 28), "ERROR: dotProduct() wrong value");

    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // ============ Equivalence Partitions Tests ==============

        // TC01: crossProduct() for two parallel vectors throws an exception
        assertThrows(IllegalArgumentException.class, ()-> v1.crossProduct(v2),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");

        // TC02: correct crossProduct() length
        assertTrue(isZero(vr.length() - v1.length() * v3.length()), "ERROR: crossProduct() wrong result length");

        // TC03: correct crossProduct() orthogonal to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "ERROR: crossProduct() result is not orthogonal to its operands");

        assertTrue(isZero(vr.dotProduct(v3)), "ERROR: crossProduct() result is not orthogonal to its operands");

    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct calculation of a vectors length
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5), "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct calculation of a vectors length
        assertTrue(isZero(new Vector(1, 2, 3).lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();

        // TC01: test vector normalization vs vector length and cross-product
        assertTrue(isZero(u.length() - 1), "ERROR: the normalized vector is not a unit vector");

        // TC02: test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, ()-> v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");

        // TC03: test that the vectors are not in opposite directions
        assertFalse(v.dotProduct(u) < 0, "ERROR: the normalized vector is opposite to the original one");
    }

    @Test
    void testMoveClockwiseAround() {
        Vector v1 = new Vector (0,0,-1);
        Vector v2 = new Vector (0,-1,0);
        //============== Equivalence Partitions Tests ==============

        // EP02: angle is 90 degrees
        assertEquals(new Vector(-1, 0, 0), v1.moveClockwiseAround(v2,90), "move v1 90 degrees around v2 test failed");

        // EP04: angle is more than 360 degrees
        assertEquals(new Vector(-1, 0, 0), v1.moveClockwiseAround(v2,450), "move v1 450 degrees around v2 test failed");

        // EP05: angle is negative
        assertEquals(new Vector(-1, 0, 0),  v1.moveClockwiseAround(v2,-270), "angle is negative- didn't move vector correctly");

        // EP06: angle is ZERO
        assertEquals(new Vector(0, 0, -1),  v1.moveClockwiseAround(v2,0), "angle is ZERO- the vector moved");

        // EP06: angle is 45 degrees
        assertEquals(new Vector(-1,0,-1).normalize(),  v1.moveClockwiseAround(v2,45), "angle is 45 test failed");
    }
}