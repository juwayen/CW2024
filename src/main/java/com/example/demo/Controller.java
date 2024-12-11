package com.example.demo;

import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.level.*;
import com.example.demo.screen.GameOverScreen;
import com.example.demo.screen.GameScreen;
import com.example.demo.screen.StartScreen;
import com.example.demo.screen.WinScreen;
import com.example.demo.service.*;
import com.example.demo.ui.*;
import com.example.demo.util.AudioUtils;
import com.example.demo.util.Signal;

import java.util.ArrayList;
import java.util.List;

public class Controller {
	private final SceneService sceneService;
	private final AudioService audioService;
	private final Signal sceneReset;
    private final StartScreen startScreen;
	private final PlayerPlane player;
	private final UserInterface userInterface;
	private final List<Level> levelsOrdered;

	private Level currentLevel;
    private int nextLevelIndex;

	public Signal getSceneResetSignal() {
		return sceneReset;
	}

	public PlayerPlane getPlayer() {
		return player;
	}

    public Controller() {
		this.sceneService = ServiceLocator.getSceneService();
		this.audioService = ServiceLocator.getAudioService();
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
		levelsOrdered.add(new LevelThree(this));
		levelsOrdered.add(new LevelFour(this));
		levelsOrdered.add(new LevelFive(this));
	}

	private void connectSignals() {
		player.getEnteredLevelSignal().connect(this::onPlayerEnteredLevel);
		player.getExitedLevelSignal().connect(this::onPlayerExitedLevel);
		startScreen.getContinuedSignal().connect(this::startLevels);

		for (Level level : levelsOrdered) {
			level.getLevelWon().connect(this::onLevelWon);
			level.getLevelLost().connect(this::loseGame);
		}
	}

	private void onPlayerEnteredLevel() {
		currentLevel.startLevel();
	}

	private void startLevels() {
		sceneService.clearMiddleLayer();

		player.addToScene();

		sceneService.addNodeToTopLayer(userInterface);
		audioService.startBackgroundMusic();

		nextLevelIndex = 0;

		goToNextLevel();
	}

	private void goToNextLevel() {
		currentLevel = levelsOrdered.get(nextLevelIndex++);

		userInterface.setVisible(true);
		player.playEnterTransition();
	}

	private void onPlayerExitedLevel() {
		GameScreen levelEndScreen = currentLevel.getLevelEndScreen();
		sceneService.addNodeToMiddleLayer(levelEndScreen);
		levelEndScreen.getContinuedSignal().connect(this::startNextLevel);
		levelEndScreen.start();
	}

	private void startNextLevel() {
		GameScreen levelEndScreen = currentLevel.getLevelEndScreen();
		sceneService.removeNodeFromMiddleLayer(levelEndScreen);
		levelEndScreen.getContinuedSignal().clearConnections();

		boolean isFinalLevelWon = nextLevelIndex >= levelsOrdered.size();

		if (isFinalLevelWon)
			winGame();
		else {
			sceneReset.emit();
			goToNextLevel();
		}
	}

	private void onLevelWon() {
		userInterface.setVisible(false);
		player.playExitTransition();
	}

	private void winGame() {
		WinScreen winScreen = new WinScreen();
		winScreen.getContinuedSignal().connect(this::startLevels);

		sceneService.clearMiddleLayer();
		sceneService.addNodeToMiddleLayer(winScreen);
		audioService.stopBackgroundMusic();
		audioService.playSound(AudioService.Sound.GAME_WON);
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
		audioService.startBackgroundMusic();
	}
}
