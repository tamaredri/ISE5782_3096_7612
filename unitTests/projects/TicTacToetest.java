package projects;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.*;
import static java.awt.Color.*;

public class TicTacToetest{

    private final Scene scene = new Scene("TicTacToe")
                .setBackground(new Color(102,102,102));
                //.setAmbientLight(new AmbientLight(new Color(177,172,184), 1));

    private final Scene scene1 = new Scene("XO scene")
            .setAmbientLight(new AmbientLight(new Color(200,200,200), 0.15));

    private Camera camera1 = new Camera(new Point(1400, 4000, 150),
                                       new Vector(-1400, -4000, -150) ,
                                       new Vector(0, -150, 4000))
                            .setVPSize(150, 150)
                            .setVPDistance(50);

    private Camera camera = new Camera(new Point(0,0, 700),
                                       new Vector(0,0,-1) ,
                                       new Vector(0,1,0))
                            .setVPSize(150, 150)
                            .setVPDistance(50);

    private Camera camera2 = new Camera(new Point(-100, 400, 500),
                                       new Vector(0,0,-1) ,
                                       new Vector(0,1,0))
                            .setVPSize(150, 150)
                            .setVPDistance(50);

    private final Geometry plane = new Plane(new Point(0,0,-100),
                                       new Vector(0,0,1));

    LightSource spotLight = new SpotLight(new Color(242,242,242),
                                      new Point(6000,-6000,2600),
                                      new Vector(-4600, 4600, -2600));


    Color xEmission = new Color(14,14,150);
    Color oEmission = new Color(255,255,255).scale(0.05);
    Color boardEmission = new Color(177,177,184);
    Material pondMaterial = new Material().setKd(0.2).setKs(0.5).setShininess(30).setkT(0.5).setDiffuseness(5);
    Material boardMaterial = new Material().setKd(0.2).setKs(0.5).setShininess(30).setkR(0.5).setGlossiness(5);

    @Test
    void createXO(){
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setX(xEmission, pondMaterial)
                .setO(oEmission, pondMaterial);
        scene1.addGeometry(ticTacToe.generateX(new Point(0, 0, 0)));

        scene1.addGeometry(ticTacToe.generateO(new Point(500,500,0)));
        scene1.lights.add(spotLight);

        ImageWriter imageWriter = new ImageWriter("construct x", 500, 500);
       camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //

        camera2 = camera2.moveReferencePoint(-800,300,0).rotateAroundVRight(-30);
        imageWriter = new ImageWriter("construct x rotation", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    void createBoardTest(){
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setBoard(boardEmission,boardMaterial)
                .setX(xEmission, pondMaterial)
                .setO(oEmission, pondMaterial);
        scene1.addGeometry(ticTacToe.generateBoard(1000 * 0.05));

        ImageWriter imageWriter = new ImageWriter("construct x", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //

        camera = camera.moveReferencePoint(-800,300,0).rotateAroundVRight(-30);
        imageWriter = new ImageWriter("construct x rotation", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    void createTicTacToe(){
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setX(xEmission, pondMaterial)
                .setO(oEmission, pondMaterial)
                .setBoard(boardEmission, boardMaterial);
        Geometries ticTacToeBoard = ticTacToe.generateTicTacToe();
        scene.addGeometry(ticTacToeBoard);
        //scene.lights.add(pointLight1);
        scene.lights.add(spotLight);

        ImageWriter imageWriter = new ImageWriter("construct ticTacToe", 500, 500);
        camera/*.rotateAroundVTo(45)*/.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //

        camera = camera.moveReferencePoint(-800,300,0).rotateAroundVRight(-30);
        imageWriter = new ImageWriter("construct BoarRotation rotation", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }
}
