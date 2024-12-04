package com.example.demo;

import com.example.demo.controller.Updatable;
import com.example.demo.util.ImageUtils;
import javafx.scene.image.ImageView;

import static com.example.demo.Main.OUTPUT_SCALE;
import static com.example.demo.controller.GameLoop.MILLISECOND_DELAY;

public class Background extends ImageView implements Updatable {
    private static final String IMAGE_NAME = "background.png";
    private static final double SCROLL_SPEED = 0.24;
    private static final int IMAGE_EXTRA_HEIGHT = 1024;
    private static final int REPEATING_TILE_HEIGHT = 512;

    private final double startingTranslateY;
    private final double endingTranslateY;

    public Background() {
        this.startingTranslateY = -IMAGE_EXTRA_HEIGHT / OUTPUT_SCALE;
        this.endingTranslateY = -REPEATING_TILE_HEIGHT / OUTPUT_SCALE;

        setImage(ImageUtils.getImageFromName(IMAGE_NAME));
        setTranslateY(startingTranslateY);

        initialize();
    }

    private void initialize() {
        addToGameLoop();
    }

    @Override
    public void update() {
        double translateY = getTranslateY();

        if (translateY >= endingTranslateY)
            setTranslateY(startingTranslateY);

        setTranslateY(getTranslateY() + SCROLL_SPEED / OUTPUT_SCALE * MILLISECOND_DELAY);
    }
}
