package com.example.demo.ui;

import com.example.demo.controller.GameLoop;
import com.example.demo.entity.Updatable;
import com.example.demo.util.ImageUtils;
import javafx.scene.image.ImageView;

import static com.example.demo.controller.GameLoop.MILLISECOND_DELAY;
import static com.example.demo.util.ImageUtils.OUTPUT_SCALE;

public class Background extends ImageView implements Updatable {
    private final static String IMAGE_NAME = "background.png";
    private final static double SCROLL_SPEED = 0.24;
    private final static int IMAGE_EXTRA_HEIGHT = 1024;
    private final static int REPEATING_TILE_HEIGHT = 512;

    private final double startingTranslateY;
    private final double endingTranslateY;

    public Background() {
        this.startingTranslateY = -IMAGE_EXTRA_HEIGHT / OUTPUT_SCALE;
        this.endingTranslateY = -REPEATING_TILE_HEIGHT / OUTPUT_SCALE;

        setViewOrder(1);
        setImage(ImageUtils.getImageFromName(IMAGE_NAME));
        setTranslateY(startingTranslateY);

        initialize();
    }

    private void initialize() {
        GameLoop.addToLoop(this);
    }

    @Override
    public void update() {
        double translateY = getTranslateY();

        setTranslateY(translateY + SCROLL_SPEED / OUTPUT_SCALE * MILLISECOND_DELAY);

        if (translateY > endingTranslateY)
            setTranslateY(startingTranslateY);
    }
}
