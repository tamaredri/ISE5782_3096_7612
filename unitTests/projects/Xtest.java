package projects;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.*;
import static java.awt.Color.*;

public class Xtest {
    private Scene scene = new Scene("X scene")
            .setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
    private Camera camera = new Camera(new Point(0,0, 1000),
                                        new Vector(0,0,-1),
                                        new Vector(0,1,0))
    //private Camera camera = new Camera(new Point(500,500, 200),
    //                                    new Vector(-1,-1, 0),
    //                                    new Vector(0, 0, 1))
                            .setVPSize(150, 150)
                            .setVPDistance(50);


    private Geometry plane = new Plane(new Point(0,0,0),
                                        new Vector(0,0,1))
                                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                                .setEmission(new Color(167,136,105)); // dark blue

    private PointLight spotLight = new SpotLight(new Color(218,113,235),
                                    new Point(5, 5, 1000),
                                    new Vector(0,0,-1));
    private PointLight pointLight = new PointLight(new Color(218,113,235),
                                            new Point(-1000,-1000, 500));

    private Color emition = new Color(27,17,59); // light brown
    private Material material = new Material().setKd(0.2).setKs(0.2).setShininess(5).setkT(0.3).setkR(0.3);

    @Test
    void createX(){
        TicTacToe ticTacToe = new TicTacToe(200, 300, 350);
        scene.addGeometry(ticTacToe.generateX(new Point(-100, 400, 0),
                new Vector(1,0,0),
                new Vector(0,-1,0), emition, material));


        scene.addGeometry(plane);
        scene.lights.add(spotLight.setKl(0.001).setKq(0.0002));
        scene.lights.add(pointLight);

        ImageWriter imageWriter = new ImageWriter("construct x", 500, 500);
       camera.rotateAroundVTo(45).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //

        camera = camera.moveReferencePoint(-800,300,0).rotateAroundVRight(-30);
        imageWriter = new ImageWriter("construct x rotation", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }
}
