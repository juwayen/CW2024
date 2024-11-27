package com.example.demo.manager;

import com.example.demo.level.LevelOne;
import com.example.demo.signal.Signal;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.level.LevelParent;

public class GameManager {
	private final Stage stage;
	private final LevelParent levelOne;
	private final InputManager inputManager = InputManager.getInstance();

    public GameManager(Stage stage) {
		this.stage = stage;
		levelOne = new LevelOne(stage.getWidth(), stage.getHeight());
	}

	public void launchGame() {
		stage.show();
		goToLevel(levelOne);
	}

	private void goToLevel(LevelParent nextLevel) {
		Signal levelWinSignal = nextLevel.getLevelWinSignal();
		levelWinSignal.connect(this, "onLevelWin", LevelParent.class);
		Scene scene = nextLevel.initializeScene();
		inputManager.setScene(scene);
		stage.setScene(scene);
		nextLevel.startGame();
	}

	public void onLevelWin(LevelParent nextLevel) {
		goToLevel(nextLevel);
	}
}
