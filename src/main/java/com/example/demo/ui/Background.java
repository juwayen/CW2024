package com.example.demo.ui;

import com.example.demo.service.UpdateService;
import com.example.demo.service.ServiceLocator;
import com.example.demo.service.Updatable;
import com.example.demo.util.ImageUtils;
import javafx.scene.image.ImageView;

import static com.example.demo.service.UpdateService.MILLISECOND_DELAY;

public class Background extends ImageView implements Updatable {
    private static final String IMAGE_NAME = "background.png";
    private static final double SCROLL_SPEED = 0.24;
    private static final int IMAGE_EXTRA_HEIGHT = 1024;
    private static final int REPEATING_TILE_HEIGHT = 512;

    private final UpdateService updateService;
    private final double startingTranslateY;
    private final double endingTranslateY;

    public Background() {
        this.updateService = ServiceLocator.getUpdateService();
        this.startingTranslateY = -IMAGE_EXTRA_HEIGHT;
        this.endingTranslateY = -REPEATING_TILE_HEIGHT;

        setImage(ImageUtils.getImageFromName(IMAGE_NAME));
        setTranslateY(startingTranslateY);

        initialize();
    }

    private void initialize() {
        updateService.addToLoop(this);
    }

    @Override
    public void update() {
        double translateY = getTranslateY();

        if (translateY >= endingTranslateY)
            setTranslateY(startingTranslateY);

        setTranslateY(getTranslateY() + SCROLL_SPEED * MILLISECOND_DELAY);
    }
}
