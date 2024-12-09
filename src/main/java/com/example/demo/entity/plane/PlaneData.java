package com.example.demo.entity.plane;

import com.example.demo.entity.bullet.BulletConfig;
import com.example.demo.util.Vector;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class PlaneData {
    private final List<Image> movingStraightImages;
    private final List<Image> movingRightImages;
    private final List<Image> movingLeftImages;
    private final List<Image> destroyedImages;

    private Vector initialPosition;
    private Vector finalPosition;
    private int health;
    private double minMillisecondsPerFire;
    private double maxMillisecondsPerFire;
    private double speed;
    private BulletConfig bulletConfig;

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

    public Vector getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(Vector initialPosition) {
        this.initialPosition = initialPosition;
    }

    public Vector getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(Vector finalPosition) {
        this.finalPosition = finalPosition;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getMinMillisecondsPerFire() {
        return minMillisecondsPerFire;
    }

    public void setMinMillisecondsPerFire(double minMillisecondsPerFire) {
        this.minMillisecondsPerFire = minMillisecondsPerFire;
    }

    public double getMaxMillisecondsPerFire() {
        return maxMillisecondsPerFire;
    }

    public void setMaxMillisecondsPerFire(double maxMillisecondsPerFire) {
        this.maxMillisecondsPerFire = maxMillisecondsPerFire;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public BulletConfig getBulletConfig() {
        return bulletConfig;
    }

    public void setBulletConfig(BulletConfig bulletConfig) {
        this.bulletConfig = bulletConfig;
    }

    public PlaneData() {
        this.movingStraightImages = new ArrayList<>();
        this.movingRightImages = new ArrayList<>();
        this.movingLeftImages = new ArrayList<>();
        this.destroyedImages = new ArrayList<>();
    }
}
