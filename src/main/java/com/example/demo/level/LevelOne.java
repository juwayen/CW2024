package com.example.demo.level;

import com.example.demo.entity.plane.BasicEnemyData;
import com.example.demo.factory.*;
import com.example.demo.screen.LevelOneEndScreen;

/**
 * A concrete implementation of the {@link Level} class, representing the first level.
 */
public class LevelOne extends Level {
	private static final int FACTORY_MAX_ENEMIES = 3;
	private static final int FACTORY_ENEMIES_AT_ONCE = 3;

    private FormationFactory formationFactory;

	/**
	 * Constructs a new instance of {@link LevelOne}.
	 * This level is configured with the number of kills to win and the level end {@link com.example.demo.screen.GameScreen}.
	 */
	public LevelOne() {
		super(new LevelOneEndScreen(), FACTORY_MAX_ENEMIES);
	}

	/**
	 * Initializes the {@link FormationFactory}s for generating enemies in the level.
	 * Configures the factories with a specific {@link com.example.demo.entity.plane.PlaneData}.
	 */
	@Override
	protected void initializeFactories() {
		formationFactory = new RandomFormationFactory(new BasicEnemyData());
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
