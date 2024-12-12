package com.example.demo.screen;

/**
 * Represents the game over screen displayed when the game is lost.
 */
public class GameOverScreen extends GameScreen {
    private static final String LABEL_TEXT = "Game Over\nPress Any Key To Restart";

    /**
     * Constructs a new instance of {@link GameOverScreen} with the appropriate text.
     */
    public GameOverScreen() {
        super(LABEL_TEXT);
    }
}