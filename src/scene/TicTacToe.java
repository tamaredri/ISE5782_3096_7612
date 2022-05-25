package scene;

import geometries.*;
import primitives.*;

/**
 * a class that generates the instruments of the tic-tac-toe game
 */
public class TicTacToe {
    private int width;
    private int length;
    private int totalHeight;
    private int height;
    private Vector vUp = new Vector(0,0,1);

    public TicTacToe(int width, int length, int height) {
        this.width = width;
        this.length = length;
        this.height = height;
        this.totalHeight = 2 * length + width;
    }

    //region X
    /**
     *  create an X in space
     * @param A first reference point - edge of the X
     * @param v1 dir vector for the short side of the X
     * @param v2 dir vector for the long side of the X
     * @param color the color of the X
     * @param mat the material of the X
     * @return Geometry that has the sides and bases of a 3D X
     */
    public Geometries generateX(Point A, Vector v1, Vector v2, Color color, Material mat){
        v1 = v1.normalize();
        v2 = v2.normalize();

        //region set points
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
                .setMaterial(mat).setEmission(color.scale(0.5));
        Geometry base2 = new Polygon(E,H,I,J)
                .setMaterial(mat).setEmission(color.scale(0.5));
        Geometry base3 = new Polygon(F,G,L,K)
                .setMaterial(mat).setEmission(color.scale(0.5));
        Geometries XShape = new Geometries(base1, base2, base3); // bottom base
        //endregion

        //region x upper base
        // move up in vUp's direction
        base1 = new Polygon(A.add(vUp.scale(height)),B.add(vUp.scale(height)),
                C.add(vUp.scale(height)),D.add(vUp.scale(height)))
                .setMaterial(mat).setEmission(color.scale(0.5));
        base2 = new Polygon(E.add(vUp.scale(height)),H.add(vUp.scale(height)),
                I.add(vUp.scale(height)),J.add(vUp.scale(height)))
                .setMaterial(mat).setEmission(color.scale(0.5));
        base3 = new Polygon(F.add(vUp.scale(height)),G.add(vUp.scale(height)),
                L.add(vUp.scale(height)),K.add(vUp.scale(height)))
                .setMaterial(mat).setEmission(color.scale(0.5));

        XShape.add(base1, base2, base3); // upper base
        //endregion

        //region x sides
        // side 1
        XShape.add(generateSide(A,B,height)
                .setMaterial(mat).setEmission(color));
        // side 2
        XShape.add(generateSide(B,H,height)
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

    /**
     * for 2 given points, generate a rectangle in a specified height
     * @param p1 point on the base of the X
     * @param p2 point on the base of the X
     * @param height of the X
     * @return the side of X between p1 and p2
     */
    private Polygon generateSide(Point p1, Point p2, double height){
        return new Polygon(p1, p1.add(vUp.scale(height)),
                p2.add(vUp.scale(height)),p2);
    }
    //endregion
}
