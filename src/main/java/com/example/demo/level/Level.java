package com.example.demo.level;

import com.example.demo.entity.collectible.Collectible;
import com.example.demo.entity.collectible.HealthCollectible;
import com.example.demo.entity.collectible.PowerupCollectible;
import com.example.demo.entity.plane.EnemyPlane;
import com.example.demo.factory.FormationFactory;
import com.example.demo.screen.GameScreen;
import com.example.demo.service.GameLoopService;
import com.example.demo.service.ServiceLocator;
import com.example.demo.service.Updatable;
import com.example.demo.Controller;
import com.example.demo.util.Signal;
import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.service.GameLoopService.MILLISECOND_DELAY;

public abstract class Level implements Updatable {
	private static final int MILLISECONDS_PER_COLLECTIBLE_CHANCE = 5000;
	private static final double COLLECTIBLE_CHANCE = 0.75;
	private static final Vector COLLECTIBLE_MIN_POSITION = new Vector(0.0, 492.5);
	private static final Vector COLLECTIBLE_MAX_POSITION = new Vector(960.0, 921.0);

	private final GameScreen levelEndScreen;
	private final int killsToWin;
	private final Signal levelWon;
	private final Signal levelLost;
	private final PlayerPlane player;
	private final GameLoopService gameLoopService;
	private final List<Collectible> collectiblePool;

	private boolean isStopped;
	private int enemiesKilled;
	private double millisecondsSinceLastCollectibleChance;

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

	public Level(Controller controller, GameScreen levelEndScreen, int killsToWin) {
		this.levelEndScreen = levelEndScreen;
		this.killsToWin = killsToWin;
		this.levelWon = new Signal();
		this.levelLost = new Signal();
		this.player = controller.getPlayer();
		this.gameLoopService = ServiceLocator.getGameLoopService();
		this.collectiblePool = new ArrayList<>();
		this.collectiblePool.add(new HealthCollectible(controller));
		this.collectiblePool.add(new PowerupCollectible(controller));

		this.isStopped = true;
		this.enemiesKilled = 0;
		this.millisecondsSinceLastCollectibleChance = 0;

		initializeFactories();
	}

	protected abstract void initializeFactories();

	@Override
	public void update() {
		spawnEnemyUnits();
		spawnCollectibles();
	}

	protected abstract void spawnEnemyUnits();

	protected void spawnEnemyFromFactory(FormationFactory factory, int enemiesAtOnce, int maxEnemies) {
		if (factory.getTotalEnemyCount() >= maxEnemies)
			return;

		if (factory.getCurrentEnemyCount() >= enemiesAtOnce)
			return;

		EnemyPlane enemyPlane = factory.create();

		enemyPlane.getDestroyedSignal().connect(this::onEnemyPlaneDestroyed);
	}

	protected void onEnemyPlaneDestroyed() {
		enemiesKilled++;

		if (enemiesKilled >= killsToWin)
			winLevel();
	}

	private void spawnCollectibles() {
		if (millisecondsSinceLastCollectibleChance < MILLISECONDS_PER_COLLECTIBLE_CHANCE) {
			millisecondsSinceLastCollectibleChance += MILLISECOND_DELAY;
			return;
		}

		boolean shouldCollectibleSpawn = Math.random() < COLLECTIBLE_CHANCE;

		if (!shouldCollectibleSpawn)
			return;

		Collectible collectible = collectiblePool.get((int) (Math.random() * collectiblePool.size()));
		collectible.spawnAt(Vector.random(COLLECTIBLE_MIN_POSITION, COLLECTIBLE_MAX_POSITION));

		millisecondsSinceLastCollectibleChance = 0;
	}

	public void startLevel() {
		isStopped = false;
		enemiesKilled = 0;
		millisecondsSinceLastCollectibleChance = 0;

		initializeFactories();

		gameLoopService.addToLoop(this);

		getPlayer().getDestroyedSignal().connect(this::loseLevel);
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
