package com.example.demo.entity.bullet;

import com.example.demo.entity.plane.Plane;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class BulletConfig {
    private final List<Image> destroyedImages;

    private Plane shooter;
    private Vector direction;
    private Vector offset;
    private Image image;
    private double speed;
    private int damage;

    public Plane getShooter() {
        return shooter;
    }

    public void setShooter(Plane shooter) {
        this.shooter = shooter;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Vector getOffset() {
        return offset;
    }

    public void setOffset(Vector offset) {
        this.offset = offset;
    }

    public Image getImage() {
        return image;
    }

    public void setImageName(Image image) {
        this.image = image;
    }

    public List<Image> getDestroyedImages() {
        return destroyedImages;
    }

    public void addDestroyedImage(Image image) {
        destroyedImages.add(image);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Vector getInitialPosition() {
        Vector imageCenter = ImageUtils.getImageCenterOffset(image);

        Vector initialPosition = shooter.getPosition().add(offset);

        return initialPosition.subtract(imageCenter);
    }

    public BulletConfig() {
        this.destroyedImages = new ArrayList<>();
    }
}
