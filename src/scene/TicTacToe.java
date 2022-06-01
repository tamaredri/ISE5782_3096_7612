package scene;

import geometries.*;
import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * a class that generates the instruments and the full game of tic-tac-toe.
 * relative to the (0,0,0) position in space
 */
public class TicTacToe {
    private final int size;
    private final Vector vUp = new Vector(0,0,1);
    private final Vector vRight = new Vector(1,0,0);
    private final Vector vTo = new Vector(0,1,0);
    private Color boardColor;
    private Material boardMat;
    private Color xColor;
    private Material xMat;
    private Color oColor;
    private Material oMat;

    //region setters
    public TicTacToe setX(Color xColor, Material xMat){
        this.xColor = xColor;
        this.xMat = xMat;
        return this;
    }

    public TicTacToe setO(Color oColor, Material oMat){
        this.oColor = oColor;
        this.oMat = oMat;
        return this;
    }

    public TicTacToe setBoard(Color boardColor, Material boardMat){
        this.boardColor = boardColor;
        this.boardMat = boardMat;
        return this;
    }
    //endregion

    //region ctor
    public TicTacToe(int boardSize /*, Color boardColor, Material boardMat*/) {
        this.size = boardSize;
        //this.boardColor = boardColor;
        //this.boardMat = boardMat;
    }
    //endregion

    //region generateLine
    /**
     * generate a triangular line on the board
     * @param vTo direction of the line
     * @param vRight direction orthogonal to the line
     * @param p start point of the line (left side of the line)
     * @return geometry that represents the line
     */
    public Geometries generateLine(Vector vTo, Vector vRight, Point p){

        double linesHeight = this.size / 50d; // change value?

        p = p.add(vUp.scale(linesHeight));

        Point A = p.add(vRight.scale(linesHeight / 2) // the middle of the triangle base
                   .add(vUp.scale(-linesHeight))); // the upper point of the triangle
        Point B = A.add(vTo.scale(size));

        Point secondHead = p.add(vTo.scale(size));

        /*
        Point C = secondHead.add(vRight.scale(-linesHeight)
                            .add(vUp.scale(-linesHeight)));
        */

        Point C = B.add(vRight.scale(-linesHeight));
        Point D = A.add(vRight.scale(-linesHeight));

        Geometry firstPart = new Polygon(p,A,B,secondHead).setEmission(boardColor).setMaterial(boardMat);
        Geometry secondPart = new Polygon(p,secondHead,C,D).setEmission(boardColor).setMaterial(boardMat);

        return new Geometries(firstPart,
                              secondPart,
                              new Triangle(A,D,p).setEmission(boardColor).setMaterial(boardMat),
                              new Triangle(B,C,secondHead).setEmission(boardColor).setMaterial(boardMat));

    }
    //endregion

    //region generateLines
    /**
     * explain
     * @return
     */
    public Geometries generateLines(){
        Geometries lines = generateLine(vTo, vRight, new Point(-size/6d, -size/2d, 0));
        lines.add(generateLine(vTo, vRight, new Point(size/6d, -size/2d, 0)));
        lines.add(generateLine(vRight, vTo.scale(-1), new Point(-size/2d, size/6d, 0)));
        lines.add(generateLine(vRight, vTo.scale(-1), new Point(-size/2d, -size/6d, 0)));
        return lines;
    }
    //endregion

    //region board
    /**
     *
     * @return
     * @param boardHeight
     */
    public Geometries generateBoard(double boardHeight){
        double coordinate = size / 2d;

        // 4 points for top base
        Point A = new Point (-coordinate, coordinate,0);
        Point B = new Point (coordinate, coordinate,0);
        Point C = new Point (coordinate, -coordinate,0);
        Point D = new Point (-coordinate, -coordinate,0);

        // 4 points for bottom base
        Point E = A.add(vUp.scale(-boardHeight));
        Point F = B.add(vUp.scale(-boardHeight));
        Point G = C.add(vUp.scale(-boardHeight));
        Point H = D.add(vUp.scale(-boardHeight));

        // two bases
        Geometry topBase = new Polygon(A,B,C,D).setEmission(boardColor).setMaterial(boardMat);
        Geometry bottomBase = new Polygon(E,F,G,H).setEmission(boardColor).setMaterial(boardMat);

        //4 sides
        Geometry side1 = generateSide(E,F, boardHeight).setEmission(boardColor).setMaterial(boardMat);
        Geometry side2 = generateSide(F,G, boardHeight).setEmission(boardColor).setMaterial(boardMat);
        Geometry side3 = generateSide(G,H, boardHeight).setEmission(boardColor).setMaterial(boardMat);
        Geometry side4 = generateSide(H,E, boardHeight).setEmission(boardColor).setMaterial(boardMat);

        Geometries lines = generateLines();
        lines.add(topBase, bottomBase,
                side1,side2,side3,side4);
        return lines;
    }
    //endregion

    //region X
    public Geometries generateX(Point A){
        double squareSize = alignZero(this.size / 3d);
        double totalLength = alignZero(squareSize * 0.7);
        double shapesHeight = totalLength * 0.5;



        return generateX(A, totalLength, shapesHeight);
    }


        /**
         * create an X in space
         * @param A first reference point - edge of the X
         * @return Geometry that has the sides and bases of a 3D X
         */
    private Geometries generateX(Point A, double totalLength, double height){
        Vector vTo = this.vTo.moveClockwiseAround(this.vUp, Math.random() * 25 + 45);
        Vector vRight = vTo.crossProduct(vUp).normalize();

        double width = totalLength * 0.25;
        double length = totalLength * 0.75 * 0.5;

        A = A.add(vTo.scale(-width / 2d))
                .add(vRight.scale(-totalLength / 2d));

        //region set points
        // long rectangle base
        Point B = A.add(vTo.scale(width));
        Point C = B.add(vRight.scale(totalLength));
        Point D = A.add(vRight.scale(totalLength));
        // short rectangle base
        Point E = B.add(vRight.scale(width + length));
        Point F = A.add(vRight.scale(width + length));
        Point G = A.add(vRight.scale(length));
        Point H = B.add(vRight.scale(length));

        Point I = H.add(vTo.scale(length));
        Point J = E.add(vTo.scale(length));
        Point K = F.add(vTo.scale(-length));
        Point L = G.add(vTo.scale(-length));
        //endregion

        //region x bottom base
        Geometry base1 = new Polygon(A,B,C,D)
                .setMaterial(xMat).setEmission(xColor.scale(0.5));
        Geometry base2 = new Polygon(E,H,I,J)
                .setMaterial(xMat).setEmission(xColor.scale(0.5));
        Geometry base3 = new Polygon(F,G,L,K)
                .setMaterial(xMat).setEmission(xColor.scale(0.5));
        Geometries XShape = new Geometries(base1, base2, base3); // bottom base
        //endregion

        //region x upper base
        // move up in vUp's direction
        base1 = new Polygon(A.add(this.vUp.scale(height)),B.add(this.vUp.scale(height)),
                C.add(this.vUp.scale(height)),D.add(this.vUp.scale(height)))
                .setMaterial(xMat).setEmission(xColor.scale(0.5));
        base2 = new Polygon(E.add(this.vUp.scale(height)),H.add(this.vUp.scale(height)),
                I.add(this.vUp.scale(height)),J.add(this.vUp.scale(height)))
                .setMaterial(xMat).setEmission(xColor.scale(0.5));
        base3 = new Polygon(F.add(this.vUp.scale(height)),G.add(this.vUp.scale(height)),
                L.add(this.vUp.scale(height)),K.add(this.vUp.scale(height)))
                .setMaterial(xMat).setEmission(xColor.scale(0.5));

        XShape.add(base1, base2, base3); // upper base
        //endregion

        //region x sides
        // side 1
        XShape.add(generateSide(A,B,height)
                .setMaterial(xMat).setEmission(xColor));
        // side 2
        XShape.add(generateSide(B,H,height)
                .setMaterial(xMat).setEmission(xColor));
        // side 3
        XShape.add(generateSide(H,I,height)
                .setMaterial(xMat).setEmission(xColor));
        // side 4
        XShape.add(generateSide(I,J,height)
                .setMaterial(xMat).setEmission(xColor));
        // side 5
        XShape.add(generateSide(J,E,height)
                .setMaterial(xMat).setEmission(xColor));
        // side 6
        XShape.add(generateSide(E,C,height)
                .setMaterial(xMat).setEmission(xColor));
        // side 7
        XShape.add(generateSide(C,D,height)
                .setMaterial(xMat).setEmission(xColor));
        // side 8
        XShape.add(generateSide(D,F,height)
                .setMaterial(xMat).setEmission(xColor));
        // side 9
        XShape.add(generateSide(F,K,height)
                .setMaterial(xMat).setEmission(xColor));
        // side 10
        XShape.add(generateSide(K,L,height)
                .setMaterial(xMat).setEmission(xColor));
        // side 11
        XShape.add(generateSide(L,G,height)
                .setMaterial(xMat).setEmission(xColor));
        // side 12
        XShape.add(generateSide(G,A,height)
                .setMaterial(xMat).setEmission(xColor));
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

    //region O
    public Geometries generateO(Point center) {
        double squareSize = this.size / 3d;
        double koter = squareSize * 0.7;
        double shapesHeight = koter * 0.5;

        return generateO(center, koter, shapesHeight);
    }


    public Geometries generateO(Point center, double radius, double height) {
        return new Geometries(new Cylinder(new Ray(center, vUp), radius, height)
                .setMaterial(oMat)
                .setEmission(oColor));
    }
    //endregion

    //region tic-tac-toe
    public Geometries generateTicTacToe(){

        double squareSize = this.size / 3d;
        double radius = squareSize * 0.35;
        double shapesHeight = radius * 0.5;
        double boardHeight = this.size * 0.05;

        Geometries TTT = new Geometries(generateBoard(boardHeight));

        // 3 o
        Point centerSquare = middleOfSquare(squareSize,0, 0); //
        TTT.add(generateO(centerSquare, radius, shapesHeight));
        centerSquare = middleOfSquare(squareSize,2, 2); //
        TTT.add(generateO(centerSquare, radius, shapesHeight));
        centerSquare = middleOfSquare(squareSize,1, 0); //
        TTT.add(generateO(centerSquare, radius, shapesHeight));


        // 3 x
        centerSquare = middleOfSquare(squareSize,1, 1); //
        TTT.add(generateX(centerSquare, radius * 2 , shapesHeight));
        centerSquare = middleOfSquare(squareSize,2, 0); //
        TTT.add(generateX(centerSquare, radius * 2 , shapesHeight));

        return TTT; //
    }


    // same method from the camera
    private Point middleOfSquare(double squareSize, int i, int j) {
        // calculating the middle of the "pixel"
        Point pc = new Point(0,0,0);


        double Ry = alignZero(squareSize);                      // Ratio - pixel height
        double Rx = alignZero(squareSize);                       // Ratio - pixel width

        double yI = alignZero(-(i - (3 - 1) / 2d) * Ry);       // move pc Yi pixels
        double xJ = alignZero((j - (3 - 1) / 2d) * Rx);        // move pc Xj pixels

        Point PIJ = pc;
        if(!isZero(xJ))  PIJ = PIJ.add(vRight.scale(xJ));       // move the point in vRight direction
        if(!isZero(yI))  PIJ = PIJ.add(vTo.scale(yI));          // move the point in vUp direction
        return PIJ;
    }
    //endregion

}
