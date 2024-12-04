package com.example.demo.level;

import com.example.demo.controller.GameController;
import com.example.demo.entity.plane.BossPlane;

public class LevelTwo extends Level {
	private final GameController gameController;

    public LevelTwo(GameController gameController) {
		super(gameController);

		this.gameController = gameController;
	}

	@Override
	public void startLevel() {
		super.startLevel();

        BossPlane boss = new BossPlane(gameController);
		boss.getDestroyedSignal().connect(this::onBossPlaneDestroyed);
		boss.addToScene();
		getPlayer().getDestroyedSignal().connect(this::loseLevel);
	}

	private void onBossPlaneDestroyed() {
		winLevel();
	}

	@Override
	protected void spawnEnemyUnits() {}
}
