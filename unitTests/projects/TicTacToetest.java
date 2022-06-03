package projects;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.*;

public class TicTacToetest{

    private final Scene scene = new Scene("scene")
                .setBackground(new Color(102,102,102));
                //.setAmbientLight(new AmbientLight(new Color(177,172,184), 1));

    private final Scene scene1 = new Scene("scene1")
            .setAmbientLight(new AmbientLight(new Color(200,200,200), 0.15));



    private Camera camera = new Camera(new Point(0,0, 700),
                                       new Vector(0,0,-1) ,
                                       new Vector(0,-1,0))
                            .setVPSize(150, 150)
                            .setVPDistance(50);

    private Camera cameraXO = new Camera(new Point(0,0,700),
            new Vector(0,0,-1),
            new Vector(0,1,0))
            .setVPSize(150, 150)
            .setVPDistance(50);

    private Camera camera2 = new Camera(new Point(-400, 400, 500),
                                       new Vector(0,0,-1) ,
                                       new Vector(-1,0,0))
                            .setVPSize(150, 150)
                            .setVPDistance(50);

    private Camera camera3 = new Camera(new Point(0, -3500, 150),
            new Vector(0,3500,-150) ,
            new Vector(0, 150,3500))
            .setVPSize(150, 150)
            .setVPDistance(50);

    private Camera camera4 = new Camera(new Point(0, -1500, 0),
            new Vector(0,1,0) ,
            new Vector(0, 0,1))
            .setVPSize(150, 150)
            .setVPDistance(50);


    Color xEmission = new Color(14,14,150);
    Color oEmission = new Color(128,0,0);
    Color tableEmission = new Color(70,35,0);
    Color boardEmission = new Color(200,200,200);
    Color baseEmission = new Color(51,51,51).scale(2);

    Material pondMaterial = new Material().setKd(0.2).setKs(0.9).setShininess(30).setkT(0.4).setDiffuseness(5);
    Material boardMaterial = new Material().setKs(0.5).setShininess(30).setkR(0.8).setGlossiness(2);
    Material tableMaterial = new Material().setKd(0.7).setKs(0.5).setShininess(0).setkR(0.1).setGlossiness(3);
    Material baseMaterial = new Material().setKd(0.7).setKs(0.1).setShininess(30).setkR(0.2);

    private final Geometry base = new Plane(new Point(0,0,0), new Vector(0,0,1))
            .setMaterial(baseMaterial).setEmission(baseEmission);

    LightSource spotLight = new SpotLight(new Color(242,242,242),
            new Point(6000,-6000,2600),
            new Vector(-4600, 4600, -2600)).setKc(2.5);

    LightSource pointLightXO = new PointLight(new Color(242,242,242),
            new Point(0,0,6000)).setKc(2);

    LightSource pointLightGame = new PointLight(new Color(242,242,242),
            new Point(0,0,6000)).setKc(2).setKq(1);

    LightSource directionalLight = new DirectionalLight(new Color(242,242,242),
            new Vector(0,0,1));


    @Test
    void createXO(){
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setX(xEmission, pondMaterial)
                .setO(oEmission, pondMaterial);

        scene1.addGeometry(ticTacToe.generateX(new Point(0, 0, 0)));
        scene1.addGeometry(base);
        scene1.addGeometry(ticTacToe.generateO(new Point(200,200,0)));
        scene1.lights.add(spotLight);
        scene1.lights.add(pointLightXO);

        ImageWriter imageWriter = new ImageWriter("construct x", 500, 500);
        cameraXO.moveReferencePoint(100,400,100).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //

        //cameraXO = cameraXO.rotateAroundVTo(50);
        cameraXO = cameraXO.moveReferencePoint(-100,0,100)
                .rotateAroundVRight(-30)
                .rotateAroundVUp(-20);

        imageWriter = new ImageWriter("construct x rotation", 500, 500);
        cameraXO.setImageWriter(imageWriter) //
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
        cameraXO.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //

        cameraXO = cameraXO.moveReferencePoint(-800,300,0).rotateAroundVRight(-30);
        imageWriter = new ImageWriter("construct board rotation", 500, 500);
        cameraXO.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    void createTicTacToeTest(){
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setX(xEmission, pondMaterial)
                .setO(oEmission, pondMaterial)
                .setBoard(boardEmission, boardMaterial);
        Geometries ticTacToeBoard = ticTacToe.generateTicTacToe();
        scene1.addGeometry(ticTacToeBoard);
        scene1.addGeometry(base);
        scene1.addGeometry(ticTacToe.generateX(new Point(-750,200,0)));
        scene1.addGeometry(ticTacToe.generateX(new Point(100,-850,0)));
        scene1.addGeometry(ticTacToe.generateO(new Point(700,-370,0)));
        scene1.addGeometry(ticTacToe.generateO(new Point(-400,-750,0)));
        scene1.lights.add(spotLight);
        scene1.lights.add(pointLightGame);
        scene1.lights.add(directionalLight);



        camera = camera.moveReferencePoint(100,0,0);
        ImageWriter imageWriter = new ImageWriter("ticTacToe - camera", 500, 500);
        camera.setMultiThreading(true).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //

        camera = camera.moveReferencePoint(-100, 0,-100)
                .rotateAroundVTo(-70).rotateAroundVRight(-10)
                .moveReferencePoint(-200,50,-100);

        imageWriter = new ImageWriter("ticTacToe - camera - rotation", 500, 500);
        camera.setMultiThreading(true).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //

        camera4.moveReferencePoint(200,350,-50).rotateAroundVRight(30);
        imageWriter = new ImageWriter("ticTacToe - camera4", 500, 500);
        camera4.setMultiThreading(true).setImageWriter(imageWriter) //
                        .setRayTracer(new RayTracerBasic(scene1)) //
                        .renderImage() //
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

        //region 4 points for top base
        Point A = new Point (-x2, y2,z0);
        Point B = new Point (x2, y2,z0);
        Point C = new Point (x2, -y2,z0);
        Point D = new Point (-x2, -y2,z0);
        //endregion

        //region 4 points for bottom base
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


        return new Geometries(new Cylinder(new Ray(new Point(x1,y1,z0), legDir), radius, height) // all 4 legs
                                        .setEmission(tableEmission)
                                        .setMaterial(tableMaterial),
                              new Cylinder(new Ray(new Point(-x1,y1,z0), legDir), radius, height)
                                      .setEmission(tableEmission)
                                      .setMaterial(tableMaterial),
                              new Cylinder(new Ray(new Point(-x1,-y1,z0), legDir), radius, height)
                                      .setEmission(tableEmission)
                                      .setMaterial(tableMaterial),
                              new Cylinder(new Ray(new Point(x1,-y1,z0), legDir), radius, height)
                                      .setEmission(tableEmission)
                                      .setMaterial(tableMaterial),

                             topBase .setEmission(tableEmission)
                                     .setMaterial(tableMaterial),
                            bottomBase.setEmission(tableEmission)
                                      .setMaterial(tableMaterial),
                            side1.setEmission(tableEmission)
                                    .setMaterial(tableMaterial),
                            side2.setEmission(tableEmission)
                                    .setMaterial(tableMaterial),
                            side3.setEmission(tableEmission)
                                    .setMaterial(tableMaterial),
                            side4 .setEmission(tableEmission)
                                    .setMaterial(tableMaterial)
                                     ); // base pf the table

    }
    //endregion


}
