package main.model;

import java.util.Random;


public class Shape {

    private final int specialChance = 2;
    public enum possibleShape {
        noShape, zShape, sShape, line, tShape, square, lShape, mirroredLShape, superVShape, superHShape
    }

    private possibleShape shapeOfPiece;
    private final int[][] coordinates;

    public Shape() {
        coordinates = new int[4][2];
        setShape(possibleShape.noShape);
    }

    public void setShape(possibleShape shape) {
        int[][][] coordinatesTable = new int[][][]{
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}},
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}}
        };
        for (int i = 0; i < 4; ++i) {
            System.arraycopy(coordinatesTable[shape.ordinal()], 0, coordinates, 0, 4);
        }
        shapeOfPiece = shape;
    }

    private void setX(int index, int x) {
        coordinates[index][0] = x;
    }

    private void setY(int index, int y) {
        coordinates[index][1] = y;
    }

    public int getX(int index) {
        return coordinates[index][0];
    }

    public int getY(int index) {
        return coordinates[index][1];
    }

    public possibleShape getShape() {
        return shapeOfPiece;
    }

    public void setRandomShape() {
        var rnd = new Random();
        int shapeNr = rnd.nextInt(7) + 1;

        int sup = rnd.nextInt(specialChance) + 1;

        possibleShape[] values = possibleShape.values();
        if (sup == 1) {
            setShape(values[8]);
        } else if (sup == 2) {
            setShape(values[9]);
        } else {
            setShape(values[shapeNr]);
        }

    }

    public int getMinX() {
        int minX = coordinates[0][0];

        for (int i = 1; i < 4; ++i) {
            minX = Math.min(coordinates[i][0], minX);
        }

        return minX;
    }

    public int getMinY() {
        int minY = coordinates[0][1];

        for (int i = 1; i < 4; ++i) {
            minY = Math.min(coordinates[i][1], minY);
        }

        return minY;
    }

    public Shape rotateLeft() {
        if (shapeOfPiece == possibleShape.square || shapeOfPiece == possibleShape.superHShape || shapeOfPiece == possibleShape.superVShape) {
            return this;
        }

        Shape result = new Shape();
        result.shapeOfPiece = shapeOfPiece;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, getY(i));
            result.setY(i, -getX(i));
        }

        return result;
    }

    public Shape rotateRight() {
        if (shapeOfPiece == possibleShape.square) {
            return this;
        }

        Shape result = new Shape();
        result.shapeOfPiece = shapeOfPiece;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, -getY(i));
            result.setY(i, getX(i));
        }

        return result;
    }

}