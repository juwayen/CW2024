package com.example.demo.level;

import com.example.demo.Controller;
import com.example.demo.entity.plane.BasicEnemyData;
import com.example.demo.factory.*;
import com.example.demo.screen.LevelTwoEndScreen;

public class LevelTwo extends Level {
	private static final int FACTORY_MAX_ENEMIES = 15;
	private static final int FACTORY_ENEMIES_AT_ONCE = 5;
	private static final double FACTORY_Y_OFFSET = 128;

	private final Controller controller;

	private FormationFactory formationFactory;

	public LevelTwo(Controller controller) {
		super(controller, new LevelTwoEndScreen(), FACTORY_MAX_ENEMIES);

		this.controller = controller;
	}

	@Override
	protected void initializeFactories() {
		formationFactory = new WallFormationFactory(controller, new BasicEnemyData(), FACTORY_Y_OFFSET);
	}

	@Override
	protected void spawnEnemyUnits() {
		spawnEnemyFromFactory(formationFactory, FACTORY_ENEMIES_AT_ONCE, FACTORY_MAX_ENEMIES);
	}
}
