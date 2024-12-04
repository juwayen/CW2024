package com.example.demo.util;

import javafx.scene.image.Image;

import java.util.Objects;

import static com.example.demo.Main.OUTPUT_SCALE;

public class ImageUtils {
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

    public static Vector getImageCenter(String imageName) {
        Image image = getImageFromName(imageName);
        Vector imageSize = new Vector(image.getWidth(), image.getHeight()).multiply(OUTPUT_SCALE);
        return imageSize.divide(2.0);
    }
}
