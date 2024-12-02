package com.example.demo.level;

import com.example.demo.controller.GameController;
import com.example.demo.entity.boss.BossPlane;

public class LevelTwo extends LevelParent {

	private final BossPlane boss;

    public LevelTwo(GameController gameController) {
		super(gameController);

		this.boss = new BossPlane(gameController);

		initialize();
	}

	private void initialize() {
		connectSignals();
	}

	private void connectSignals() {
		boss.getPlaneDestroyedSignal().connect(this::onBossPlaneDestroyed);
	}

	private void onBossPlaneDestroyed() {
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
