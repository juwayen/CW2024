package com.example.demo.util;

import javafx.scene.image.Image;
import javafx.stage.Screen;

import java.util.Objects;

public class ImageUtils {
    public static final double OUTPUT_SCALE = Screen.getPrimary().getOutputScaleX();

    private static final String IMAGE_PATH = "/com/example/demo/images/";

    public static Image getImageFromName(String imageName) {
        String imageUrl = Objects.requireNonNull(ImageUtils.class.getResource(IMAGE_PATH + imageName)).toExternalForm();
        Image image = new Image(imageUrl);

        image = new Image(
                imageUrl,
                image.getWidth() / OUTPUT_SCALE,
                image.getHeight() / OUTPUT_SCALE,
                true,
                false
        );

        return image;
    }
}
