package com.example.demo.entity.bullet;

import com.example.demo.entity.plane.Plane;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;

public class BulletConfig {
    private Plane shooter;
    private Vector direction;
    private Vector offset;
    private String imageName;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
        Vector imageCenter = ImageUtils.getImageCenter(imageName);

        Vector initialPosition = shooter.getPosition().add(offset);

        return initialPosition.subtract(imageCenter);
    }

    public boolean getIsFriendly() {
        return shooter.isFriendly();
    }
}
