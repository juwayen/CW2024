package com.example.demo.controller;

import com.example.demo.entity.player.PlayerPlane;
import com.example.demo.level.*;
import com.example.demo.screen.Background;
import com.example.demo.screen.GameOverScreen;
import com.example.demo.screen.StartScreen;
import com.example.demo.screen.WinScreen;
import com.example.demo.signal.Signal;
import com.example.demo.ui.*;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameController {
	private final Signal sceneReset;
	private final Stage stage;
	private final Group root;
	private final Group backgroundLayer;
	private final Group middlegroundLayer;
	private final Group foregroundLayer;
	private final StartScreen startScreen;
	private final PlayerPlane player;
	private final UserInterface userInterface;
	private final List<LevelParent> levelsOrdered;

    private int nextLevelIndex = 0;

	public Scene getScene() {
		return stage.getScene();
	}

	public PlayerPlane getPlayer() {
		return player;
	}

	public Signal getSceneResetSignal() {
		return sceneReset;
	}

    public GameController(Stage stage) {
		this.sceneReset = new Signal();
		this.stage = stage;
		this.root = (Group) stage.getScene().getRoot();
		this.backgroundLayer = new Group();
		this.middlegroundLayer = new Group();
		this.foregroundLayer = new Group();
		this.startScreen = new StartScreen();
		this.player = new PlayerPlane(this);
		this.userInterface = new UserInterface(this);
		this.levelsOrdered = new ArrayList<>();

		initialize();
	}

	private void initialize() {
		Input.initialize(getScene());
		initializeRoot();
		initializeLevels();
		connectSignals();
	}

	private void initializeRoot() {
		root.getChildren().add(backgroundLayer);
		root.getChildren().add(middlegroundLayer);
		root.getChildren().add(foregroundLayer);

		backgroundLayer.getChildren().add(new Background());
	}

	private void initializeLevels() {
		levelsOrdered.add(new LevelOne(this));
		levelsOrdered.add(new LevelTwo(this));

		connectLevelsSignals();
	}

	private void connectLevelsSignals() {
		for (LevelParent level : levelsOrdered) {
			level.getLevelWon().connect(this::onLevelWon);
			level.getLevelLost().connect(this::loseGame);
		}
	}

	private void connectSignals() {
		startScreen.getStartedSignal().connect(this::startLevels);
	}

	private void startLevels() {
		middlegroundLayer.getChildren().clear();
		player.addToScene();
		foregroundLayer.getChildren().add(userInterface);
		nextLevelIndex = 0;
		goToNextLevel();
	}

	public void launchGame() {
		middlegroundLayer.getChildren().add(startScreen);
	}

	private void goToNextLevel() {
        LevelParent currentLevel = levelsOrdered.get(nextLevelIndex++);

		currentLevel.startLevel();
	}

	private void onLevelWon() {
		if (nextLevelIndex < levelsOrdered.size()) {
			sceneReset.emit();
			goToNextLevel();
		}
		else
			winGame();
	}

	private void winGame() {
		WinScreen winScreen = new WinScreen();
		winScreen.getContinuedSignal().connect(this::startLevels);

		middlegroundLayer.getChildren().clear();
		middlegroundLayer.getChildren().add(winScreen);
		winScreen.activate();
		stopGame();
	}

	private void loseGame() {
		GameOverScreen gameOverScreen = new GameOverScreen();
		gameOverScreen.getContinuedSignal().connect(this::startLevels);

		middlegroundLayer.getChildren().clear();
		middlegroundLayer.getChildren().add(gameOverScreen);
		gameOverScreen.activate();
		stopGame();
	}

	private void stopGame() {
		sceneReset.emit();
		player.removeFromScene();
		foregroundLayer.getChildren().clear();
	}

	public void addNodeToRoot(Node node) {
		middlegroundLayer.getChildren().add(node);
	}

	public void removeNodeFromRoot(Node node) {
		middlegroundLayer.getChildren().remove(node);
	}
}
