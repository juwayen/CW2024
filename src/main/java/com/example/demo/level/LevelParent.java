package com.example.demo.level;

import java.util.*;

import com.example.demo.entity.Updatable;
import com.example.demo.controller.GameLoop;
import com.example.demo.controller.GameController;
import com.example.demo.signal.Signal;
import com.example.demo.entity.player.PlayerPlane;
import javafx.scene.image.*;

import static com.example.demo.Main.IMAGE_PATH;
import static com.example.demo.Main.SCREEN_HEIGHT;

public abstract class LevelParent implements Updatable {
	protected static final double ENEMY_MAX_Y_POSITION = SCREEN_HEIGHT - 150;

	private final Signal levelWon;
	private final Signal levelLost;
	private final GameController gameController;
	private final PlayerPlane player;
	private final Image backgroundImage;

	private int enemyCount;

	public LevelParent(GameController gameController, String backgroundImageName) {
		this.levelWon = new Signal();
		this.levelLost = new Signal();
		this.gameController = gameController;
		this.player = gameController.getPlayer();
		this.backgroundImage = new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + backgroundImageName)).toExternalForm());
	}

	protected PlayerPlane getPlayer() {
		return player;
	}

	public Signal getLevelWon() {
		return levelWon;
	}

	public Signal getLevelLost() {
		return levelLost;
	}

	protected int getEnemyCount() {
		return enemyCount;
	}

	protected void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}

	@Override
	public void update() {
		spawnEnemyUnits();
	}

	protected abstract void spawnEnemyUnits();

	public void startGame() {
		gameController.setBackgroundImage(backgroundImage);
		GameLoop.addToLoop(this);
	}

	protected void winLevel() {
		stopLevel();
		levelWon.emit();
	}

	protected void loseLevel() {
		stopLevel();
		levelLost.emit();
	}

	protected void stopLevel() {
		GameLoop.removeFromLoop(this);
	}
}
