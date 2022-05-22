package projects;

import geometries.Geometry;
import geometries.Plane;
import geometries.Polygon;
import lighting.AmbientLight;
import lighting.LightSource;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;

public class X {
    private Scene scene = new Scene("X scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
    private Camera camera = new Camera(new Point(50, 50, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(150, 150) //
            .setVPDistance(50);

    double x = 100, y = 400, z1 = 0, z2= 200;

    private Point[] longRectanglePointsBottom = { new Point(-x,y,z1), new Point(x,y,z1), new Point(x,-y,z1), new Point(-x,-y,z1)};
    private Point[] longRectanglePointsTop = { new Point(-x,y,z2), new Point(x,y,z2), new Point(x,-y,z2), new Point(-x,-y,z2)};
    private Point[] shortRectangle1PointsBottom = {new Point(-y,-x,z1), new Point(-y,x,z1), new Point(-x,x,z1), new Point(-x,-x,z1)};
    private Point[] shortRectangle1PointsTop = {new Point(-y,-x,z2), new Point(-y,x,z2), new Point(-x,x,z2), new Point(-x,-x,z2)};
    private Point[] shortRectangle2PointsBottom = {new Point(x,x,z1), new Point(y,x,z1), new Point(y,-x,z1), new Point(x,-x,z1)};
    private Point[] shortRectangle2PointsTop = {new Point(x,x,z2), new Point(y,x,z2), new Point(y,-x,z2), new Point(x,-x,z2)};


    private Point[] side1 = {new Point(-x,y,z1), new Point(-x, y, z2), new Point(-x,x,z2), new Point(-x,x,z1)};
    private Point[] side2 = {new Point(-x,x,z1), new Point(-x,x,z2), new Point(-y,x,z2), new Point(-y,x,z1)};
    private Point[] side3 = {new Point(-y,x,z2), new Point(-y,x,z1), new Point(-y,-x,z1), new Point(-y,-x,z2)};
    private Point[] side4 = {new Point(-y,x,z2), new Point(-y,x,z1), new Point(-x,-x,z1), new Point(-x,-x,z2)};
    private Point[] side6 = {new Point(-x,-x,z1), new Point(-x,-x,z2), new Point(-x,-y,z2), new Point(-x,-y,z1)};
    private Point[] side7 = { new Point(-x,-y,z2), new Point(-x,-y,z1), new Point(x,-y,z1), new Point(x,-y,z2)};
    private Point[] side8 = {new Point(x,-y,z1), new Point(x,-y,z2), new Point(x,-x,z2), new Point(x,-x,z1)};
    private Point[] side9 = {new Point(x,-x,z2), new Point(x,-x,z1), new Point(y,-x,z1), new Point(y,-x,z2)};
    private Point[] side10 = { new Point(y,-x,z1), new Point(y,-x,z2), new Point(y,x,z2), new Point(y,x,z1)};
    private Point[] side11 = { new Point(y,x,z2), new Point(y,x,z1), new Point(x,x,z1), new Point(x,x,z2)};
    private Point[] side12 = {new Point(x,x,z1), new Point(x,x,z2), new Point(x,y,z2), new Point(x,y,z1)};

    private Material material = new Material().setKd(0.2).setKs(0.2).setShininess(5).setkT(0.6);


    private Color color = new Color(0,255,150);
    private Geometry longRectangle = new Polygon(longRectanglePointsBottom).setMaterial(material).setEmission(color);
    private Geometry shortRectangle = new Polygon(shortRectangle1PointsBottom).setMaterial(material).setEmission(color);
    private Geometry shortRectangle2 = new Polygon(shortRectangle2PointsBottom).setMaterial(material).setEmission(color);
    private Geometry plane = new Plane(new Point(0,0,-100), new Vector(0,0,1)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)).setEmission(new Color(PINK));
    private PointLight pointLight = new PointLight(new Color(0,0,255), new Point(100,100,50)).setKc(0.1);

    @Test
    void createX(){

        scene.addGeometry(longRectangle).addGeometry(shortRectangle).addGeometry(shortRectangle2).addGeometry(plane)
                .addGeometry(new Polygon(side1).setEmission(color))
                .addGeometry(new Polygon(side2).setEmission(color))
                .addGeometry(new Polygon(side3).setEmission(color))
                .addGeometry(new Polygon(side4).setEmission(color))
                .addGeometry(new Polygon(side6).setEmission(color))
                .addGeometry(new Polygon(side7).setEmission(color))
                .addGeometry(new Polygon(side8).setEmission(color))
                .addGeometry(new Polygon(side9).setEmission(color))
                .addGeometry(new Polygon(side10).setEmission(color))
                .addGeometry(new Polygon(side11).setEmission(color))
                .addGeometry(new Polygon(side12).setEmission(color));
        scene.lights.add(pointLight.setKl(0.001).setKq(0.0002));

        ImageWriter imageWriter = new ImageWriter("construct x", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }
}
