package com.example.demo.entity.plane;

import com.example.demo.entity.Cloneable;
import com.example.demo.entity.bullet.Bullet;
import com.example.demo.entity.bullet.BulletConfig;
import com.example.demo.util.Vector;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the data for a {@link Plane}.
 */
public class PlaneData implements Cloneable {
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

    /**
     * Getter method for the {@link List} of {@link Image}s used to represent {@link com.example.demo.entity.state.PlaneMovingStraightState}.
     *
     * @return A {@link List} of {@link Image} objects representing {@link com.example.demo.entity.state.PlaneMovingStraightState}.
     */
    public List<Image> getMovingStraightImages() {
        return movingStraightImages;
    }

    /**
     * Adds an {@link Image} to the {@link List} of {@link Image}s used to represent {@link com.example.demo.entity.state.PlaneMovingStraightState}.
     *
     * @param image An {@link Image} representing one frame of the animation.
     */
    public void addMovingStraightImage(Image image) {
        movingStraightImages.add(image);
    }

    /**
     * Getter method for the {@link List} of {@link Image}s used to represent {@link com.example.demo.entity.state.PlaneMovingRightState}.
     *
     * @return A {@link List} of {@link Image} objects representing {@link com.example.demo.entity.state.PlaneMovingRightState}.
     */
    public List<Image> getMovingRightImages() {
        return movingRightImages;
    }

    /**
     * Adds an {@link Image} to the {@link List} of {@link Image}s used to represent {@link com.example.demo.entity.state.PlaneMovingRightState}.
     *
     * @param image An {@link Image} representing one frame of the animation.
     */
    public void addMovingRightImage(Image image) {
        movingRightImages.add(image);
    }

    /**
     * Getter method for the {@link List} of {@link Image}s used to represent {@link com.example.demo.entity.state.PlaneMovingLeftState}.
     *
     * @return A {@link List} of {@link Image} objects representing {@link com.example.demo.entity.state.PlaneMovingLeftState}.
     */
    public List<Image> getMovingLeftImages() {
        return movingLeftImages;
    }

    /**
     * Adds an {@link Image} to the {@link List} of {@link Image}s used to represent {@link com.example.demo.entity.state.PlaneMovingLeftState}.
     *
     * @param image An {@link Image} representing one frame of the animation.
     */
    public void addMovingLeftImage(Image image) {
        movingLeftImages.add(image);
    }

    /**
     * Getter method for the {@link List} of {@link Image}s used to represent {@link com.example.demo.entity.state.PlaneDestroyedState}.
     *
     * @return A {@link List} of {@link Image} objects representing {@link com.example.demo.entity.state.PlaneDestroyedState}.
     */
    public List<Image> getDestroyedImages() {
        return destroyedImages;
    }

    /**
     * Adds an {@link Image} to the {@link List} of {@link Image}s used to represent {@link com.example.demo.entity.state.PlaneDestroyedState}.
     *
     * @param image An {@link Image} representing one frame of the animation.
     */
    public void addDestroyedImage(Image image) {
        destroyedImages.add(image);
    }

    /**
     * Getter method for the initial position.
     *
     * @return The {@link Vector} representing the initial position.
     */
    public Vector getInitialPosition() {
        return initialPosition;
    }

    /**
     * Setter method for the initial position.
     *
     * @param initialPosition the {@link Vector} representing the new initial position.
     */
    public void setInitialPosition(Vector initialPosition) {
        this.initialPosition = initialPosition;
    }

    /**
     * Getter method for the final position.
     *
     * @return The {@link Vector} representing the final position.
     */
    public Vector getFinalPosition() {
        return finalPosition;
    }

    /**
     * Setter method for the final position.
     *
     * @param finalPosition The {@link Vector} representing the new final position.
     */
    public void setFinalPosition(Vector finalPosition) {
        this.finalPosition = finalPosition;
    }

    /**
     * Getter method for the health.
     *
     * @return The health of the plane.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setter method for the health.
     *
     * @param health The new health value.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Getter method for the minimum time interval in milliseconds between firing bullets.
     *
     * @return The minimum number of milliseconds that must elapse between two consecutive bullets.
     */
    public double getMinMillisecondsPerFire() {
        return minMillisecondsPerFire;
    }

    /**
     * Setter method for the minimum time interval in milliseconds between firing bullets.
     *
     * @param minMillisecondsPerFire The minimum number of milliseconds that must elapse between two consecutive bullets.
     */
    public void setMinMillisecondsPerFire(double minMillisecondsPerFire) {
        this.minMillisecondsPerFire = minMillisecondsPerFire;
    }

    /**
     * Getter method for the maximum time interval in milliseconds between firing bullets.
     *
     * @return The maximum number of milliseconds that can elapse between two consecutive bullets.
     */
    public double getMaxMillisecondsPerFire() {
        return maxMillisecondsPerFire;
    }

    /**
     * Setter method for the maximum time interval in milliseconds between firing bullets.
     *
     * @param maxMillisecondsPerFire The maximum number of milliseconds that must elapse between two consecutive bullets.
     */
    public void setMaxMillisecondsPerFire(double maxMillisecondsPerFire) {
        this.maxMillisecondsPerFire = maxMillisecondsPerFire;
    }

    /**
     * Getter method for the speed.
     *
     * @return The current speed of the plane.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Setter method for the speed.
     *
     * @param speed The new speed of the plane.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Getter method for the {@link BulletConfig}.
     *
     * @return The {@link BulletConfig} object.
     */
    public BulletConfig getBulletConfig() {
        return bulletConfig;
    }

    /**
     * Setter method for the {@link BulletConfig}.
     *
     * @param bulletConfig The new {@link BulletConfig} object.
     */
    public void setBulletConfig(BulletConfig bulletConfig) {
        this.bulletConfig = bulletConfig;
    }

    /**
     * Constructor for the {@link PlaneData}.
     */
    public PlaneData() {
        this.movingStraightImages = new ArrayList<>();
        this.movingRightImages = new ArrayList<>();
        this.movingLeftImages = new ArrayList<>();
        this.destroyedImages = new ArrayList<>();
    }

    /**
     * Creates and returns a deep copy of the current {@link PlaneData} instance.
     */
    @Override
    public PlaneData cloneObject() {
        PlaneData clonedPlaneData = new PlaneData();

        for (Image image : this.getMovingStraightImages())
            clonedPlaneData.addMovingStraightImage(image);

        for (Image image : this.getMovingRightImages())
            clonedPlaneData.addMovingRightImage(image);

        for (Image image : this.getMovingLeftImages())
            clonedPlaneData.addMovingLeftImage(image);

        for (Image image : this.getDestroyedImages())
            clonedPlaneData.addDestroyedImage(image);

        clonedPlaneData.setInitialPosition(this.getInitialPosition());
        clonedPlaneData.setFinalPosition(this.getFinalPosition());
        clonedPlaneData.setHealth(this.getHealth());
        clonedPlaneData.setMinMillisecondsPerFire(this.getMinMillisecondsPerFire());
        clonedPlaneData.setMaxMillisecondsPerFire(this.getMaxMillisecondsPerFire());
        clonedPlaneData.setSpeed(this.getSpeed());
        clonedPlaneData.setBulletConfig(this.getBulletConfig().cloneObject());

        return clonedPlaneData;
    }
}
