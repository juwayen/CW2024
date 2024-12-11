package com.example.demo;

import com.example.demo.service.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static final int STAGE_WIDTH = 1040;
	public static final int STAGE_HEIGHT = 1024;
	public static final int GAME_WIDTH = 1024;
	public static final int GAME_HEIGHT = 985;

	private static final String TITLE = "Warbirds";

    @Override
	public void start(Stage stage) {
		initializeStage(stage);
		initializeServiceLocator(stage);

		ServiceLocator.getGameService().launchGame();
	}

	private void initializeStage(Stage stage) {
		Group root = new Group();

		Scene scene = new Scene(root);

		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setWidth(STAGE_WIDTH);
		stage.setHeight(STAGE_HEIGHT);
		stage.setScene(scene);
		stage.show();
	}

	private void initializeServiceLocator(Stage stage) {
		ServiceLocator.registerUpdateService(new UpdateService());
		ServiceLocator.registerSceneService(new SceneService(stage.getScene()));
		ServiceLocator.registerInputService(new InputService());
		ServiceLocator.registerCollisionService(new CollisionService());
		ServiceLocator.registerAudioService(new AudioService());
		ServiceLocator.registerGameService(new GameService());
	}

	public static void main(String[] args) {
		launch();
	}
}