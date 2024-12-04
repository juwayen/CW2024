package com.example.demo.controller;

public interface Updatable {
    default void addToGameLoop() {
        GameLoop.addToLoop(this);
    }

    default void removeFromGameLoop() {
        GameLoop.removeFromLoop(this);
    }

    void update();
}
