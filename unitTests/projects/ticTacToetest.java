package projects;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.*;

public class ticTacToetest {
    private final Scene sceneXO = new Scene("XO")
            .setAmbientLight(new AmbientLight(new Color(200,200,200), 0.15));

    private final Scene sceneBoard = new Scene("board")
            .setAmbientLight(new AmbientLight(new Color(200,200,200), 0.15));

    private final Scene sceneTicTacToe = new Scene("ticTacToe")
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

    private Camera cameraXOComponents = new Camera(new Point(100,100,300),
            new Vector(0,0,-1),
            new Vector(0,1,0))
            .setVPSize(150, 150)
            .setVPDistance(50);

    //region material parameter
    Color xEmission = new Color(14,14,150);
    Color oEmission = new Color(128,0,0);
    Color boardEmission = new Color(200,200,200);
    Color baseEmission = new Color(51,51,51).scale(2);

    Material pondMaterial = new Material().setKd(0.2).setKs(0.9).setShininess(30).setkT(0.3);
    Material boardMaterial = new Material().setKs(0.5).setShininess(30).setkR(0.8);
    Material pondMaterialDiffuse = new Material().setKd(0.2).setKs(0.9).setShininess(30).setkT(0.3).setDiffuseness(5);
    Material boardMaterialGlossy = new Material().setKs(0.5).setShininess(30).setkR(0.8).setGlossiness(2);
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

    //region XO tests
    @Test
    void createXOTest(){
        generateXOScene(false);

        ImageWriter imageWriter = new ImageWriter("construct x", 500, 500);
        cameraXOComponents.setMultiThreading(4)
                .setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneXO)) //
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    void createXODiffuseAndGlossTest(){
        generateXOScene(true);

        ImageWriter imageWriter = new ImageWriter("construct x", 500, 500);
        cameraXOComponents.setMultiThreading(4)
                .setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneXO)) //
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    void createXOAntiAliasingTest(){
        generateXOScene(false);

        ImageWriter imageWriter = new ImageWriter("construct x", 500, 500);
        cameraXOComponents.setMultiThreading(4)
                .setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneXO)) //
                .renderImage(81) //
                .writeToImage(); //
    }

    void generateXOScene(boolean gloss){
        TicTacToe ticTacToe = new TicTacToe(1000);

        if(gloss)
            ticTacToe
                .setX(xEmission, pondMaterialDiffuse)
                .setO(oEmission, pondMaterialDiffuse);
        else
            ticTacToe
                    .setX(xEmission, pondMaterial)
                    .setO(oEmission, pondMaterial);

        sceneXO.addGeometry(ticTacToe.generateX(new Point(0, 0, 0)));
        sceneXO.addGeometry(base);
        sceneXO.addGeometry(ticTacToe.generateO(new Point(200,200,0)));
        sceneXO.lights.add(spotLight);
        sceneXO.lights.add(pointLightXO);
        sceneXO.lights.add(directionalLight);
    }
    //endregion

    //region board test
    @Test
    void createBoardTest(){
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setBoard(boardEmission,boardMaterial);
        sceneBoard.addGeometry(ticTacToe.generateBoard());

        ImageWriter imageWriter = new ImageWriter("construct board", 500, 500);
        cameraXOComponents.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneBoard)) //
                .renderImage() //
                .writeToImage(); //

        cameraXOComponents = cameraXOComponents.moveReferencePoint(-800,300,0).rotateAroundVRight(-30);
        imageWriter = new ImageWriter("construct board rotation", 500, 500);
        cameraXOComponents.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneBoard)) //
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    void createBoardAntiAliasingTest(){
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setBoard(boardEmission,boardMaterial);
        sceneBoard.addGeometry(ticTacToe.generateBoard());

        ImageWriter imageWriter = new ImageWriter("construct board", 500, 500);
        cameraXOComponents.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneBoard)) //
                .renderImage(81) //
                .writeToImage(); //

        cameraXOComponents = cameraXOComponents.moveReferencePoint(-800,300,0).rotateAroundVRight(-30);
        imageWriter = new ImageWriter("construct board rotation", 500, 500);
        cameraXOComponents.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneBoard)) //
                .renderImage(81) //
                .writeToImage(); //
    }

    @Test
    void createBoardAntiAliasingAndGlossyTest(){
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setBoard(boardEmission, boardMaterialGlossy);
        sceneBoard.addGeometry(ticTacToe.generateBoard());

        ImageWriter imageWriter = new ImageWriter("construct board", 500, 500);
        cameraXOComponents.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneBoard)) //
                .renderImage(81) //
                .writeToImage(); //

        cameraXOComponents = cameraXOComponents.moveReferencePoint(-800,300,0).rotateAroundVRight(-30);
        imageWriter = new ImageWriter("construct board rotation", 500, 500);
        cameraXOComponents.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneBoard)) //
                .renderImage(81) //
                .writeToImage(); //
    }
    //endregion

    //region tic-tac-toe tests
    @Test
    void createTicTacToeTest(){
        addTTTtoScene(false);

        ImageWriter imageWriter = new ImageWriter("ticTacToe - camera - rotation", 500, 500);
        camera.setMultiThreading(4).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneTicTacToe)) //
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    void createTicTacToeGlossyAndDiffuseTest(){
        addTTTtoScene(true);

        ImageWriter imageWriter = new ImageWriter("ticTacToe - camera - rotation", 500, 500);
        camera.setMultiThreading(4).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneTicTacToe)) //
                .renderImage() //
                .writeToImage(); //
    }

    @Test
    void createTicTacToeAntiAliasingTest(){
        addTTTtoScene(false);
        ImageWriter imageWriter = new ImageWriter("ticTacToe - camera - rotation", 500, 500);
        camera.setMultiThreading(4).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneTicTacToe)) //
                .renderImage(81) //
                .writeToImage(); //
    }

    @Test
    void createTicTacToeAntiAliasingAndGlossyTest(){
        addTTTtoScene(true);
        ImageWriter imageWriter = new ImageWriter("ticTacToe - camera - rotation", 500, 500);
        camera.setMultiThreading(4).setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(sceneTicTacToe)) //
                .renderImage(81) //
                .writeToImage(); //
    }

    /**
     * add the elements to tic-tac-toe scene
     * @param gloss activate the glossy effect or not
     */
    void addTTTtoScene(boolean gloss){
        xEmission = new Color(102,137,162);
        oEmission = new Color(30, 5, 33);
        TicTacToe ticTacToe = new TicTacToe(1000)
                .setBoard(boardEmission, boardMaterial);

        if(gloss)
            ticTacToe.setX(xEmission, pondMaterialDiffuse)
                .setO(oEmission, pondMaterialDiffuse)
                    .setBoard(boardEmission, boardMaterialGlossy);
        else
                ticTacToe.setX(xEmission, pondMaterial)
                        .setO(oEmission, pondMaterial)
                        .setBoard(boardEmission, boardMaterial);

        Geometries ticTacToeBoard = ticTacToe.generateTicTacToe();

        sceneTicTacToe.addGeometry(ticTacToeBoard);
        sceneTicTacToe.addGeometry(base);

        sceneTicTacToe.addGeometry(ticTacToe.generateX(new Point(-750,200,0)));
        sceneTicTacToe.addGeometry(ticTacToe.generateX(new Point(100,-850,0)));
        sceneTicTacToe.addGeometry(ticTacToe.generateO(new Point(700,-370,0)));
        sceneTicTacToe.addGeometry(ticTacToe.generateO(new Point(-400,-750,0)));

        sceneTicTacToe.lights.add(spotLight);
        sceneTicTacToe.lights.add(spotLight1);
        sceneTicTacToe.lights.add(spotLight2);
        sceneTicTacToe.lights.add(pointLightGame);
        sceneTicTacToe.lights.add(directionalLight);
    }
    //endregion

}
