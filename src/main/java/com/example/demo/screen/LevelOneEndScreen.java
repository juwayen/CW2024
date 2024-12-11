package com.example.demo.screen;

import com.example.demo.service.AudioService;
import com.example.demo.service.ServiceLocator;

public class LevelOneEndScreen extends GameScreen {
    private static final String LABEL_TEXT = "Mission One Complete\nPress Any Key To Continue";

    public LevelOneEndScreen() {
        super(LABEL_TEXT);
    }
}