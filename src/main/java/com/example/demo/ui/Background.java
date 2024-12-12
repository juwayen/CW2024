package com.example.demo.ui;

import com.example.demo.service.UpdateService;
import com.example.demo.service.ServiceLocator;
import com.example.demo.service.Updatable;
import com.example.demo.util.ImageUtils;
import javafx.scene.image.ImageView;

import static com.example.demo.service.UpdateService.MILLISECOND_DELAY;

/**
 * Represents a visual background component that scrolls vertically to simulate a continuous animated background.
 */
public class Background extends ImageView implements Updatable {
    private static final String IMAGE_NAME = "background.png";
    private static final double SCROLL_SPEED = 0.24;
    private static final int IMAGE_EXTRA_HEIGHT = 1024;
    private static final int REPEATING_TILE_HEIGHT = 512;

    private final UpdateService updateService;
    private final double startingTranslateY;
    private final double endingTranslateY;

    /**
     * Constructs a new instance, initializing its visual appearance and configuration for vertical scrolling.
     */
    public Background() {
        this.updateService = ServiceLocator.getUpdateService();
        this.startingTranslateY = -IMAGE_EXTRA_HEIGHT;
        this.endingTranslateY = -REPEATING_TILE_HEIGHT;

        setImage(ImageUtils.getImageFromName(IMAGE_NAME));
        setTranslateY(startingTranslateY);

        initializeUpdate();
    }

    /**
     * Initializes the background component by registering it to the {@link UpdateService}.
     */
    private void initializeUpdate() {
        updateService.addToLoop(this);
    }

    /**
     * Updates the vertical position of the background to simulate scrolling.
     * Resets the translation back to the starting position, when appropriate, creating a looping effect.
     */
    @Override
    public void update() {
        double translateY = getTranslateY();

        if (translateY >= endingTranslateY)
            setTranslateY(startingTranslateY);

        setTranslateY(getTranslateY() + SCROLL_SPEED * MILLISECOND_DELAY);
    }
}
