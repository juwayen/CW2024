package com.example.demo.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class AudioUtils {
    private static final String SOUND_PATH = "/com/example/demo/sounds/";
    private static final double VOLUME = 0.05;

    public static MediaPlayer getSoundFromName(String soundName) {
        String soundUrl = Objects.requireNonNull(AudioUtils.class.getResource(SOUND_PATH + soundName)).toExternalForm();

        MediaPlayer mediaPlayer = new MediaPlayer(new Media(soundUrl));
        mediaPlayer.setVolume(VOLUME);
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);

        return mediaPlayer;
    }
}
