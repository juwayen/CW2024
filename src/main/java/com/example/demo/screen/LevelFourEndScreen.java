package com.example.demo.screen;

/**
 * Represents the end screen displayed when the fourth level of the game is completed.
 */
public class LevelFourEndScreen extends GameScreen {
    private static final String LABEL_TEXT = "Mission Four Complete\nPress Any Key To Continue";

    /**
     * Constructs a new instance of {@link GameOverScreen} with the appropriate text.
     */
    public LevelFourEndScreen() {
        super(LABEL_TEXT);
    }
}