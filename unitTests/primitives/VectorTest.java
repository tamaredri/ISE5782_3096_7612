package primitives;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.*;

/**
 * Testing Polygons
 *
 * @author Yael Shamai
 *
 */
class VectorTest {

    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Constructor of (0,0,0) vector throws an exception
        assertThrows(IllegalArgumentException.class, ()-> new Vector(0, 0, 0),
        "Constructed (0,0,0) vector");
    }

    @Test
    void testAdd() {
    }

    @Test
    void testScale() {
    }

    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct calculation of dotProduct() of orthogonal vectors
        assertEquals(isZero(new Vector(1, 2, 3).dotProduct(new Vector(0, 3, -2))),
                 true, "ERROR: dotProduct() for orthogonal vectors is not zero");


        // TC02: Correct calculation of dotProduct() of 2 regular vectors
        assertEquals(isZero(new Vector(1, 2, 3).dotProduct(new Vector(-2, -4, -6)) + 28),
                true,"ERROR: dotProduct() wrong value");

    }

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
        assertEquals(isZero(vr.length() - v1.length() * v3.length()), true,
                "ERROR: crossProduct() wrong result length");

        // TC03: correct crossProduct() orthogonal to its operands
        assertEquals(isZero(vr.dotProduct(v1)), true,
                "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(isZero(vr.dotProduct(v3)), true,
                "ERROR: crossProduct() result is not orthogonal to its operands");

    }

    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct calculation of a vectors length
        assertEquals(!isZero(new Vector(0, 3, 4).length() - 5), true,
                "ERROR: length() wrong value");
    }

    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct calculation of a vectors length
        assertEquals(isZero(new Vector(1, 2, 3).lengthSquared() - 14), true,
                 "ERROR: lengthSquared() wrong value" );
    }

    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();

        // TC01: test vector normalization vs vector length and cross-product
        assertEquals(isZero(u.length()-1), true,
                "ERROR: the normalized vector is not a unit vector");

        // TC02: test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, ()-> v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");

        // TC03: test that the vectors are not in opposite directions
        assertEquals(v.dotProduct(u)<0, false,
                "ERROR: the normalized vector is opposite to the original one");
    }
}