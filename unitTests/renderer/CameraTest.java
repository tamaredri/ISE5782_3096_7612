package renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import primitives.*;

/**
 * Testing Camera Class
 *
 * @author Dan
 */
class CameraTest {
    static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testConstructRay() {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(10);
        String badRay = "Bad ray";

        // ============ Equivalence Partitions Tests ==============
        // EP01: 4X4 Inside (1,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(1, -1, -10)),
                camera.setVPSize(8, 8).constructRay(4, 4, 1, 1), badRay);

		// =============== Boundary Values Tests ==================
		// BV01: 3X3 Center (1,1)
		assertEquals(new Ray(ZERO_POINT, new Vector(0, 0, -10)),
				camera.setVPSize(6, 6).constructRay(3, 3, 1, 1), badRay);

		// BV02: 3X3 Center of Upper Side (0,1)
		assertEquals(new Ray(ZERO_POINT, new Vector(0, -2, -10)),
				camera.setVPSize(6, 6).constructRay(3, 3, 1, 0), badRay);

		// BV03: 3X3 Center of Left Side (1,0)
		assertEquals(new Ray(ZERO_POINT, new Vector(2, 0, -10)),
				camera.setVPSize(6, 6).constructRay(3, 3, 0, 1), badRay);

		// BV04: 3X3 Corner (0,0)
		assertEquals(new Ray(ZERO_POINT, new Vector(2, -2, -10)),
				camera.setVPSize(6, 6).constructRay(3, 3, 0, 0), badRay);

		// BV05: 4X4 Corner (0,0)
		assertEquals(new Ray(ZERO_POINT, new Vector(3, -3, -10)),
				camera.setVPSize(8, 8).constructRay(4, 4, 0, 0), badRay);

		// BV06: 4X4 Side (0,1)
		assertEquals(new Ray(ZERO_POINT, new Vector(1, -3, -10)),
				camera.setVPSize(8, 8).constructRay(4, 4, 1, 0), badRay);
	}

	@Test
	void testMoveCamera() {
		// ============ Equivalence Partitions Tests ==============
		// EP01: move the camera 90 degrees
		Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(10);
		assertEquals(new Vector(0, 0, -1), camera.rotateAroundVRight(90).getvUp(), "move camera around vRight test");

        // EP02: move the camera 90 degrees
        camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(10);
        assertEquals(new Vector(-1, 0, 0), camera.rotateAroundVUp(90).getvTo(), "move camera around vUp test");

        // EP03: move the camera 90 degrees
        assertEquals(new Vector(0, -1, 0), camera.rotateAroundVTo(90).getvRight(), "move camera around vTo test");

        // EP04: angle is more than 360 degrees
        camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(10);
        assertEquals(new Vector(-1, 0, 0), camera.rotateAroundVUp(450).getvTo(), "angle is more than 360 degrees- didn't move camera correctly");

        // EP05: angle is negative
        camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(10);
        assertEquals(new Vector(-1, 0, 0), camera.rotateAroundVUp(-270).getvTo(), "angle is negative- didn't move camera correctly");

        // EP06: angle is ZERO
        camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(10);
        assertEquals(new Vector(0, 0, -1), camera.rotateAroundVUp(0).getvTo(), "angle is ZERO- the camera moved");
    }

    @Test
    void testMoveReferencePoint() {
        //TC01 camera doesn't move
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0));
        assertEquals(ZERO_POINT, camera.moveReferencePoint(0, 0, 0).getP0(), "camera doesn't move- camera moved");

        //TC02 camera moves
        assertEquals(new Point(-1, -1, -1), camera.moveReferencePoint(1, 1, 1).getP0(), "move reference point- didn't move correctly");

        //TC02 one of the doubles is zero
        assertEquals(new Point(-1, -2, -2), camera.moveReferencePoint(1, 1, 0).getP0(), "one of the doubles is zero- didn't move correctly");
    }
}

