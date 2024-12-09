package com.example.demo.level;

import com.example.demo.Controller;
import com.example.demo.entity.plane.BossEnemy;
import com.example.demo.screen.LevelFiveEndScreen;
import com.example.demo.util.Vector;

public class LevelFive extends Level {
	private final Controller controller;

    public LevelFive(Controller controller) {
		super(controller, new LevelFiveEndScreen());

		this.controller = controller;
	}

	@Override
	public void startLevel() {
		super.startLevel();

        BossEnemy boss = new BossEnemy(controller, 84.0, new Vector(84.0, -182.0));
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
