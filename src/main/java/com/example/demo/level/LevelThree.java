package com.example.demo.level;

import com.example.demo.Controller;
import com.example.demo.entity.plane.EnemyPlane;
import com.example.demo.factory.BasicEnemyFactory;
import com.example.demo.factory.FormFactory;
import com.example.demo.factory.WallFormFormFactory;
import com.example.demo.screen.LevelThreeEndScreen;

public class LevelThree extends Level {
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 25;

	private final FormFactory formFactory;

	private int enemyCount;
	private int enemyPlanesDestroyed;
	private int totalEnemiesSpawned;

	public LevelThree(Controller controller) {
		super(controller, new LevelThreeEndScreen());

		this.formFactory = new WallFormFormFactory(new BasicEnemyFactory(controller));

		this.enemyCount = 0;
		this.enemyPlanesDestroyed = 0;
		this.totalEnemiesSpawned = 0;
	}

	@Override
	public void startLevel() {
		super.startLevel();

		enemyCount = 0;
		enemyPlanesDestroyed = 0;
		totalEnemiesSpawned = 0;

		getPlayer().getDestroyedSignal().connect(this::loseLevel);
	}

	@Override
	protected void spawnEnemyUnits() {
		if (totalEnemiesSpawned >= KILLS_TO_ADVANCE)
			return;

		for (int i = 0; i < TOTAL_ENEMIES - enemyCount; i++) {
			EnemyPlane enemyPlane = formFactory.create();

			enemyCount++;
			totalEnemiesSpawned++;

			enemyPlane.getRemovedSignal().connect(this::decrementEnemyCount);
			enemyPlane.getDestroyedSignal().connect(this::onEnemyPlaneDestroyed);
		}
	}

	private void decrementEnemyCount() {
		enemyCount--;
	}

	private void onEnemyPlaneDestroyed() {
		enemyPlanesDestroyed++;

		if (enemyPlanesDestroyed >= KILLS_TO_ADVANCE)
			winLevel();
	}
}
