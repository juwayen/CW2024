package com.example.demo.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;

import static com.example.demo.service.UpdateService.MILLISECOND_DELAY;

/**
 * Utility class for image-related operations.
 */
public class ImageUtils {
    private static final String IMAGE_PATH = "/com/example/demo/images/";

    /**
     * Loads an image from the given image name.
     *
     * @param imageName The name of the image file to be loaded.
     * @return An {@link Image} object representing the loaded image.
     * @throws NullPointerException If the image resource cannot be found.
     */
    public static Image getImageFromName(String imageName) {
        String imageUrl = Objects.requireNonNull(ImageUtils.class.getResource(IMAGE_PATH + imageName)).toExternalForm();

        return new Image(imageUrl);
    }

    /**
     * Calculates the offset of the image center based on its size.
     *
     * @param image The {@link Image} for which the center offset is calculated.
     * @return A {@link Vector} object representing the offset from the top-left corner of the image to its center.
     */
    public static Vector getImageCenterOffset(Image image) {
        Vector imageSize = new Vector(image.getWidth(), image.getHeight());
        return imageSize.divide(2.0);
    }

    /**
     * Adds an image to the specified {@link Timeline}.
     *
     * @param timeline The {@link Timeline} to which the image will be added.
     * @param image The {@link Image} to be displayed in the {@link ImageView} during the timeline.
     * @param imageView The {@link ImageView} where the image will be displayed.
     */
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

    /**
     * Adds a series of images to a specified {@link Timeline} with a specified delay between each.
     *
     * @param timeline The {@link Timeline} to which the key frames will be added.
     * @param images A {@link List} of {@link Image} objects to be displayed in the timeline.
     * @param imageView The {@link ImageView} where the images will be displayed.
     * @param framesPerImage The number of frames between each image.
     */
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
