package com.example.demo;

import com.example.demo.controller.GameController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	public static final double OUTPUT_SCALE = Screen.getPrimary().getOutputScaleX();
	public static final int LEFT_PADDING = 119;
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 1024;
	public static final int GAME_WIDTH = 1024;
	public static final int GAME_HEIGHT = 1024;

	private static final String TITLE = "Warbirds";

    @Override
	public void start(Stage stage) {
		initializeStage(stage);

		GameController gameController = new GameController(stage);
		gameController.launchGame();
	}

	private void initializeStage(Stage stage) {
		Group root = new Group();
		root.setLayoutX(LEFT_PADDING / OUTPUT_SCALE);

		Scene scene = new Scene(root, Color.BLACK);

		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setWidth(SCREEN_WIDTH / OUTPUT_SCALE);
		stage.setHeight(SCREEN_HEIGHT / OUTPUT_SCALE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}