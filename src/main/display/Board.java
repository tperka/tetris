package main.display;

import main.game.Tetris;
import main.model.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel {

    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 22;
    private final int PERIOD_INTERVAL = 300;

    private final Timer timer;
    private boolean isFallingFinished = false;
    private boolean isPaused = false;
    private int numLinesRemoved = 0;
    private int curX = 0;
    private int curY = 0;
    private JLabel statusbar;
    private Shape curPiece;
    private Shape.possibleShape[] board;
    private final Menu menu;

    public Board(Tetris parent) {
        menu = new Menu(this);
        addMouseListener(menu);
        addKeyListener(new TAdapter());
        timer = new Timer(PERIOD_INTERVAL, new GameCycle());
        initBoard(parent);
    }

    private void initBoard(Tetris parent) {

        setFocusable(true);
        statusbar = parent.getStatusBar();
    }

    private int squareWidth() {

        return (int) getSize().getWidth() / BOARD_WIDTH;
    }

    private int squareHeight() {

        return (int) getSize().getHeight() / BOARD_HEIGHT;
    }

    private Shape.possibleShape shapeAt(int x, int y) {

        return board[(y * BOARD_WIDTH) + x];
    }

    public void start() {

        curPiece = new Shape();
        board = new Shape.possibleShape[BOARD_WIDTH * BOARD_HEIGHT];

        clearBoard();
        newPiece();
        statusbar.setText("Current score: " + numLinesRemoved);

        timer.start();
    }

    private void pause() {

        isPaused = !isPaused;

        if (isPaused) {

            statusbar.setText("paused");
        } else {

            statusbar.setText("Current score: " + numLinesRemoved);
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (Tetris.getGState() == Tetris.STATE.MENU) {
            statusbar.setText("");
            Draw.drawMenu(g2);
        }
        if (Tetris.getGState() == Tetris.STATE.END) {
            statusbar.setText("");
            Draw.drawEndMenu(g2, numLinesRemoved);
        }
        if (Tetris.getGState() == Tetris.STATE.GAME) {
            doDrawing(g);
        }
    }

    private void doDrawing(Graphics g) {

        var size = getSize();
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * squareHeight();

        for (int i = 0; i < BOARD_HEIGHT; i++) {

            for (int j = 0; j < BOARD_WIDTH; j++) {

                Shape.possibleShape shape = shapeAt(j, BOARD_HEIGHT - i - 1);

                if (shape != Shape.possibleShape.noShape) {

                    drawSquare(g, j * squareWidth(),
                            boardTop + i * squareHeight(), shape);
                }
            }
        }

        if (curPiece.getShape() != Shape.possibleShape.noShape) {

            for (int i = 0; i < 4; i++) {

                int x = curX + curPiece.getX(i);
                int y = curY - curPiece.getY(i);

                drawSquare(g, x * squareWidth(),
                        boardTop + (BOARD_HEIGHT - y - 1) * squareHeight(),
                        curPiece.getShape());
            }
        }
    }

    private void dropDown() {

        int newY = curY;

        while (newY > 0) {

            if (!tryMove(curPiece, curX, newY - 1)) {

                break;
            }

            newY--;
        }

        pieceDropped();
    }

    private void oneLineDown() {

        if (!tryMove(curPiece, curX, curY - 1)) {

            pieceDropped();
        }
    }

    private void clearBoard() {

        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {

            board[i] = Shape.possibleShape.noShape;
        }
    }

    private void pieceDropped() {

        for (int i = 0; i < 4; i++) {

            int x = curX + curPiece.getX(i);
            int y = curY - curPiece.getY(i);
            board[(y * BOARD_WIDTH) + x] = curPiece.getShape();
        }
        if (curPiece.getShape() == Shape.possibleShape.superVShape) {
            superVFallen(curX, curY);
        }

        if (curPiece.getShape() == Shape.possibleShape.superHShape) {
            superHFallen(curX, curY);
        }

        removeFullLines();

        if (!isFallingFinished) {

            newPiece();
        }
    }

    private void newPiece() {

        curPiece.setRandomShape();
        curX = BOARD_WIDTH / 2 + 1;
        curY = BOARD_HEIGHT - 1 + curPiece.getMinY();

        if (!tryMove(curPiece, curX, curY)) {

            timer.stop();

            Tetris.setState(Tetris.STATE.END);
        }
    }

    private boolean tryMove(Shape newPiece, int newX, int newY) {

        for (int i = 0; i < 4; i++) {

            int x = newX + newPiece.getX(i);
            int y = newY - newPiece.getY(i);

            if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {

                return false;
            }

            if (shapeAt(x, y) != Shape.possibleShape.noShape) {

                return false;
            }
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;

        repaint();

        return true;
    }

    private void superVFallen(int x, int y) {
        int counter = 4;
        for (int i = y; i >= 0; i--) {
            for (int j = x; j <= x + 1; j++) {
                if (board[(i * BOARD_WIDTH) + j] == Shape.possibleShape.noShape && counter > 0) {
                    board[(i * BOARD_WIDTH) + j] = Shape.possibleShape.superVShape;
                    counter--;
                }
            }
        }
    }

    private void superHFallen(int x, int y) {
        int counter = 4;
        for (int i = y - 1; i <= y; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[(i * BOARD_WIDTH) + j] == Shape.possibleShape.noShape && counter > 0) {
                    board[(i * BOARD_WIDTH) + j] = Shape.possibleShape.superHShape;
                    counter--;
                }
            }
        }
    }

    private void removeFullLines() {

        int numFullLines = 0;

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {

            boolean lineIsFull = true;

            for (int j = 0; j < BOARD_WIDTH; j++) {

                if (shapeAt(j, i) == Shape.possibleShape.noShape) {

                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {

                numFullLines++;

                for (int k = i; k < BOARD_HEIGHT - 1; k++) {
                    for (int j = 0; j < BOARD_WIDTH; j++) {
                        board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {

            numLinesRemoved += numFullLines;

            statusbar.setText("Current score: " + numLinesRemoved);
            isFallingFinished = true;
            curPiece.setShape(Shape.possibleShape.noShape);
        }
    }

    private void drawSquare(Graphics g, int x, int y, Shape.possibleShape shape) {

        Color[] colors = {new Color(0, 0, 0), new Color(200, 100, 100),
                new Color(100, 200, 100), new Color(100, 100, 200),
                new Color(200, 200, 200), new Color(200, 100, 200),
                new Color(100, 200, 200), new Color(218, 50, 0),
                new Color(255, 215, 0), new Color(255, 255, 255)
        };

        var color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private void doGameCycle() {

        update();
        repaint();
    }

    private void update() {

        if (isPaused || Tetris.getGState() == Tetris.STATE.MENU || Tetris.getGState() == Tetris.STATE.END) {

            return;
        }

        if (isFallingFinished) {

            isFallingFinished = false;
            newPiece();
        } else {

            oneLineDown();
        }
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (curPiece.getShape() == Shape.possibleShape.noShape) {

                return;
            }

            int keycode = e.getKeyCode();

            switch (keycode) {

                case KeyEvent.VK_P:
                    pause();
                    break;
                case KeyEvent.VK_LEFT:
                    tryMove(curPiece, curX - 1, curY);
                    break;
                case KeyEvent.VK_RIGHT:
                    tryMove(curPiece, curX + 1, curY);
                    break;
                case KeyEvent.VK_DOWN:
                    tryMove(curPiece.rotateRight(), curX, curY);
                    break;
                case KeyEvent.VK_UP:
                    tryMove(curPiece.rotateLeft(), curX, curY);
                    break;
                case KeyEvent.VK_SPACE:
                    dropDown();
                    break;
                case KeyEvent.VK_D:
                    oneLineDown();
                    break;
                case KeyEvent.VK_M:
                    Tetris.setState(Tetris.STATE.MENU);
                    break;
            }
        }

    }
}
