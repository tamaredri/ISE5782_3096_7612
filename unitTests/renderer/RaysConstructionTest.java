package renderer;

import org.junit.jupiter.api.*;
import primitives.*;
import geometries.*;
import primitives.Vector;
import renderer.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the integration between the camera and
 * the intersections of a ray and a geometry
 *
 * @author tamar and yael
 */
public class RaysConstructionTest {

    /**
     * test for intersections of a ray, constructed through a view plane, with a triangle
     */
    @Test
    void testRayConstructionTriangle() {
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0,0,-1), new Vector(0, 1, 0))
                .setVPDistance(1)
                .setVPSize(3, 3);

        // TC01: triangle is before the camera, parallel and smaller than the view plane (0 points)
        Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(1, getNumberOfIntersection(camera, triangle, 3, 3),
                "triangle is before the camera, parallel and smaller tha the view plane (0 points) - wrong number of intersections");

        // TC02: triangle is before the camera, parallel and bigger than the view plane (2 points)
        triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(2, getNumberOfIntersection(camera, triangle, 3, 3),
                "triangle is before the camera, parallel and bigger than the view plane (2 points) - wrong number of intersections");

    }

    /**
     * test for intersections of a ray, constructed through a view plane, with a plane
     */
    @Test
    void testRayConstructionPlane() {
        Camera camera = new Camera(new Point(0, 0, 1), new Vector(0,0,-1), new Vector(0, 1, 0))
                .setVPDistance(1)
                .setVPSize(3, 3);

        // TC01: plane is before the camera and parallel to the view plane(9 points)
        Plane plane = new Plane(new Point(0, 0, -2), new Vector(0, 0, 1));
        assertEquals(9, getNumberOfIntersection(camera, plane, 3, 3),
                "plane is before the camera and parallel to the view plane(9 points) - wrong number of intersections");

        // TC02: plane is before the camera(6 points)
        plane = new Plane(new Point(1.5, 1.5, 0), new Vector(-1,0,1));
        assertEquals(6, getNumberOfIntersection(camera, plane, 3, 3),
                "plane is before the camera(6 points) - wrong number of intersections");

        // TC02: plane is before the camera(9 points)
        plane = new Plane(new Point(1.5, 1.5, 0), new Vector(-1,0,3));
        assertEquals(9, getNumberOfIntersection(camera, plane, 3, 3),
                "plane is before the camera(9 points) - wrong number of intersections");
    }

    /**
     * test for intersections of a ray, constructed through a view plane, with a sphere
     */
    @Test
    void testRayConstructionSphere() {
        Camera camera = new Camera(new Point(0, 0, 0.5), new Vector(0,0,-1), new Vector(0, 1, 0))
                .setVPDistance(1)
                .setVPSize(3, 3);

        // TC01: Sphere before the camera  and is smaller than the view plane (2 points)
        Sphere sphere = new Sphere(new Point(0, 0, -2.5), 1);
        assertEquals(2, getNumberOfIntersection(camera, sphere, 3, 3),
                "Sphere before the camera and is smaller than the view plane (2 points) - wrong amount of intersections");

        // TC02: Sphere before the camera and bigger than the view plane (18 points)
        sphere = new Sphere(new Point(0, 0, -2.5), 2.5);
        assertEquals(18, getNumberOfIntersection(camera, sphere, 3, 3),
                "Sphere before the camera and bigger than the view plane (18 points) - wrong amount of intersections");

        // TC03: Sphere before the camera and smaller than the view plane (10 points)
        sphere = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, getNumberOfIntersection(camera, sphere, 3, 3),
                "Sphere before the camera and smaller than the view plane (10 points) - wrong amount of intersections");

        // TC04: Sphere encapsulates the view plane (9 points)
        sphere = new Sphere(new Point(0, 0, -0.5), 4);
        assertEquals(9, getNumberOfIntersection(camera, sphere, 3, 3),
                "Sphere encapsulates the view plane (9 points) - wrong amount of intersections");

        // TC05: Sphere after the camera and smaller than the view plane (0 points)
        sphere = new Sphere(new Point(0, 0, 2), 0.5);
        assertEquals(0, getNumberOfIntersection(camera, sphere, 3, 3),
                "Sphere after the camera and smaller than the view plane (0 points) - wrong amount of intersections");

    }

    /**
     *  help method for the tests
     *
     * @param camera the object to construct the rays from
     * @param intersectable the object to intersect with the rays
     * @param nX the width pixels of the view plane
     * @param nY the height pixels of the view plane
     * @return the amount of intersections with a geometry from a camera through a view plane
     */
    int getNumberOfIntersection(Camera camera, Intersectable intersectable, int nX, int nY){
        int numOfIntersections = 0;

        for (int i = 0 ; i < nX ; i++)      // go over all the pixels in the view plane
            for(int j = 0; j < nY; j++)     // for each pixel [j,i], construct a ray and
            {                               // check the intersection points with the geometry
                List<Point> l = intersectable.findIntersections(camera.constructRay(nX, nY, j, i));
                if (l != null)
                    numOfIntersections += l.size();
            }
        return numOfIntersections;
    }
}
