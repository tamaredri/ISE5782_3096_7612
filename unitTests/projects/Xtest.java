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
    //private Camera camera = new Camera(new Point(1000,1000, 200),
                                        new Vector(0,0, -1),
                                        new Vector(0, 1, 0))
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
        scene.addGeometry(generateX(new Point(-100, 400, 0),
                                    new Vector(1,0,0),
                                    new Vector(0,-1,0), emition, material));

        scene.addGeometry(plane);
        scene.lights.add(spotLight.setKl(0.001).setKq(0.0002));
        scene.lights.add(pointLight);

        ImageWriter imageWriter = new ImageWriter("construct x", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //

        camera = camera.moveReferencePoint(0,500,0);
        imageWriter = new ImageWriter("construct x rotation", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     *
     * @param A first reference point - edge of the X
     * @param v1 dir vector for the short side of the X
     * @param v2 dir vector for the long side of the X
     * @param color the color of the X
     * @param mat the material of the X
     * @return
     */
    private Geometries generateX(Point A, Vector v1, Vector v2, Color color, Material mat){
        int width = 200, length = 300, totalHeight = 2 * length + width, height = 350;
        Vector vUp = new Vector(0,0,1);
        v1 = v1.normalize();
        v2 = v2.normalize();

        //region    set points
        // long rectangle base
        Point B = A.add(v1.scale(width));
        Point C = B.add(v2.scale(totalHeight));
        Point D = A.add(v2.scale(totalHeight));
        // short rectangle base
        Point E = B.add(v2.scale(width + length));
        Point F = A.add(v2.scale(width + length));
        Point G = A.add(v2.scale(length));
        Point H = B.add(v2.scale(length));

        Point I = H.add(v1.scale(length));
        Point J = E.add(v1.scale(length));
        Point K = F.add(v1.scale(-length));
        Point L = G.add(v1.scale(-length));
        //endregion

        //region x bottom base
        Geometry base1 = new Polygon(A,B,C,D)
                            .setMaterial(mat).setEmission(color);
        Geometry base2 = new Polygon(E,H,I,J)
                            .setMaterial(mat).setEmission(color);
        Geometry base3 = new Polygon(F,G,L,K)
                            .setMaterial(mat).setEmission(color);
        Geometries XShape = new Geometries(base1, base2, base3); // bottom base
        //endregion

        //region x upper base
        // move up in vUp's direction
        base1 = new Polygon(A.add(vUp.scale(height)),B.add(vUp.scale(height)),
                            C.add(vUp.scale(height)),D.add(vUp.scale(height)))
                            .setMaterial(mat).setEmission(color);
        base2 = new Polygon(E.add(vUp.scale(height)),H.add(vUp.scale(height)),
                            I.add(vUp.scale(height)),J.add(vUp.scale(height)))
                            .setMaterial(mat).setEmission(color);
        base3 = new Polygon(F.add(vUp.scale(height)),G.add(vUp.scale(height)),
                            L.add(vUp.scale(height)),K.add(vUp.scale(height)))
                            .setMaterial(mat).setEmission(color);

        XShape.add(base1, base2, base3); // upper base
        //endregion

        //region x sides
        // side 1
        XShape.add(new Polygon(A, A.add(vUp.scale(width)),
                               B.add(vUp.scale(width)),B)
                                .setMaterial(mat).setEmission(color));
        // side 2
        XShape.add(new Polygon(B, B.add(vUp.scale(width)),
                               H.add(vUp.scale(width)),H)
                                .setMaterial(mat).setEmission(color));
        // side 3
        XShape.add(generateSide(H,I,height)
                                .setMaterial(mat).setEmission(color));
        // side 4
        XShape.add(generateSide(I,J,height)
                                .setMaterial(mat).setEmission(color));
        // side 5
        XShape.add(generateSide(J,E,height)
                                .setMaterial(mat).setEmission(color));
        // side 6
        XShape.add(generateSide(E,C,height)
                                .setMaterial(mat).setEmission(color));
        // side 7
        XShape.add(generateSide(C,D,height)
                                .setMaterial(mat).setEmission(color));
        // side 8
        XShape.add(generateSide(D,F,height)
                                .setMaterial(mat).setEmission(color));
        // side 9
        XShape.add(generateSide(F,K,height)
                                .setMaterial(mat).setEmission(color));
        // side 10
        XShape.add(generateSide(K,L,height)
                                .setMaterial(mat).setEmission(color));
        // side 11
        XShape.add(generateSide(L,G,height)
                                .setMaterial(mat).setEmission(color));
        // side 12
        XShape.add(generateSide(G,A,height)
                                .setMaterial(mat).setEmission(color));
        //endregion

        return XShape;
    }

    private Polygon generateSide(Point p1, Point p2, double height){
        Vector vUp = new Vector(0,0,1);
        return new Polygon(p1, p1.add(vUp.scale(height)),
                p2.add(vUp.scale(height)),p2);
    }
}
