package com.example.demo.entity.bullet;

import com.example.demo.entity.Cloneable;
import com.example.demo.entity.plane.Plane;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the configuration for a {@link Bullet}.
 */
public class BulletConfig implements Cloneable {
    private final List<Image> destroyedImages;

    private Vector offset;
    private Vector direction;
    private Plane shooter;
    private Image image;
    private double speed;
    private int damage;

    /**
     * Getter method for the {@link List} of {@link Image}s used to represent {@link com.example.demo.entity.state.BulletDestroyedState}.
     *
     * @return A {@link List} of {@link Image} objects representing {@link com.example.demo.entity.state.BulletDestroyedState}.
     */
    public List<Image> getDestroyedImages() {
        return destroyedImages;
    }

    /**
     * Adds an {@link Image} to the {@link List} of {@link Image}s used to represent {@link com.example.demo.entity.state.BulletDestroyedState}.
     *
     * @param image An {@link Image} representing one frame of the animation.
     */
    public void addDestroyedImage(Image image) {
        destroyedImages.add(image);
    }

    /**
     * Getter method for the offset.
     *
     * @return The offset as a {@link Vector}.
     */
    public Vector getOffset() {
        return offset;
    }

    /**
     * Setter method for the offset.
     *
     * @param offset The {@link Vector} representing the new offset to be assigned.
     */
    public void setOffset(Vector offset) {
        this.offset = offset;
    }

    /**
     * Getter method for the direction.
     *
     * @return The {@link Vector} representing the direction.
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Setter method for the direction.
     *
     * @param direction The {@link Vector} representing the new direction to be assigned.
     */
    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    /**
     * Getter for the shooter.
     *
     * @return The {@link Plane} instance representing the shooter.
     */
    public Plane getShooter() {
        return shooter;
    }

    /**
     * Setter method for the shooter.
     *
     * @param shooter The {@link Plane} instance that will be associated as the shooter.
     */
    public void setShooter(Plane shooter) {
        this.shooter = shooter;
    }

    /**
     * Getter method for the {@link Image}.
     *
     * @return The {@link Image} object representing the visual appearance.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Setter method for the image.
     *
     * @param image The {@link Image} object to be used for the {@link Bullet}.
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Getter method for the speed.
     *
     * @return The speed of the bullet.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Setter method for the speed.
     *
     * @param speed The speed value to be assigned.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Getter method for the damage value.
     *
     * @return The damage value.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Setter method for the damage value.
     *
     * @param damage The damage value to be assigned.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Computes the initial position.
     *
     * @return A {@link Vector} representing the initial position.
     */
    public Vector getInitialPosition() {
        Vector imageCenter = ImageUtils.getImageCenterOffset(image);

        Vector initialPosition = shooter.getPosition().add(offset);

        return initialPosition.subtract(imageCenter);
    }

    /**
     * Constructs a new instance of {@link BulletConfig}.
     */
    public BulletConfig() {
        this.destroyedImages = new ArrayList<>();
    }

    /**
     * Creates and returns a deep copy of the current {@link BulletConfig} instance.
     */
    @Override
    public BulletConfig cloneObject() {
        BulletConfig clonedBulletConfig = new BulletConfig();

        for (Image image : this.getDestroyedImages())
            clonedBulletConfig.addDestroyedImage(image);

        clonedBulletConfig.setShooter(this.getShooter());
        clonedBulletConfig.setDirection(this.getDirection());
        clonedBulletConfig.setOffset(this.getOffset());
        clonedBulletConfig.setImage(this.getImage());
        clonedBulletConfig.setSpeed(this.getSpeed());
        clonedBulletConfig.setDamage(this.getDamage());

        return clonedBulletConfig;
    }
}
