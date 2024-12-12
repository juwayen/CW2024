package com.example.demo.level;

import com.example.demo.entity.collectible.Collectible;
import com.example.demo.entity.collectible.HealthCollectible;
import com.example.demo.entity.collectible.PowerupCollectible;
import com.example.demo.entity.plane.EnemyPlane;
import com.example.demo.factory.FormationFactory;
import com.example.demo.screen.GameScreen;
import com.example.demo.service.UpdateService;
import com.example.demo.service.ServiceLocator;
import com.example.demo.service.Updatable;
import com.example.demo.util.Signal;
import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.service.UpdateService.MILLISECOND_DELAY;

/**
 * Abstract base class for creating levels in the game.
 */
public abstract class Level implements Updatable {
	private static final int MILLISECONDS_PER_COLLECTIBLE_CHANCE = 5000;
	private static final double COLLECTIBLE_CHANCE = 0.75;
	private static final Vector COLLECTIBLE_MIN_POSITION = new Vector(0.0, 492.5);
	private static final Vector COLLECTIBLE_MAX_POSITION = new Vector(960.0, 921.0);

	private final UpdateService updateService;
	private final Signal levelWon;
	private final Signal levelLost;
	private final GameScreen levelEndScreen;
	private final int killsToWin;
	private final PlayerPlane player;
	private final List<Collectible> collectiblePool;

	private boolean isStopped;
	private int enemiesKilled;
	private double millisecondsSinceLastCollectibleChance;

	/**
	 * Getter method for the level won {@link Signal} associated with the event of winning the level.
	 *
	 * @return the level won {@link Signal} that is emitted when the level is won.
	 */
	public Signal getLevelWon() {
		return levelWon;
	}

	/**
	 * Getter method for the level lost {@link Signal} associated with the event of losing the level.
	 *
	 * @return the level lost {@link Signal} that is emitted when the level is lost.
	 */
	public Signal getLevelLost() {
		return levelLost;
	}

	/**
	 * Getter method for the screen that is displayed at the end of the level.
	 *
	 * @return The {@link GameScreen} instance representing the level end screen.
	 */
	public GameScreen getLevelEndScreen() {
		return levelEndScreen;
	}

	/**
	 * Getter method for the {@link PlayerPlane} for the current level.
	 *
	 * @return the {@link PlayerPlane} instance representing the player.
	 */
	protected PlayerPlane getPlayer() {
		return player;
	}

	/**
	 * Constructs a new {@link Level} instance.
	 *
	 * @param levelEndScreen The {@link GameScreen} instance that represents the screen displayed at the end of the level.
	 * @param killsToWin The number of enemy kills required to win the level.
	 */
	public Level(GameScreen levelEndScreen, int killsToWin) {
		this.updateService = ServiceLocator.getUpdateService();
		this.levelWon = new Signal();
		this.levelLost = new Signal();
		this.levelEndScreen = levelEndScreen;
		this.killsToWin = killsToWin;
		this.player = ServiceLocator.getGameService().getPlayer();
		this.collectiblePool = new ArrayList<>();
		this.collectiblePool.add(new HealthCollectible());
		this.collectiblePool.add(new PowerupCollectible());

		this.isStopped = true;
		this.enemiesKilled = 0;
		this.millisecondsSinceLastCollectibleChance = 0;

		initializeFactories();
	}

	/**
	 * Initializes the necessary {@link FormationFactory}s for the specific {@link Level} implementation.
	 */
	protected abstract void initializeFactories();

	/**
	 * Update method that invokes {@link #spawnEnemyUnits()} and {@link #spawnCollectibles()}.
	 */
	@Override
	public void update() {
		spawnEnemyUnits();
		spawnCollectibles();
	}

	/**
	 * Triggers the spawning of enemy units.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Spawns a random {@link Collectible} item based on a predefined chance and time interval.
	 */
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

	/**
	 * Spawns {@link EnemyPlane}s from a specified {@link FormationFactory}.
	 * Connects the destroyed {@link Signal} of the created {@link EnemyPlane} to {@link #onEnemyPlaneDestroyed()};
	 *
	 * @param factory The {@link FormationFactory} instance used to create {@link EnemyPlane}s.
	 * @param enemiesAtOnce The maximum number of {@link EnemyPlane}s that can be in the scene at once.
	 * @param maxEnemies The maximum total number of {@link EnemyPlane}s allowed to exist at any point.
	 */
	protected void spawnEnemyFromFactory(FormationFactory factory, int enemiesAtOnce, int maxEnemies) {
		if (factory.getTotalEnemyCount() >= maxEnemies)
			return;

		if (factory.getCurrentEnemyCount() >= enemiesAtOnce)
			return;

		EnemyPlane enemyPlane = factory.create();

		enemyPlane.getDestroyedSignal().connect(this::onEnemyPlaneDestroyed);
	}

	/**
	 * Handles the event of an {@link EnemyPlane} being destroyed.
	 * If the kills to win threshold has been reached, it invokes {@link #winLevel()}.
	 */
	protected void onEnemyPlaneDestroyed() {
		enemiesKilled++;

		if (enemiesKilled >= killsToWin)
			winLevel();
	}

	/**
	 * Stops the level and performs necessary actions to handle the victory state.
	 * Emits the level won {@link Signal}.
	 */
	protected void winLevel() {
		if (isStopped)
			return;

		levelWon.emit();
		stopLevel();
	}

	/**
	 * Stops the level and performs necessary actions to handle the lost state.
	 * Emits the level lost {@link Signal}.
	 */
	protected void loseLevel() {
		if (isStopped)
			return;

		levelLost.emit();
		stopLevel();
	}

	/**
	 * Starts the level by performing the necessary initializations and setup.
	 * Connects the destroyed {@link Signal} of the {@link PlayerPlane} to {@link #loseLevel()}.
	 */
	public void startLevel() {
		isStopped = false;
		enemiesKilled = 0;
		millisecondsSinceLastCollectibleChance = 0;

		initializeFactories();

		updateService.addToLoop(this);

		getPlayer().getDestroyedSignal().connect(this::loseLevel);
	}

	/**
	 * Stops the current level by marking it as stopped and unregistering it from the {@link UpdateService}.
	 */
	protected void stopLevel() {
		isStopped = true;

		updateService.removeFromLoop(this);
	}
}
