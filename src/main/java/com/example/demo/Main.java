package com.example.demo;

import com.example.demo.controller.GameController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static final String IMAGE_PATH = "/com/example/demo/images/";
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;

	private static final String TITLE = "Warbirds";

    @Override
	public void start(Stage stage) {
		Group root = new Group();
		initializeStage(stage, root);
		GameController gameController = new GameController(stage, root);
		gameController.launchGame();
	}

	private void initializeStage(Stage stage, Group root) {
		Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setWidth(SCREEN_WIDTH);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}