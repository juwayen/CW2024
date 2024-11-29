package com.example.demo.level;

import com.example.demo.controller.GameController;
import com.example.demo.entity.boss.BossPlane;

public class LevelTwo extends LevelParent {
	private static final String BACKGROUND_IMAGE_NAME = "background_2.png";

	private final BossPlane boss;

    public LevelTwo(GameController gameController) {
		super(gameController, BACKGROUND_IMAGE_NAME);

		this.boss = new BossPlane(gameController);

		initialize();
	}

	private void initialize() {
		connectSignals();
	}

	private void connectSignals() {
		boss.getPlaneDestroyedSignal().connect(this, "onBossPlaneDestroyed");
	}

	public void onBossPlaneDestroyed() {
		winLevel();
	}

	@Override
	protected void spawnEnemyUnits() {
		int enemyCount = getEnemyCount();

		if (enemyCount == 0) {
			boss.addToScene();
			setEnemyCount(enemyCount + 1);
		}
	}
}
