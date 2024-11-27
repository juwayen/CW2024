package com.example.demo.manager;

import com.example.demo.level.LevelOne;
import com.example.demo.level.LevelTwo;
import com.example.demo.signal.Signal;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.level.LevelParent;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
	private final Stage stage;
	private final InputManager inputManager = InputManager.getInstance();
	private final List<LevelParent> levelsOrdered = new ArrayList<>();
	private int nextLevelIndex = 0;

    public GameManager(Stage stage) {
		this.stage = stage;
		initializeLevels();
	}

	private void initializeLevels() {
		levelsOrdered.add(new LevelOne(stage.getWidth(), stage.getHeight()));
		levelsOrdered.add(new LevelTwo(stage.getWidth(), stage.getHeight()));
	}

	public void launchGame() {
		stage.show();
		goToNextLevel();
	}

	private void goToNextLevel() {
		LevelParent nextLevel = levelsOrdered.get(nextLevelIndex);

		Signal levelWinSignal = nextLevel.getLevelWinSignal();
		levelWinSignal.connect(this, "onLevelWin");

		Scene scene = nextLevel.initializeScene();
		inputManager.setScene(scene);
		stage.setScene(scene);

		nextLevel.startGame();

		nextLevelIndex++;
	}

	public void onLevelWin() {
		goToNextLevel();
	}
}
