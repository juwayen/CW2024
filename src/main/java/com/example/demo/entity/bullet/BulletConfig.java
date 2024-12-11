package com.example.demo.entity.bullet;

import com.example.demo.entity.Cloneable;
import com.example.demo.entity.plane.Plane;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class BulletConfig implements Cloneable {
    private final List<Image> destroyedImages;

    private Vector offset;
    private Vector direction;
    private Plane shooter;
    private Image image;
    private double speed;
    private int damage;

    public Vector getOffset() {
        return offset;
    }

    public void setOffset(Vector offset) {
        this.offset = offset;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public Plane getShooter() {
        return shooter;
    }

    public void setShooter(Plane shooter) {
        this.shooter = shooter;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
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
