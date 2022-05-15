package lighting;

import org.junit.jupiter.api.Test;

import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import static java.awt.Color.*;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class LightsTests {
	private Scene scene1 = new Scene("Test scene");
	private Scene scene2 = new Scene("Test scene") //
			.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
	private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(150, 150) //
			.setVPDistance(1000);
	private Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(200, 200) //
			.setVPDistance(1000);

	private Point[] p = { // The Triangles' vertices:
			new Point(-110, -110, -150), // the shared left-bottom
			new Point(95, 100, -150), // the shared right-top
			new Point(110, -110, -150), // the right-bottom
			new Point(-75, 78, 100) }; // the left-top
	private Point trPL = new Point(30, 10, -100); // Triangles test Position of Light
	private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
	private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
	private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
	private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
	private Material material = new Material().setKd(0.5).setKs(0.5).setShininess(300);
	private Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
	private Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);
	private Geometry polygon1 = new Polygon(p[0], p[1], p[2]).setMaterial(material);
	private Geometry polygon2 = new Polygon(p[0], p[1], p[3]).setMaterial(material);
	private Geometry sphere = new Sphere(new Point(0, 0, -50), 50d) //
			.setEmission(new Color(BLUE).reduce(2)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void sphereDirectional() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}


	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void sphereThreeLights() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(new Color(GRAY),
						  new Vector(1, 1, -0.5)));

		scene1.lights.add(new PointLight(new Color(RED),
						  				 new Point(10,10,20)));

		scene1.lights.add(new SpotLight(new Color(PINK) ,
								        new Point(100,-10,-50),
									    new Vector(-1, 0, -0.5)));

		ImageWriter imageWriter = new ImageWriter("sphereThreeLights1", 500, 500);

		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //

		// *********************************************************************************

		scene1.lights.clear();
		scene1.lights.add(new DirectionalLight(new Color(18,170,32),
											   new Vector(1, 1, -0.5)));

		scene1.lights.add(new PointLight(new Color(242,114,114),
										 new Point(10,10,20)));

		scene1.lights.add(new SpotLight(new Color(YELLOW),
										spPL,
										new Vector(1, 1, -0.5)).setKl(new Double3(2)));

		imageWriter = new ImageWriter("sphereThreeLights2", 650, 650);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //

		// *********************************************************************************


		scene1.lights.clear();
		scene1.lights.add(new DirectionalLight(new Color(GREEN),
											   new Vector(1, 1, -0.5)));

		scene1.lights.add(new PointLight(new Color(RED).reduce(2),
										 new Point(10,10,20)));

		scene1.lights.add(new SpotLight(new Color(ORANGE) ,
										new Point(300,-10,-50),
										new Vector(-1, -1, -0.5)));

		imageWriter = new ImageWriter("sphereThreeLights3", 800, 800);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a point light
	 */
	@Test
	public void spherePoint() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new PointLight(spCL, spPL).setKl(new Double3(0.001)).setKq(new Double3(0.0002)));


		ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a spotlight
	 */
	@Test
	public void sphereSpot() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setKl(new Double3(0.001)).setKq(new Double3(0.0001)));

		ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a polygon lighted by a spotlight
	 */
	@Test
	public void polygonsSpot() {
		scene2.geometries.add(polygon1, polygon2);
		scene2.lights.add(new SpotLight(trCL, trPL, trDL).setKl(new Double3(0.001)).setKq(new Double3(0.0001)));

		ImageWriter imageWriter = new ImageWriter("lightPolygonSpot", 500, 500);
		//camera2.rotateAroundVRight(5).moveReferencePoint(0,617.35,0);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	@Test
	public void polygonsDirectional() {
		scene2.geometries.add(polygon1, polygon2);
		scene2.lights.add(new DirectionalLight(trCL, trDL));

		ImageWriter imageWriter = new ImageWriter("lightPolygonsDirectional", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	@Test
	public void polygonsPoint() {
		scene2.geometries.add(polygon1, polygon2);
		scene2.lights.add(new PointLight(trCL, trPL).setKl(new Double3(0.001)).setKq(new Double3(0.0002)));

		ImageWriter imageWriter = new ImageWriter("lightPolygonsPoint", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	@Test
	public void polygonsThreeLight() {
		scene2.geometries.add(polygon1, polygon2);
		scene2.lights.add(new DirectionalLight(new Color(18,170,32),
				trDL));

		scene2.lights.add(new PointLight(new Color(255,110,110),
				new Point(40, 5, -100)));

		scene2.lights.add(new SpotLight(new Color(YELLOW) ,
				trPL,
				new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("polygonsThreeLight1", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //


		// *********************************************************************************

		scene2.lights.clear();
		scene2.lights.add(new DirectionalLight(new Color(RED),
				new Vector(-1,5,2)));

		scene2.lights.add(new PointLight(new Color(GREEN).reduce(2),
				new Point(0,-10,-100)));

		scene2.lights.add(new SpotLight(new Color(YELLOW) ,
				new Point(30, 60, -100),
				new Vector(2, -1, -1)));

		imageWriter = new ImageWriter("trianglesThreeLight2", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //

		// *********************************************************************************

		scene2.lights.clear();
		scene2.lights.add(new DirectionalLight(new Color(RED),
				new Vector(-2,2,2)));

		scene2.lights.add(new PointLight(new Color(27,233,249),
				new Point(0,0,0)));

		scene2.lights.add(new SpotLight(new Color(YELLOW) ,
				new Point(60, 50, 200),
				new Vector(1, -2, 0.5)));

		imageWriter = new ImageWriter("trianglesThreeLight3", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}
	/**
	 * Produce a picture of two triangles lighted by a directional light
	 */
	@Test
	public void trianglesDirectional() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new DirectionalLight(trCL, trDL));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of two triangles lighted by a directional light
	 */
	@Test
	public void trianglesThreeLight() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new DirectionalLight(new Color(18,170,32),
											   trDL));

		scene2.lights.add(new PointLight(new Color(255,110,110),
										 new Point(40, 5, -100)));

		scene2.lights.add(new SpotLight(new Color(YELLOW) ,
										trPL,
										new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("trianglesThreeLight1", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //


		// *********************************************************************************

		scene2.lights.clear();
		scene2.lights.add(new DirectionalLight(new Color(RED),
											   new Vector(-1,5,2)));

		scene2.lights.add(new PointLight(new Color(GREEN).reduce(2),
										 new Point(0,-10,-100)));

		scene2.lights.add(new SpotLight(new Color(YELLOW) ,
										new Point(30, 60, -100),
										new Vector(2, -1, -1)));

		imageWriter = new ImageWriter("trianglesThreeLight2", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //

		// *********************************************************************************

		scene2.lights.clear();
		scene2.lights.add(new DirectionalLight(new Color(RED),
											   new Vector(-2,2,2)));

		scene2.lights.add(new PointLight(new Color(27,233,249),
										 new Point(0,0,0)));

		scene2.lights.add(new SpotLight(new Color(YELLOW) ,
										new Point(60, 50, 200),
										new Vector(1, -2, 0.5)));

		imageWriter = new ImageWriter("trianglesThreeLight3", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}
	/**
	 * Produce a picture of a two triangles lighted by a point light
	 */
	@Test
	public void trianglesPoint() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new PointLight(trCL, trPL).setKl(new Double3(0.001)).setKq(new Double3(0.0002)));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of two triangles lighted by a spotlight
	 */
	@Test
	public void trianglesSpot() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new SpotLight(trCL, trPL, trDL).setKl(new Double3(0.001)).setKq(new Double3(0.0001)));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a narrow spot light
	 */
	@Test
	public void sphereSpotSharp() {
		scene1.geometries.add(sphere);
		scene1.lights
				.add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setKl(new Double3(0.001)).setKq(new Double3(0.00004)));

		ImageWriter imageWriter = new ImageWriter("lightSphereSpotSharp", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage() //
				.writeToImage(); //
	}

	/**
	 * Produce a picture of a two triangles lighted by a narrow spot light
	 */
	@Test
	public void trianglesSpotSharp() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new SpotLight(trCL, trPL, trDL).setKl(new Double3(0.001)).setKq(new Double3(0.00004)));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage() //
				.writeToImage(); //
	}

}
