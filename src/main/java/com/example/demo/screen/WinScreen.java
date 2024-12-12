package com.example.demo.screen;

/**
 * Represents the win screen displayed when the game is won.
 */
public class WinScreen extends GameScreen {
    private static final String LABEL_TEXT = "You Won!\nPress Any Key To Play Again";

    /**
     * Constructs a new instance of {@link GameOverScreen} with the appropriate text.
     */
    public WinScreen() {
        super(LABEL_TEXT);
    }
}