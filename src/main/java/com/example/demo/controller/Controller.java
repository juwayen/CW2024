package com.example.demo.controller;

import com.example.demo.LevelOne;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

public class Controller {
	private final Stage stage;
	private final LevelParent levelOne;

    public Controller(Stage stage) {
		this.stage = stage;
		levelOne = new LevelOne(stage.getWidth(), stage.getHeight());
	}

	public void launchGame() {
		stage.show();
		goToLevel(levelOne);
	}

	private void goToLevel(LevelParent nextLevel) {
		nextLevel.levelWinSignal.connect(this, "onLevelWin", LevelParent.class);
		Scene scene = nextLevel.initializeScene();
		stage.setScene(scene);
		nextLevel.startGame();
	}

	public void onLevelWin(LevelParent nextLevel) {
		goToLevel(nextLevel);
	}
}
