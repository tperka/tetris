package main.display;

import main.game.Tetris;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu implements MouseListener {


    Board board;

    public Menu(Board board) {
        this.board = board;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int mouseX = mouseEvent.getX();
        int mouseY = mouseEvent.getY();

        if (Buttons.wasPlayClicked(mouseX, mouseY)) {
            Tetris.setState(Tetris.STATE.GAME);
            board.start();

        }

        if (Buttons.wasQuitClicked(mouseX, mouseY)) {
            System.exit(1);
        }

    }


    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
