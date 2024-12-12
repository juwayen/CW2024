package com.example.demo.service;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for updates.
 */
public class UpdateService {
    public static final double MILLISECOND_DELAY = (double) 1000 / 60;

    private final List<Updatable> updatables;
    private final List<Updatable> updatablesToAdd;
    private final List<Updatable> updatablesToRemove;

    /**
     * Constructs an instance of the {@link UpdateService} and starts the update loop.
     */
    public UpdateService() {
        this.updatables = new ArrayList<>();
        this.updatablesToAdd = new ArrayList<>();
        this.updatablesToRemove = new ArrayList<>();

        initializeLoop();
    }

    /**
     * Initializes the update loop {@link Timeline} for the service.
     */
    private void initializeLoop() {
        KeyFrame updateLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), event -> update());

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(updateLoop);
        timeline.play();
    }

    /**
     * Update method that invokes {@link #handleUpdates()} and {@link #processQueue()}.
     */
    private void update() {
        handleUpdates();
        processQueue();
    }

    /**
     * Invokes {@link Updatable#update()} on all registered {@link Updatable}s.
     * Skips any that are marked for removal.
     */
    private void handleUpdates() {
        for (Updatable updatable : updatables) {
            if (updatablesToRemove.contains(updatable))
                continue;

            updatable.update();
        }
    }

    /**
     * Processes the queues of {@link Updatable}s to add and remove from the loop.
     */
    private void processQueue() {
        updatables.addAll(updatablesToAdd);
        updatablesToAdd.clear();

        updatables.removeAll(updatablesToRemove);
        updatablesToRemove.clear();
    }

    /**
     * Registers the specified {@link Updatable} for updates.
     *
     * @param updatable The {@link Updatable} to enable updates for.
     */
    public void addToLoop(Updatable updatable) {
        updatablesToAdd.add(updatable);
    }

    /**
     * Unregisters the specified {@link Updatable} for updates.
     *
     * @param updatable The {@link Updatable} to disable updates for.
     */
    public void removeFromLoop(Updatable updatable) {
        updatablesToRemove.add(updatable);
    }
}
