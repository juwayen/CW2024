package com.example.demo.ui;

import com.example.demo.util.ImageUtils;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

import static com.example.demo.Main.SCREEN_WIDTH;
import static com.example.demo.Main.SCREEN_HEIGHT;
import static com.example.demo.util.ImageUtils.OUTPUT_SCALE;

public abstract class ImageParent extends ImageView {
    public ImageParent(String imageName, int height, double xPosition, double yPosition) {
        setImage(ImageUtils.getImageFromName(imageName));
        setPreserveRatio(true);
        setFitHeight(height);
        setTranslateX(xPosition);
        setTranslateY(yPosition);
    }

    public void moveToCenter() {
        Bounds bounds = getBoundsInParent();

        double imageWidth = bounds.getWidth();
        double imageHeight = bounds.getHeight();

        double x = ((double) SCREEN_WIDTH / 2) - (imageWidth / 2);
        double y = ((double) SCREEN_HEIGHT / 2) - (imageHeight / 2);

        setTranslateX(x / OUTPUT_SCALE);
        setTranslateY(y / OUTPUT_SCALE);
    }
}
