package com.example.demo.service;

import com.example.demo.util.AudioUtils;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioService {
    public enum Sound {
        PLANE_DESTROYED,
        BULLET_FIRED,
        GAME_WON,
        COLLECTIBLE_COLLECTED,
    }

    private final MediaPlayer backgroundMusic;

    public AudioService() {
        this.backgroundMusic = AudioUtils.getSoundFromName("background_music.mp3");
        backgroundMusic.setOnEndOfMedia(() -> {
            backgroundMusic.seek(Duration.ZERO);
            backgroundMusic.play();
        });
    }

    public void startBackgroundMusic() {
        backgroundMusic.play();
    }

    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    public void playSound(Sound sound) {
        String soundName = "";

        switch (sound) {
            case PLANE_DESTROYED -> soundName = "plane_destroyed.wav";
            case BULLET_FIRED -> soundName = "bullet_fired.wav";
            case GAME_WON -> soundName = "game_won.wav";
            case COLLECTIBLE_COLLECTED -> soundName = "collectible_collected.wav";
        }

        if (soundName.isEmpty())
            return;

        AudioUtils.getSoundFromName(soundName).play();
    }
}
