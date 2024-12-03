package com.example.demo.level;

import com.example.demo.entity.Updatable;
import com.example.demo.controller.GameController;
import com.example.demo.signal.Signal;
import com.example.demo.entity.player.PlayerPlane;

public abstract class LevelParent implements Updatable {
	private final Signal levelWon;
	private final Signal levelLost;
	private final PlayerPlane player;

	private boolean isStopped = true;

	public LevelParent(GameController gameController) {
		this.levelWon = new Signal();
		this.levelLost = new Signal();
		this.player = gameController.getPlayer();
	}

	public Signal getLevelWon() {
		return levelWon;
	}

	public Signal getLevelLost() {
		return levelLost;
	}

	protected PlayerPlane getPlayer() {
		return player;
	}

	@Override
	public void update() {
		spawnEnemyUnits();
	}

	protected abstract void spawnEnemyUnits();

	public void startLevel() {
		isStopped = false;

		addToGameLoop();
	}

	protected void winLevel() {
		if (isStopped)
			return;

		levelWon.emit();
		stopLevel();
	}

	protected void loseLevel() {
		if (isStopped)
			return;

		levelLost.emit();
		stopLevel();
	}

	protected void stopLevel() {
		isStopped = true;

		removeFromGameLoop();
	}
}
