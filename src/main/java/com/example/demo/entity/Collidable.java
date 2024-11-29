package com.example.demo.entity;

import javafx.geometry.Bounds;

public interface Collidable {
    void onCollision(Collidable collidable);
    Bounds getHitbox();
    boolean isFriendly();
}
