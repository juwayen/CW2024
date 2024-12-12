package com.example.demo.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

/**
 * Utility class for audio-related operations.
 */
public class AudioUtils {
    private static final String SOUND_PATH = "/com/example/demo/sounds/";
    private static final double VOLUME = 0.05;

    /**
     * Creates and returns a {@link MediaPlayer} for a given sound name.
     * The {@link MediaPlayer#dispose()} method is invoked after the sound is finished playing.
     *
     * @param soundName The name of the sound file to be loaded.
     * @return A new {@link MediaPlayer} instance configured to play the specified sound.
     * @throws NullPointerException If the sound file cannot be found at the specified path.
     */
    public static MediaPlayer getSoundFromName(String soundName) {
        String soundUrl = Objects.requireNonNull(AudioUtils.class.getResource(SOUND_PATH + soundName)).toExternalForm();

        MediaPlayer mediaPlayer = new MediaPlayer(new Media(soundUrl));
        mediaPlayer.setVolume(VOLUME);
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);

        return mediaPlayer;
    }
}
