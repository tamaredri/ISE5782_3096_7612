package projects;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.*;
import static java.awt.Color.*;

public class TicTacToetest{

    private final Scene scene = new Scene("scene")
                .setBackground(new Color(102,102,102));
                //.setAmbientLight(new AmbientLight(new Color(177,172,184), 1));

    private final Scene scene1 = new Scene("scene1")
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
    Color oEmission = new Color(255,255,255).scale(0.1);
    Color boardEmission = new Color(177,177,184);
    Material pondMaterial = new Material().setKd(0.2).setKs(0.9).setShininess(30).setkT(0.5);
    Material boardMaterial = new Material().setKd(0.2).setKs(0.5).setShininess(30).setkR(0.5);

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
                .renderImage(81) //
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
        scene1.addGeometry(ticTacToe.generateBoard());

        ImageWriter imageWriter = new ImageWriter("construct board", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //

        camera = camera.moveReferencePoint(-800,300,0).rotateAroundVRight(-30);
        imageWriter = new ImageWriter("construct board rotation", 500, 500);
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
        scene1.addGeometry(ticTacToeBoard);
        scene1.lights.add(spotLight);

        ImageWriter imageWriter = new ImageWriter("construct ticTacToe", 500, 500);
        camera.setMultiThreading(3).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage(81) //
                .writeToImage(); //

        //scene1.addGeometry(generateTable());

        camera = camera.moveReferencePoint(-800,300,0).rotateAroundVRight(-30);
        imageWriter = new ImageWriter("construct ticTacToe rotation", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage(81) //
                .writeToImage(); //


    }

    //region generate table
    /**
     * create a table under the xy surface
     */
    Geometries generateTable(){
        double z0 = 0;

        // legs
        double x1 = 1300;
        double y1 = 800;
        double height = 1500;
        double radius = 90;
        Vector legDir = new Vector(0, 0, -1);

        // base
        double x2 = 1500;
        double y2 = 1000;
        double z2 = -500;

        //region 4 points for bottom base
        Point A = new Point (-x2, y2,z0);
        Point B = new Point (x2, y2,z0);
        Point C = new Point (x2, -y2,z0);
        Point D = new Point (-x2, -y2,z0);
        //endregion

        //region 4 points for top base
        Point E = new Point (-x2, y2,z2);
        Point F = new Point (x2, y2,z2);
        Point G = new Point (x2, -y2,z2);
        Point H = new Point (-x2, -y2,z2);
        //endregion

        // two bases
        Geometry topBase = new Polygon(A,B,C,D);
        Geometry bottomBase = new Polygon(E,F,G,H);

        //4 sides
        Geometry side1 = new Polygon(A,B,F,E);
        Geometry side2 = new Polygon(B,C,G,F);
        Geometry side3 = new Polygon(C,D,H,G);
        Geometry side4 = new Polygon(D,A,E,H);


        return new Geometries(new Cylinder(new Ray(new Point(x1,y1,z0), legDir), radius, height).setEmission(new Color(GREEN)), // all 4 legs
                              new Cylinder(new Ray(new Point(-x1,y1,z0), legDir), radius, height).setEmission(new Color(GREEN)),
                              new Cylinder(new Ray(new Point(-x1,-y1,z0), legDir), radius, height).setEmission(new Color(GREEN)),
                              new Cylinder(new Ray(new Point(x1,-y1,z0), legDir), radius, height).setEmission(new Color(GREEN)),

                              topBase.setEmission(new Color(GREEN)),
                bottomBase.setEmission(new Color(GREEN)),
                side1.setEmission(new Color(GREEN)),
                side2.setEmission(new Color(GREEN)),
                side3.setEmission(new Color(GREEN)),
                side4.setEmission(new Color(GREEN))); // base pf the table

    }
    //endregion


}
