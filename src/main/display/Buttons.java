package main.display;

import main.game.Tetris;

public class Buttons {
    public static final int BUTTON_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 50;

    private static final int PLAY_X = Tetris.WINDOW_WIDTH / 2 - BUTTON_WIDTH / 2;
    private static final int PLAY_Y = Tetris.WINDOW_HEIGHT / 2;
    private static final int QUIT_X = PLAY_X;
    private static final int QUIT_Y = PLAY_Y + BUTTON_HEIGHT * 2;

    private static final int NEW_GAME_WIDTH = 240;
    private static final int NEW_GAME_HEIGHT = 60;
    private static final int NEW_GAME_OFFSET = 50;
    private static final int QUIT_GAME_WIDTH = 120;
    private static final int QUIT_GAME_HEIGHT = 30;
    private static final int BUTTON_STR_OFFSET = 40;

    public static boolean wasPlayClicked(int mouseX, int mouseY) {
        return (Tetris.getGState() == Tetris.STATE.MENU || Tetris.getGState() == Tetris.STATE.END) &&
                mouseX >= PLAY_X && mouseX <= PLAY_X + BUTTON_WIDTH &&
                mouseY >= PLAY_Y && mouseY <= PLAY_Y + BUTTON_HEIGHT;
    }

    public static boolean wasQuitClicked(int mouseX, int mouseY) {
        return (Tetris.getGState() == Tetris.STATE.MENU || Tetris.getGState() == Tetris.STATE.END) &&
                mouseX >= QUIT_X && mouseX <= QUIT_X + BUTTON_WIDTH &&
                mouseY >= QUIT_Y && mouseY <= QUIT_Y + BUTTON_HEIGHT;
    }

}
