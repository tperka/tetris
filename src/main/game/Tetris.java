package main.game;

import main.display.Board;

import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame {

    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 800;

    private JLabel statusbar;

    public static STATE getGState() {
        return state;
    }

    public static void setState(STATE state) {
        Tetris.state = state;
    }

    public enum STATE {
        MENU,
        GAME,
        END
    }

    private static STATE state = STATE.MENU;

    public Tetris() {

        initUI();
    }

    private void initUI() {

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);

        setTitle("Tetris");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        var board = new Board(this);
        add(board);
        //board.start();

    }

    public JLabel getStatusBar() {

        return statusbar;
    }

    public static void startGame() {
        EventQueue.invokeLater(() -> {

            var game = new Tetris();
            game.setVisible(true);
        });
    }

}
