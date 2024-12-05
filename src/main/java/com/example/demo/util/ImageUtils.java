package com.example.demo.util;

import javafx.scene.image.Image;

import java.util.Objects;

public class ImageUtils {
    private static final String IMAGE_PATH = "/com/example/demo/images/";

    public static Image getImageFromName(String imageName) {
        String imageUrl = Objects.requireNonNull(ImageUtils.class.getResource(IMAGE_PATH + imageName)).toExternalForm();
        Image image = new Image(imageUrl);

        image = new Image(
                imageUrl,
                image.getWidth(),
                image.getHeight(),
                true,
                false
        );

        return image;
    }

    public static Vector getImageCenter(String imageName) {
        Image image = getImageFromName(imageName);
        Vector imageSize = new Vector(image.getWidth(), image.getHeight());
        return imageSize.divide(2.0);
    }
}
