package renderer;

import org.junit.jupiter.api.Test;

import elements.AmbientLight;
import geometries.*;
import primitives.*;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {

	/**
	 * Produce a scene with basic 3D model and render it into a png image with a
	 * grid
	 */
	@Test
	public void basicRenderTwoColorTest() {
		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(255, 191, 191), new Double3(1,1,1))) //
				.setBackground(new Color(75, 127, 90));

		scene.geometries.add(new Sphere(new Point(0, 0, -100), 50),
				new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
																													// left
				new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)), // down
																														// left
				new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
		Camera camera = new Camera(new Point(Double3.ZERO), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500) //
				.setImageWriter(new ImageWriter("base render test", 1000, 1000))
				.setRayTracer(new RayTracerBasic(scene));

		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.YELLOW));
		camera.writeToImage();


	}

	/**
	 * Test for XML based scene - for bonus
	 */
	@Test
	public void basicRenderXml() {
		Scene scene = new Scene("XML Test scene");
		// enter XML file name and parse from XML file into scene object
		// ...

		Camera camera = new Camera(new Point(Double3.ZERO), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500)
				.setImageWriter(new ImageWriter("xml render test", 1000, 1000))
				.setRayTracer(new RayTracerBasic(scene));
		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.YELLOW));
		camera.writeToImage();
	}

	@Test
	public void renderTwoColorTestWithCameraMove(){
		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(255, 191, 191), new Double3(1,1,1))) //
				.setBackground(new Color(75, 127, 90));

		scene.geometries.add(new Sphere(new Point(0, 0, -100), 50),
				new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
				// left
				new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)), // down
				// left
				new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
		Camera camera = new Camera(new Point(Double3.ZERO), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500) //
				.setRayTracer(new RayTracerBasic(scene));

		// move around vUp
		camera.setImageWriter(new ImageWriter("1 move 10 deg vUp render test", 1000, 1000));
		camera.moveAroundVUp(10);
		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.YELLOW));
		camera.writeToImage();

		// move around vTo
		camera.setImageWriter(new ImageWriter("2 move 10 deg vTo render test", 1000, 1000));
		camera.moveAroundVTo(10);
		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.YELLOW));
		camera.writeToImage();

		// move around vRight
		camera.setImageWriter(new ImageWriter("3 move 10 deg vRight render test", 1000, 1000));
		camera.moveAroundVRight(10);
		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.YELLOW));
		camera.writeToImage();
	}
}
