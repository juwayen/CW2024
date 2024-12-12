package com.example.demo.screen;

import com.example.demo.util.ImageUtils;
import javafx.scene.image.ImageView;

/**
 * Represents the start screen displayed at the beginning of the game.
 * This screen shows an image and a label prompting the user to press any key to start the game.
 */
public class StartScreen extends GameScreen {
    private static final String LABEL_TEXT = "Press Any Key To Start";
    private static final double LABEL_TOP_PADDING = 192.0;
    private static final double IMAGE_TOP_PADDING = -58.0;

    /**
     * Constructs a new instance of {@link GameOverScreen} with the appropriate text, and initializes the screen elements.
     */
    public StartScreen() {
        super(LABEL_TEXT);

        initializeElements();
    }

    /**
     * Initializes the visual elements of the start screen, including the main image and label positioning.
     */
    private void initializeElements() {
        ImageView startImage = new ImageView(ImageUtils.getImageFromName("start_screen_image.png"));
        getChildren().add(startImage);

        getLabel().setTranslateY(LABEL_TOP_PADDING);
        startImage.setTranslateY(IMAGE_TOP_PADDING);
    }
}