package com.example.demo.service;

import javafx.geometry.Bounds;

public interface Collidable {
    Bounds getHitbox();
    void onCollision(Collidable collidable);
}
