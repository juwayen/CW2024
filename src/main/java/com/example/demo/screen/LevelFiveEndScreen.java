package com.example.demo.screen;

/**
 * Represents the end screen displayed when the fifth level of the game is completed.
 */
public class LevelFiveEndScreen extends GameScreen {
    private static final String LABEL_TEXT = "Mission Five Complete\nPress Any Key To Continue";

    /**
     * Constructs a new instance of {@link GameOverScreen} with the appropriate text.
     */
    public LevelFiveEndScreen() {
        super(LABEL_TEXT);
    }
}