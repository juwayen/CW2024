package com.example.demo.controller;

import javafx.geometry.Bounds;

public interface Collidable {
    Bounds getHitbox();
    void onCollision(Collidable collidable);
}
