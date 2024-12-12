package com.example.demo.service;

import com.example.demo.util.AudioUtils;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Service responsible for managing audio playback.
 */
public class AudioService {
    private static final String BACKGROUND_MUSIC_NAME = "background_music.mp3";
    private static final String PLANE_DESTROYED_SOUND_NAME = "plane_destroyed.wav";
    private static final String BULLET_FIRED_SOUND_NAME = "bullet_fired.wav";
    private static final String GAME_WON_SOUND_NAME = "game_won.wav";
    private static final String COLLECTIBLE_COLLECTED_SOUND_NAME = "collectible_collected.wav";

    /**
     * Enum representing different sound effects that can be played.
     */
    public enum Sound {
        PLANE_DESTROYED,
        BULLET_FIRED,
        GAME_WON,
        COLLECTIBLE_COLLECTED,
    }

    private final MediaPlayer backgroundMusic;

    /**
     * Constructs a new {@link AudioService} instance.
     * Initializes the background music.
     */
    public AudioService() {
        this.backgroundMusic = AudioUtils.getSoundFromName(BACKGROUND_MUSIC_NAME);
        backgroundMusic.setOnEndOfMedia(() -> {
            backgroundMusic.seek(Duration.ZERO);
            backgroundMusic.play();
        });
    }

    /**
     * Begins playback of the background music track.
     */
    public void startBackgroundMusic() {
        backgroundMusic.play();
    }

    /**
     * Stops the background music.
     */
    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    /**
     * Plays a specified sound effect.
     *
     * @param sound The {@link Sound} to play.
     */
    public void playSound(Sound sound) {
        String soundName = "";

        switch (sound) {
            case PLANE_DESTROYED -> soundName = PLANE_DESTROYED_SOUND_NAME;
            case BULLET_FIRED -> soundName = BULLET_FIRED_SOUND_NAME;
            case GAME_WON -> soundName = GAME_WON_SOUND_NAME;
            case COLLECTIBLE_COLLECTED -> soundName = COLLECTIBLE_COLLECTED_SOUND_NAME;
        }

        if (soundName.isEmpty())
            return;

        AudioUtils.getSoundFromName(soundName).play();
    }
}
