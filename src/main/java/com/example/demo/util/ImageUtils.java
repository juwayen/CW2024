package com.example.demo.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;

import static com.example.demo.service.GameLoopService.MILLISECOND_DELAY;

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

    public static Vector getImageCenterOffset(Image image) {
        Vector imageSize = new Vector(image.getWidth(), image.getHeight());
        return imageSize.divide(2.0);
    }

    public static void addImageToTimeline(Timeline timeline, Image image, ImageView imageView) {
        KeyFrame keyFrame1 = new KeyFrame(
                Duration.ZERO,
                event -> imageView.setImage(image)
        );

        KeyFrame keyFrame2 = new KeyFrame(
                Duration.millis(MILLISECOND_DELAY)
        );

        timeline.getKeyFrames().add(keyFrame1);
        timeline.getKeyFrames().add(keyFrame2);
    }

    public static void addImagesToTimeline(Timeline timeline, List<Image> images, ImageView imageView, int framesPerImage) {
        int imagesCount = images.size();

        for (int i = 0; i < imagesCount; i++) {
            int index = i;

            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(MILLISECOND_DELAY * framesPerImage * i),
                    event -> imageView.setImage(images.get(index))
            );

            timeline.getKeyFrames().add(keyFrame);
        }

        KeyFrame keyFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY * framesPerImage * imagesCount));

        timeline.getKeyFrames().add(keyFrame);
    }
}
