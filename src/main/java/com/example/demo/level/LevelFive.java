package com.example.demo.level;

import com.example.demo.Controller;
import com.example.demo.entity.plane.AdvancedEnemyData;
import com.example.demo.entity.plane.BasicEnemyData;
import com.example.demo.entity.plane.BossEnemyData;
import com.example.demo.entity.plane.BossPlane;
import com.example.demo.factory.*;
import com.example.demo.screen.LevelFiveEndScreen;
import com.example.demo.util.Vector;

public class LevelFive extends Level {
	private static final int FACTORY_1_MAX_ENEMIES = 5;
	private static final int FACTORY_1_ENEMIES_AT_ONCE = 1;
	private static final int FACTORY_2_MAX_ENEMIES = 6;
	private static final int FACTORY_2_ENEMIES_AT_ONCE = 3;
	private static final double FACTORY_2_Y_OFFSET = 128.0;

	private final Controller controller;

	private boolean isBossSpawned;
	private FormationFactory formationFactory1;
	private FormationFactory formationFactory2;

	public LevelFive(Controller controller) {
		super(controller, new LevelFiveEndScreen(), FACTORY_1_MAX_ENEMIES + FACTORY_2_MAX_ENEMIES + 1);

		this.controller = controller;

		this.isBossSpawned = false;
	}

	@Override
	protected void initializeFactories() {
		formationFactory1 = new RandomFormationFactory(controller, new BasicEnemyData());
		formationFactory2 = new VFormationFactory(controller, new AdvancedEnemyData(), FACTORY_2_Y_OFFSET);
	}

	@Override
	protected void spawnEnemyUnits() {
		spawnBossEnemy();
		spawnEnemyFromFactory(formationFactory1, FACTORY_1_ENEMIES_AT_ONCE, FACTORY_1_MAX_ENEMIES);
		spawnEnemyFromFactory(formationFactory2, FACTORY_2_ENEMIES_AT_ONCE, FACTORY_2_MAX_ENEMIES);
	}

	private void spawnBossEnemy() {
		if (isBossSpawned)
			return;

		BossEnemyData bossEnemyData = new BossEnemyData();
		bossEnemyData.getInitialPosition().setX(480.0);
		bossEnemyData.setFinalPosition(new Vector(480.0, -104.0));

		BossPlane bossPlane = new BossPlane(controller, bossEnemyData);
		bossPlane.addToScene();

		bossPlane.getDestroyedSignal().connect(this::onEnemyPlaneDestroyed);

		isBossSpawned = true;
	}

	@Override
	public void startLevel() {
		super.startLevel();

		isBossSpawned = false;
	}
}
