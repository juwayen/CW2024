package com.example.demo.screen;

/**
 * Represents the end screen displayed when the first level of the game is completed.
 */
public class LevelOneEndScreen extends GameScreen {
    private static final String LABEL_TEXT = "Mission One Complete\nPress Any Key To Continue";

    /**
     * Constructs a new instance of {@link GameOverScreen} with the appropriate text.
     */
    public LevelOneEndScreen() {
        super(LABEL_TEXT);
    }
}