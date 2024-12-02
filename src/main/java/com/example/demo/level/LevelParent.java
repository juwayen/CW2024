package com.example.demo.level;

import com.example.demo.entity.Updatable;
import com.example.demo.controller.GameLoop;
import com.example.demo.controller.GameController;
import com.example.demo.signal.Signal;
import com.example.demo.entity.player.PlayerPlane;

public abstract class LevelParent implements Updatable {
	private final Signal levelWon;
	private final Signal levelLost;
	private final PlayerPlane player;

	private int enemyCount;

	public LevelParent(GameController gameController) {
		this.levelWon = new Signal();
		this.levelLost = new Signal();
		this.player = gameController.getPlayer();
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
