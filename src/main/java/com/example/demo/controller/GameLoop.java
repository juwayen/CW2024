package com.example.demo.controller;

import com.example.demo.entity.Updatable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameLoop {
    public static final double MILLISECOND_DELAY = (double) 1000 / 60;

    private static final List<Updatable> updatables = new ArrayList<>();
    private static final List<Updatable> updatablesToAdd = new ArrayList<>();
    private static final List<Updatable> updatablesToRemove = new ArrayList<>();

    static {
        initialize();
    }

    private static void initialize() {
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> update());

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(gameLoop);
        timeline.play();
    }

    private static void update() {
        handleUpdates();
        processQueue();
    }

    private static void handleUpdates() {
        for (Updatable updatable : updatables) {
            if (updatablesToRemove.contains(updatable))
                continue;

            updatable.update();
        }
    }

    private static void processQueue() {
        updatables.addAll(updatablesToAdd);
        updatablesToAdd.clear();

        updatables.removeAll(updatablesToRemove);
        updatablesToRemove.clear();
    }

    public static void addToLoop(Updatable updatable) {
        updatablesToAdd.add(updatable);
    }

    public static void removeFromLoop(Updatable updatable) {
        updatablesToRemove.add(updatable);
    }
}
