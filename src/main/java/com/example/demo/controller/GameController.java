package com.example.demo.controller;

import com.example.demo.entity.player.PlayerPlane;
import com.example.demo.level.LevelOne;
import com.example.demo.level.LevelTwo;
import com.example.demo.signal.Signal;
import com.example.demo.ui.GameOverImage;
import com.example.demo.ui.ImageParent;
import com.example.demo.ui.UserInterface;
import com.example.demo.ui.WinImage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.example.demo.level.LevelParent;

import java.util.ArrayList;
import java.util.List;

public class GameController {
	private final Signal sceneReset;
	private final Stage stage;
	private final Group root;
	private final ImageView background;
	private final PlayerPlane player;
	private final UserInterface userInterface;
    private final List<LevelParent> levelsOrdered;

	private int nextLevelIndex = 0;

    public GameController(Stage stage, Group root) {
		this.sceneReset = new Signal();
		this.stage = stage;
		this.root = root;
		this.background = new ImageView();
		this.player = new PlayerPlane(this);
		this.userInterface = new UserInterface(this);
		this.levelsOrdered = new ArrayList<>();

		initialize();
	}

	private void initialize() {
		Input.initialize(getScene());
		initializeBackground();
		initializeLevels();
		connectSignals();

		player.addToScene();
	}

	private void initializeBackground() {
		background.setViewOrder(1);
		background.setFitWidth(stage.getWidth());
		background.setFitHeight(stage.getHeight());
		addNodeToRoot(background);
	}

	private void initializeLevels() {
		levelsOrdered.add(new LevelOne(this));
		levelsOrdered.add(new LevelTwo(this));
	}

	private void connectSignals() {
		player.getPlaneDestroyedSignal().connect(this, "loseGame");
	}

	public Scene getScene() {
		return stage.getScene();
	}

	public PlayerPlane getPlayer() {
		return player;
	}

	public Signal getSceneResetSignal() {
		return sceneReset;
	}

	public void launchGame() {
		goToNextLevel();
	}

	private void goToNextLevel() {
		LevelParent nextLevel = levelsOrdered.get(nextLevelIndex);

		connectLevelSignals(nextLevel);

		nextLevel.startGame();

		nextLevelIndex++;
	}

	private void connectLevelSignals(LevelParent level) {
		level.getLevelWon().connect(this, "onLevelWon");
		level.getLevelLost().connect(this, "loseGame");
	}

	public void onLevelWon() {
		if (nextLevelIndex < levelsOrdered.size()) {
			sceneReset.emit();
			goToNextLevel();
		}
		else
			winGame();
	}

	private void winGame() {
		displayImage(new WinImage(0, 0));
		stopGame();
	}

	public void loseGame() {
		displayImage(new GameOverImage(0, 0));
		stopGame();
	}

	private void stopGame() {
		sceneReset.emit();
		player.removeFromScene();
		removeNodeFromRoot(userInterface);
	}

	private void displayImage(ImageParent image) {
		image.moveToCenter();
		addNodeToRoot(image);
	}

	public void addNodeToRoot(Node node) {
		root.getChildren().add(node);
	}

	public void removeNodeFromRoot(Node node) {
		root.getChildren().remove(node);
	}

	public void setBackgroundImage(Image backgroundImage) {
		background.setImage(backgroundImage);
	}
}
