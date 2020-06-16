package test.model;

import main.model.Shape;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTest {

    private Shape shape;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        shape = new Shape();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.out.println("Shape test successful");
    }

    @org.junit.jupiter.api.Test
    void getShape() {
        assertEquals(shape.getShape(), Shape.possibleShape.noShape);
    }

    @org.junit.jupiter.api.Test
    void setShape() {
        assertEquals(shape.getShape(), Shape.possibleShape.noShape);
        shape.setShape(Shape.possibleShape.lShape);
        assertEquals(shape.getShape(), Shape.possibleShape.lShape);
        shape.setShape(Shape.possibleShape.superVShape);
        assertEquals(shape.getShape(), Shape.possibleShape.superVShape);
    }

    @org.junit.jupiter.api.Test
    void getX() {
        shape.setShape(Shape.possibleShape.square);
        assertEquals(shape.getX(0), 0);
        assertEquals(shape.getX(1), 1);
        assertEquals(shape.getX(2), 0);
        assertEquals(shape.getX(3), 1);
        shape.setShape(Shape.possibleShape.zShape);
        assertEquals(shape.getX(0), 0);
        assertEquals(shape.getX(1), 0);
        assertEquals(shape.getX(2), -1);
        assertEquals(shape.getX(3), -1);
    }

    @org.junit.jupiter.api.Test
    void getY() {
        shape.setShape(Shape.possibleShape.square);
        assertEquals(shape.getY(0), 0);
        assertEquals(shape.getY(1), 0);
        assertEquals(shape.getY(2), 1);
        assertEquals(shape.getY(3), 1);
        shape.setShape(Shape.possibleShape.zShape);
        assertEquals(shape.getY(0), -1);
        assertEquals(shape.getY(1), 0);
        assertEquals(shape.getY(2), 0);
        assertEquals(shape.getY(3), 1);
    }


    @org.junit.jupiter.api.Test
    void setRandomShape() {
        shape.setRandomShape();
        assertNotEquals(shape.getShape(), Shape.possibleShape.noShape);
    }

    @org.junit.jupiter.api.Test
    void getMinX() {
        shape.setShape(Shape.possibleShape.square);
        assertEquals(shape.getMinX(), 0);
        shape.setShape(Shape.possibleShape.zShape);
        assertEquals(shape.getMinX(), -1);
    }

    @org.junit.jupiter.api.Test
    void getMinY() {
        shape.setShape(Shape.possibleShape.square);
        assertEquals(shape.getMinY(), 0);
        shape.setShape(Shape.possibleShape.zShape);
        assertEquals(shape.getMinY(), -1);
    }

    @org.junit.jupiter.api.Test
    void rotateLeft() {
        Shape temp = new Shape();
        shape.rotateLeft();
        for(int i = 0; i < 4; ++i){
            assertEquals(temp.getX(i), shape.getX(i));
            assertEquals(temp.getY(i), shape.getY(i));
        }
        shape.setShape(Shape.possibleShape.sShape);
    }

    @org.junit.jupiter.api.Test
    void rotateRight() {
    }
}