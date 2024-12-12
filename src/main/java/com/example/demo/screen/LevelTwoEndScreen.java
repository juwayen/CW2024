package com.example.demo.screen;

/**
 * Represents the end screen displayed when the second level of the game is completed.
 */
public class LevelTwoEndScreen extends GameScreen {
    private static final String LABEL_TEXT = "Mission Two Complete\nPress Any Key To Continue";

    /**
     * Constructs a new instance of {@link GameOverScreen} with the appropriate text.
     */
    public LevelTwoEndScreen() {
        super(LABEL_TEXT);
    }
}