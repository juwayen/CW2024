package com.example.demo.level;

import com.example.demo.Controller;
import com.example.demo.entity.plane.AdvancedEnemyData;
import com.example.demo.entity.plane.BasicEnemyData;
import com.example.demo.factory.*;
import com.example.demo.screen.LevelThreeEndScreen;

public class LevelThree extends Level {
	private static final int FACTORY_1_MAX_ENEMIES = 10;
	private static final int FACTORY_1_ENEMIES_AT_ONCE = 3;
	private static final int FACTORY_2_MAX_ENEMIES = 3;
	private static final int FACTORY_2_ENEMIES_AT_ONCE = 1;

	private final Controller controller;

	private FormationFactory formationFactory1;
	private FormationFactory formationFactory2;

	public LevelThree(Controller controller) {
		super(controller, new LevelThreeEndScreen(), FACTORY_1_MAX_ENEMIES + FACTORY_2_MAX_ENEMIES);

		this.controller = controller;
	}

	@Override
	protected void initializeFactories() {
		formationFactory1 = new VFormationFactory(controller, new BasicEnemyData());
		formationFactory2 = new RandomFormationFactory(controller, new AdvancedEnemyData());
	}

	@Override
	protected void spawnEnemyUnits() {
		spawnEnemyFromFactory(formationFactory1, FACTORY_1_ENEMIES_AT_ONCE, FACTORY_1_MAX_ENEMIES);
		spawnEnemyFromFactory(formationFactory2, FACTORY_2_ENEMIES_AT_ONCE, FACTORY_2_MAX_ENEMIES);
	}
}
