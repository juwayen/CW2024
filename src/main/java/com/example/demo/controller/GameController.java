package com.example.demo.controller;

import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.level.*;
import com.example.demo.Background;
import com.example.demo.screen.GameOverScreen;
import com.example.demo.screen.StartScreen;
import com.example.demo.screen.WinScreen;
import com.example.demo.signal.Signal;
import com.example.demo.ui.*;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameController {
	private final Signal sceneReset;
	private final Stage stage;
	private final Group root;
	private final Group bottomLayer;
	private final Group middleLayer;
	private final Group topLayer;
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

    public GameController(Stage stage) {
		this.sceneReset = new Signal();
		this.stage = stage;
		this.root = (Group) stage.getScene().getRoot();
		this.bottomLayer = new Group();
		this.middleLayer = new Group();
		this.topLayer = new Group();
		this.startScreen = new StartScreen();
		this.player = new PlayerPlane(this);
		this.userInterface = new UserInterface(this);
		this.levelsOrdered = new ArrayList<>();

		this.nextLevelIndex = 0;

		initialize();
	}

	private void initialize() {
		Input.initialize(stage.getScene());
		initializeRoot();
		initializeBackground();
		initializeLevels();
		connectSignals();
	}

	private void initializeRoot() {
		root.getChildren().add(bottomLayer);
		root.getChildren().add(middleLayer);
		root.getChildren().add(topLayer);
	}

	private void initializeBackground() {
		bottomLayer.getChildren().add(new Background());
	}

	private void initializeLevels() {
		levelsOrdered.add(new LevelOne(this));
		levelsOrdered.add(new LevelTwo(this));

		for (Level level : levelsOrdered) {
			level.getLevelWon().connect(this::onLevelWon);
			level.getLevelLost().connect(this::loseGame);
		}
	}

	private void connectSignals() {
		startScreen.getContinuedSignal().connect(this::startLevels);
	}

	private void startLevels() {
		clearMiddleLayer();

		player.addToScene();

		addNodeToTopLayer(userInterface);

		nextLevelIndex = 0;

		goToNextLevel();
	}

	private void goToNextLevel() {
        Level currentLevel = levelsOrdered.get(nextLevelIndex++);

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

		clearMiddleLayer();
		addNodeToMiddleLayer(winScreen);
		winScreen.start();
		stopGame();
	}

	private void loseGame() {
		GameOverScreen gameOverScreen = new GameOverScreen();
		gameOverScreen.getContinuedSignal().connect(this::startLevels);

		clearMiddleLayer();
		addNodeToMiddleLayer(gameOverScreen);
		gameOverScreen.start();
		stopGame();
	}

	private void stopGame() {
		sceneReset.emit();
		player.removeFromScene();
		clearTopLayer();
	}

	public void launchGame() {
		addNodeToMiddleLayer(startScreen);
		startScreen.start();
	}

	private void addNodeToTopLayer(Node node) {
		topLayer.getChildren().add(node);
	}

	private void clearTopLayer() {
		topLayer.getChildren().clear();
	}

	public void addNodeToMiddleLayer(Node node) {
		middleLayer.getChildren().add(node);
	}

	public void removeNodeFromMiddleLayer(Node node) {
		middleLayer.getChildren().remove(node);
	}

	private void clearMiddleLayer() {
		middleLayer.getChildren().clear();
	}
}
