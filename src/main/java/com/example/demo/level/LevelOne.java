package com.example.demo.level;

import com.example.demo.Controller;
import com.example.demo.entity.plane.BasicEnemyData;
import com.example.demo.factory.*;
import com.example.demo.screen.LevelOneEndScreen;

public class LevelOne extends Level {
	private static final int FACTORY_MAX_ENEMIES = 3;
	private static final int FACTORY_ENEMIES_AT_ONCE = 3;

	private final Controller controller;

    private FormationFactory formationFactory;

	public LevelOne(Controller controller) {
		super(controller, new LevelOneEndScreen(), FACTORY_MAX_ENEMIES);

		this.controller = controller;
	}

	@Override
	protected void initializeFactories() {
		formationFactory = new RandomFormationFactory(controller, new BasicEnemyData());
	}

	@Override
	protected void spawnEnemyUnits() {
		spawnEnemyFromFactory(formationFactory, FACTORY_ENEMIES_AT_ONCE, FACTORY_MAX_ENEMIES);
	}
}
