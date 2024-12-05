package com.example.demo;

import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.level.*;
import com.example.demo.screen.GameOverScreen;
import com.example.demo.screen.StartScreen;
import com.example.demo.screen.WinScreen;
import com.example.demo.service.*;
import com.example.demo.ui.*;
import com.example.demo.util.Signal;

import java.util.ArrayList;
import java.util.List;

public class GameController {
	private final SceneService sceneService;
	private final Signal sceneReset;
    private final StartScreen startScreen;
	private final PlayerPlane player;
	private final UserInterface userInterface;
	private final List<Level> levelsOrdered;

    private int nextLevelIndex;

	public Signal getSceneResetSignal() {
		return sceneReset;
	}

	public PlayerPlane getPlayer() {
		return player;
	}

    public GameController() {
		this.sceneService = ServiceLocator.getSceneService();
		this.sceneReset = new Signal();
        this.startScreen = new StartScreen();
		this.player = new PlayerPlane(this);
		this.userInterface = new UserInterface(this);
		this.levelsOrdered = new ArrayList<>();

		this.nextLevelIndex = 0;

		initialize();
	}

	private void initialize() {
		initializeLevels();
		connectSignals();
	}

	private void initializeLevels() {
		levelsOrdered.add(new LevelOne(this));
		levelsOrdered.add(new LevelTwo(this));
	}

	private void connectSignals() {
		startScreen.getContinuedSignal().connect(this::startLevels);

		for (Level level : levelsOrdered) {
			level.getLevelWon().connect(this::onLevelWon);
			level.getLevelLost().connect(this::loseGame);
		}
	}

	private void startLevels() {
		sceneService.clearMiddleLayer();

		player.addToScene();

		sceneService.addNodeToTopLayer(userInterface);

		nextLevelIndex = 0;

		goToNextLevel();
	}

	private void goToNextLevel() {
        Level currentLevel = levelsOrdered.get(nextLevelIndex++);

		currentLevel.startLevel();
	}

	private void onLevelWon() {
		boolean isLastLevelWon = nextLevelIndex >= levelsOrdered.size();

		if (isLastLevelWon)
			winGame();
		else {
			sceneReset.emit();
			goToNextLevel();
		}
	}

	private void winGame() {
		WinScreen winScreen = new WinScreen();
		winScreen.getContinuedSignal().connect(this::startLevels);

		sceneService.clearMiddleLayer();
		sceneService.addNodeToMiddleLayer(winScreen);
		winScreen.start();
		stopGame();
	}

	private void loseGame() {
		GameOverScreen gameOverScreen = new GameOverScreen();
		gameOverScreen.getContinuedSignal().connect(this::startLevels);

		sceneService.clearMiddleLayer();
		sceneService.addNodeToMiddleLayer(gameOverScreen);
		gameOverScreen.start();
		stopGame();
	}

	private void stopGame() {
		sceneReset.emit();
		player.removeFromScene();
		sceneService.clearTopLayer();
	}

	public void launchGame() {
		sceneService.addNodeToMiddleLayer(startScreen);
		startScreen.start();
	}
}
