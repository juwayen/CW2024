package com.example.demo.service;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class UpdateService {
    public static final double MILLISECOND_DELAY = (double) 1000 / 60;

    private final List<Updatable> updatables;
    private final List<Updatable> updatablesToAdd;
    private final List<Updatable> updatablesToRemove;

    public UpdateService() {
        this.updatables = new ArrayList<>();
        this.updatablesToAdd = new ArrayList<>();
        this.updatablesToRemove = new ArrayList<>();

        initialize();
    }

    private void initialize() {
        KeyFrame updateLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), event -> update());

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(updateLoop);
        timeline.play();
    }

    private void update() {
        handleUpdates();
        processQueue();
    }

    private void handleUpdates() {
        for (Updatable updatable : updatables) {
            if (updatablesToRemove.contains(updatable))
                continue;

            updatable.update();
        }
    }

    private void processQueue() {
        updatables.addAll(updatablesToAdd);
        updatablesToAdd.clear();

        updatables.removeAll(updatablesToRemove);
        updatablesToRemove.clear();
    }

    public void addToLoop(Updatable updatable) {
        updatablesToAdd.add(updatable);
    }

    public void removeFromLoop(Updatable updatable) {
        updatablesToRemove.add(updatable);
    }
}
