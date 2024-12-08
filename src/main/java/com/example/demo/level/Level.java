package com.example.demo.level;

import com.example.demo.screen.GameScreen;
import com.example.demo.service.GameLoopService;
import com.example.demo.service.ServiceLocator;
import com.example.demo.service.Updatable;
import com.example.demo.Controller;
import com.example.demo.util.Signal;
import com.example.demo.entity.plane.PlayerPlane;

public abstract class Level implements Updatable {
	private final GameScreen levelEndScreen;
	private final Signal levelWon;
	private final Signal levelLost;
	private final PlayerPlane player;
	private final GameLoopService gameLoopService;

	private boolean isStopped;

	public GameScreen getLevelEndScreen() {
		return levelEndScreen;
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

	public Level(Controller controller, GameScreen levelEndScreen) {
		this.levelEndScreen = levelEndScreen;
		this.levelWon = new Signal();
		this.levelLost = new Signal();
		this.player = controller.getPlayer();
		this.gameLoopService = ServiceLocator.getGameLoopService();

		this.isStopped = true;
	}

	@Override
	public void update() {
		spawnEnemyUnits();
	}

	protected abstract void spawnEnemyUnits();

	public void startLevel() {
		isStopped = false;

		gameLoopService.addToLoop(this);
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

		gameLoopService.removeFromLoop(this);
	}
}
