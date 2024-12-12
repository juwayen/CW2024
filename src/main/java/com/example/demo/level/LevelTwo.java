package com.example.demo.level;

import com.example.demo.entity.plane.BasicEnemyData;
import com.example.demo.factory.*;
import com.example.demo.screen.LevelTwoEndScreen;

/**
 * A concrete implementation of the {@link Level} class, representing the second level.
 */
public class LevelTwo extends Level {
	private static final int FACTORY_MAX_ENEMIES = 15;
	private static final int FACTORY_ENEMIES_AT_ONCE = 5;
	private static final double FACTORY_Y_OFFSET = 128;

	private FormationFactory formationFactory;

	/**
	 * Constructs a new instance of {@link LevelTwo}.
	 * This level is configured with the number of kills to win and the level end {@link com.example.demo.screen.GameScreen}.
	 */
	public LevelTwo() {
		super(new LevelTwoEndScreen(), FACTORY_MAX_ENEMIES);
	}

	/**
	 * Initializes the {@link FormationFactory}s for generating enemies in the level.
	 * Configures the factories with a specific {@link com.example.demo.entity.plane.PlaneData}.
	 */
	@Override
	protected void initializeFactories() {
		formationFactory = new WallFormationFactory(new BasicEnemyData(), FACTORY_Y_OFFSET);
	}

	/**
	 * Spawns {@link com.example.demo.entity.plane.EnemyPlane}s using the defined {@link FormationFactory}s.
	 * Invokes the {@link #spawnEnemyFromFactory(FormationFactory, int, int)} method.
	 */
	@Override
	protected void spawnEnemyUnits() {
		spawnEnemyFromFactory(formationFactory, FACTORY_ENEMIES_AT_ONCE, FACTORY_MAX_ENEMIES);
	}
}
