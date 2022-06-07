package projects;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.*;

public class ticTacToetest {
    private final Scene scene = new Scene("scene1")
            .setAmbientLight(new AmbientLight(new Color(200,200,200), 0.15));

    private Camera camera = new Camera(new Point(319.28533001223155,
                                                 -26.60444431189778,
                                                 665.270364466614),
                                       new Vector(-0.16317591116653482,
                                               -0.05939117461388472,
                                               -0.984807753012208),
                                       new Vector(-0.9254165783983234,
                                               -0.3368240888334653,
                                               0.17364817766693036)
                                        /*new Point(0,0, 700),
                                       new Vector(0,0,-1) ,
                                       new Vector(0,-1,0)*/)
                            .setVPSize(150, 150)
                            .setVPDistance(50);

    private Camera cameraXO = new Camera(new Point(100,100,300),
            new Vector(0,0,-1),
            new Vector(0,1,0))
            .setVPSize(150, 150)
            .setVPDistance(50);

    //region material parameter
    Color xEmission = new Color(14,14,150);
    Color oEmission = new Color(128,0,0);
    Color boardEmission = new Color(200,200,200);
    Color baseEmission = new Color(51,51,51).scale(2);

    Material pondMaterial = new Material().setKd(0.2).setKs(0.9).setShininess(30).setkT(0.3);//.setDiffuseness(5);
    Material boardMaterial = new Material().setKs(0.5).setShininess(30).setkR(0.8);//.setGlossiness(2);
    Material baseMaterial = new Material().setKd(0.7).setKs(0.1).setShininess(30);
    //endregion

    private final Geometry base = new Plane(new Point(0,0,0), new Vector(0,0,1))
            .setMaterial(baseMaterial).setEmission(baseEmission);

    //region lights
    LightSource spotLight = new SpotLight(new Color(242,242,242).reduce(2),
            new Point(6000,-6000,2600),
            new Vector(-4600, 4600, -2600)).setKc(2.5);

    LightSource spotLight1 = new SpotLight(new Color(/*242,242,242*/java.awt.Color.RED).reduce(2),
            new Point(5000,-6000,2600),
            new Vector(-4600, 4600, -2600)).setKc(2.5);

    LightSource spotLight2 = new SpotLight(new Color(/*242,242,242*/java.awt.Color.GREEN).reduce(2),
            new Point(7000,-6000,3000),
            new Vector(-4600, 4600, -2600)).setKc(2.5);

    LightSource pointLightXO = new PointLight(new Color(242,242,242),
            new Point(0,0,6000)).setKc(2);

    LightSource pointLightGame = new PointLight(new Color(242,242,242),
            new Point(0,0,6000)).setKc(2).setKq(1);

    LightSource directionalLight = new DirectionalLight(new Color(242,242,242),
            new Vector(0,0,1));
    //endregion


    @Test
    void createXO(){
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setX(xEmission, pondMaterial)
                .setO(oEmission, pondMaterial);

        scene.addGeometry(ticTacToe.generateX(new Point(0, 0, 0)));
        scene.addGeometry(base);
        scene.addGeometry(ticTacToe.generateO(new Point(200,200,0)));
        scene.lights.add(spotLight);
        scene.lights.add(pointLightXO);
        scene.lights.add(directionalLight);

        ImageWriter imageWriter = new ImageWriter("construct x", 500, 500);
        cameraXO.setMultiThreading(4)
                .setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(/*81*/) //
                .writeToImage(); //
    }

    @Test
    void createBoardTest(){
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setBoard(boardEmission,boardMaterial)
                .setX(xEmission, pondMaterial)
                .setO(oEmission, pondMaterial);
        scene.addGeometry(ticTacToe.generateBoard());

        ImageWriter imageWriter = new ImageWriter("construct board", 500, 500);
        cameraXO.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(81) //
                .writeToImage(); //

        cameraXO = cameraXO.moveReferencePoint(-800,300,0).rotateAroundVRight(-30);
        imageWriter = new ImageWriter("construct board rotation", 500, 500);
        cameraXO.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    void createTicTacToeTest(){
        xEmission = new Color(102,137,162);
        oEmission = new Color(30, 5, 33);
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setX(xEmission, pondMaterial)
                .setO(oEmission, pondMaterial)
                .setBoard(boardEmission, boardMaterial);
        Geometries ticTacToeBoard = ticTacToe.generateTicTacToe();
        scene.addGeometry(ticTacToeBoard);
        scene.addGeometry(base);

        scene.addGeometry(ticTacToe.generateX(new Point(-750,200,0)));
        scene.addGeometry(ticTacToe.generateX(new Point(100,-850,0)));
        scene.addGeometry(ticTacToe.generateO(new Point(700,-370,0)));
        scene.addGeometry(ticTacToe.generateO(new Point(-400,-750,0)));

        scene.lights.add(spotLight);
        scene.lights.add(spotLight1);
        scene.lights.add(spotLight2);
        scene.lights.add(pointLightGame);
        scene.lights.add(directionalLight);

        ImageWriter imageWriter = new ImageWriter("ticTacToe - camera - rotation", 500, 500);
        camera.setMultiThreading(4).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(81) //
                .writeToImage(); //
    }
    //endregion


}
