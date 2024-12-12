package com.example.demo.screen;

/**
 * Represents the end screen displayed when the third level of the game is completed.
 */
public class LevelThreeEndScreen extends GameScreen {
    private static final String LABEL_TEXT = "Mission Three Complete\nPress Any Key To Continue";

    /**
     * Constructs a new instance of {@link GameOverScreen} with the appropriate text.
     */
    public LevelThreeEndScreen() {
        super(LABEL_TEXT);
    }
}