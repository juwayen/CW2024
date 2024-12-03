package com.example.demo.entity;

import com.example.demo.controller.GameLoop;

public interface Updatable {
    default void addToGameLoop() {
        GameLoop.addToLoop(this);
    }

    default void removeFromGameLoop() {
        GameLoop.removeFromLoop(this);
    }

    void update();
}
