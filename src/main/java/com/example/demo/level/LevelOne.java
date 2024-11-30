package com.example.demo.level;

import com.example.demo.controller.GameController;
import com.example.demo.entity.enemy.EnemyPlane;

import static com.example.demo.Main.SCREEN_WIDTH;

public class LevelOne extends LevelParent {
	private static final String BACKGROUND_IMAGE_NAME = "background_1.png";
	private static final int TOTAL_ENEMIES = 2;
	private static final int KILLS_TO_ADVANCE = 1;

	private final GameController gameController;

	public LevelOne(GameController gameController) {
		super(gameController, BACKGROUND_IMAGE_NAME);

		this.gameController = gameController;

		initialize();
	}

	private void initialize() {
		connectSignals();
	}

	private void connectSignals() {
		getPlayer().getEnemyPlaneDestroyedSignal().connect(this::onEnemyPlaneDestroyed);
	}

	private void onEnemyPlaneDestroyed() {
		if (isKillTargetReached())
			winLevel();
	}

	private boolean isKillTargetReached() {
		return getPlayer().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getEnemyCount();

		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			double newEnemyInitialYPosition = Math.random() * ENEMY_MAX_Y_POSITION;
			EnemyPlane newEnemy = new EnemyPlane(gameController, SCREEN_WIDTH, newEnemyInitialYPosition);
			newEnemy.addToScene();

			setEnemyCount(getEnemyCount() + 1);

			newEnemy.getPlaneDestroyedSignal().connect(this::decrementEnemyCount);
			newEnemy.getDefensesBreachedSignal().connect(this::onDefensesBreached);
		}
	}

	private void decrementEnemyCount() {
		setEnemyCount(getEnemyCount() - 1);
	}

	private void onDefensesBreached() {
		loseLevel();
	}
}
