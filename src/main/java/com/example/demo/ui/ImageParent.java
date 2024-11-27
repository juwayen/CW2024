package com.example.demo.ui;

import com.example.demo.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public abstract class ImageParent extends ImageView {
    private static final String IMAGE_PATH = "/com/example/demo/images/";

    public ImageParent(String imageName, int height, double xPosition, double yPosition) {
        setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + imageName)).toExternalForm()));
        setVisible(false);
        setPreserveRatio(true);
        setFitHeight(height);
        setLayoutX(xPosition);
        setLayoutY(yPosition);
        setViewOrder(-1);
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    public void moveToCenter() {
        Image image = getImage();
        double widthToHeightRatio = image.getWidth() / image.getHeight();
        double imageHeight = getFitHeight();
        double imageWidth = imageHeight * widthToHeightRatio;
        double x = ((double) Main.SCREEN_WIDTH / 2) - (imageWidth / 2);
        double y = ((double) Main.SCREEN_HEIGHT / 2) - (imageHeight / 2);
        setLayoutX(x);
        setLayoutY(y);
    }
}
