package com.example.demo;

import com.example.demo.controller.GameController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 1024;

	private static final String TITLE = "Warbirds";

    @Override
	public void start(Stage stage) {
		Group root = new Group();
		initializeStage(stage, root);
		GameController gameController = new GameController(stage, root);
		gameController.launchGame();
	}

	private void initializeStage(Stage stage, Group root) {
		double outputScaleX = Screen.getPrimary().getOutputScaleX();
		double outputScaleY = Screen.getPrimary().getOutputScaleY();

		double screenWidthAdjusted = SCREEN_WIDTH / outputScaleX;
		double screenHeightAdjusted = SCREEN_HEIGHT / outputScaleY;

		Scene scene = new Scene(root);

		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setWidth(screenWidthAdjusted);
		stage.setHeight(screenHeightAdjusted);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}