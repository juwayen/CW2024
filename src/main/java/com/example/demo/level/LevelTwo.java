package com.example.demo.level;

import com.example.demo.Controller;
import com.example.demo.entity.plane.BossPlane;
import com.example.demo.screen.LevelTwoEndScreen;

public class LevelTwo extends Level {
	private final Controller controller;

    public LevelTwo(Controller controller) {
		super(controller, new LevelTwoEndScreen());

		this.controller = controller;
	}

	@Override
	public void startLevel() {
		super.startLevel();

        BossPlane boss = new BossPlane(controller);
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
