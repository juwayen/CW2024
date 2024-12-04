package com.example.demo.screen;

import com.example.demo.util.ImageUtils;
import javafx.scene.image.ImageView;

import static com.example.demo.Main.OUTPUT_SCALE;

public class StartScreen extends GameScreen {
    private static final String LABEL_TEXT = "Press Any Key To Start";
    private static final double LABEL_TOP_PADDING = 192.0;
    private static final double IMAGE_TOP_PADDING = -58.0;

    public StartScreen() {
        super(LABEL_TEXT);

        initialize();
    }

    private void initialize() {
        ImageView startImage = new ImageView(ImageUtils.getImageFromName("start_screen_image.png"));
        getChildren().add(startImage);

        getLabel().setTranslateY(LABEL_TOP_PADDING / OUTPUT_SCALE);
        startImage.setTranslateY(IMAGE_TOP_PADDING / OUTPUT_SCALE);
    }
}