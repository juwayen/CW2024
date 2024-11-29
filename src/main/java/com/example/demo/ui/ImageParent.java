package com.example.demo.ui;

import com.example.demo.Main;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

import static com.example.demo.Main.IMAGE_PATH;

public abstract class ImageParent extends ImageView {
    public ImageParent(String imageName, int height, double xPosition, double yPosition) {
        setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + imageName)).toExternalForm()));
        setPreserveRatio(true);
        setFitHeight(height);
        relocate(xPosition, yPosition);
    }

    public void moveToCenter() {
        Bounds bounds = getBoundsInParent();

        double imageWidth = bounds.getWidth();
        double imageHeight = bounds.getHeight();

        double x = ((double) Main.SCREEN_WIDTH / 2) - (imageWidth / 2);
        double y = ((double) Main.SCREEN_HEIGHT / 2) - (imageHeight / 2);

        relocate(x, y);
    }
}
