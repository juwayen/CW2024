package com.example.demo;

import com.example.demo.controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;

	private static final String TITLE = "Warbirds";

    @Override
	public void start(Stage stage) {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setWidth(SCREEN_WIDTH);
		stage.setHeight(SCREEN_HEIGHT);
        GameController gameController = new GameController(stage);
		gameController.launchGame();
	}

	public static void main(String[] args) {
		launch();
	}
}