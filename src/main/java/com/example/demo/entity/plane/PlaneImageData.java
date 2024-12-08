package com.example.demo.entity.plane;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class PlaneImageData {
    private final List<Image> movingStraightImages;
    private final List<Image> movingRightImages;
    private final List<Image> movingLeftImages;
    private final List<Image> destroyedImages;

    public List<Image> getMovingStraightImages() {
        return movingStraightImages;
    }

    public void addMovingStraightImage(Image image) {
        movingStraightImages.add(image);
    }

    public List<Image> getMovingRightImages() {
        return movingRightImages;
    }

    public void addMovingRightImage(Image image) {
        movingRightImages.add(image);
    }

    public List<Image> getMovingLeftImages() {
        return movingLeftImages;
    }

    public void addMovingLeftImage(Image image) {
        movingLeftImages.add(image);
    }

    public List<Image> getDestroyedImages() {
        return destroyedImages;
    }

    public void addDestroyedImage(Image image) {
        destroyedImages.add(image);
    }

    public PlaneImageData() {
        this.movingStraightImages = new ArrayList<>();
        this.movingRightImages = new ArrayList<>();
        this.movingLeftImages = new ArrayList<>();
        this.destroyedImages = new ArrayList<>();
    }
}
