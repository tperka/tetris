package main.display;

import java.awt.*;

import static main.display.Buttons.BUTTON_HEIGHT;
import static main.display.Buttons.BUTTON_WIDTH;
import static main.game.Tetris.WINDOW_HEIGHT;
import static main.game.Tetris.WINDOW_WIDTH;

public class Draw {

    public static final int BUTTON_STR_OFFSET = 40;
    public static final int GO_BACK_TEST_OFFSET_X = 80;
    public static final int FONT_SIZE = 50;
    public static final int SMALL_FONT = 20;

    public static void drawMenu(Graphics2D g) {

        g.setFont(new Font("cambria", Font.BOLD, FONT_SIZE));

        g.setColor(Color.ORANGE);
        g.drawString("TETRIS", WINDOW_WIDTH / 2 - 80, 100);
        g.setFont(new Font("cambria", Font.BOLD, SMALL_FONT));
        g.drawString("by Tymoteusz Perka", WINDOW_WIDTH - 240, 130);
        g.setFont(new Font("cambria", Font.BOLD, FONT_SIZE));

        Rectangle playButton = new Rectangle(WINDOW_WIDTH / 2 - BUTTON_WIDTH / 2, WINDOW_HEIGHT / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        Rectangle quitButton = new Rectangle(WINDOW_WIDTH / 2 - BUTTON_WIDTH / 2, WINDOW_HEIGHT / 2 + BUTTON_HEIGHT * 2, BUTTON_WIDTH, BUTTON_HEIGHT);

        g.setColor(Color.BLUE);
        g.drawString("Play", playButton.x, playButton.y + BUTTON_STR_OFFSET);
        g.setColor(Color.RED);
        g.drawString("Quit", quitButton.x, quitButton.y + BUTTON_STR_OFFSET);

    }

    public static void drawEndMenu(Graphics2D g, int score) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("cambria", Font.BOLD, SMALL_FONT));
        g.drawString("Your last score: " + score, 80, 160);
        drawMenu(g);
    }
}
