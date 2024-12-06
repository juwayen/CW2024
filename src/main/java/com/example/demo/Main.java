package com.example.demo;

import com.example.demo.service.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static final int WINDOW_WIDTH = 1040;
	public static final int WINDOW_HEIGHT = 1024;
	public static final int GAME_WIDTH = 1024;
	public static final int GAME_HEIGHT = 985;

	private static final String TITLE = "Warbirds";

    @Override
	public void start(Stage stage) {
		initializeStage(stage);
		initializeServiceLocator(stage);

		GameController gameController = new GameController();
		gameController.launchGame();
	}

	private void initializeStage(Stage stage) {
		Group root = new Group();

		Scene scene = new Scene(root);

		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setWidth(WINDOW_WIDTH);
		stage.setHeight(WINDOW_HEIGHT);
		stage.setScene(scene);
		stage.show();
	}

	private void initializeServiceLocator(Stage stage) {
		ServiceLocator.setGameLoopService(new GameLoopService());
		ServiceLocator.setSceneService(new SceneService(stage.getScene()));
		ServiceLocator.setInputService(new InputService());
		ServiceLocator.setCollisionService(new CollisionService());
	}

	public static void main(String[] args) {
		launch();
	}
}