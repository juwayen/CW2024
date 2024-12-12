package com.example.demo.level;

import com.example.demo.entity.plane.AdvancedEnemyData;
import com.example.demo.entity.plane.BasicEnemyData;
import com.example.demo.factory.*;
import com.example.demo.screen.LevelFourEndScreen;

/**
 * A concrete implementation of the {@link Level} class, representing the fourth level.
 */
public class LevelFour extends Level {
	private static final int FACTORY_1_MAX_ENEMIES = 10;
	private static final int FACTORY_1_ENEMIES_AT_ONCE = 5;
	private static final double FACTORY_1_Y_OFFSET = 0.0;
	private static final int FACTORY_2_MAX_ENEMIES = 3;
	private static final int FACTORY_2_ENEMIES_AT_ONCE = 3;
	private static final double FACTORY_2_Y_OFFSET = 0.0;

	private FormationFactory formationFactory1;
	private FormationFactory formationFactory2;

	/**
	 * Constructs a new instance of {@link LevelFour}.
	 * This level is configured with the number of kills to win and the level end {@link com.example.demo.screen.GameScreen}.
	 */
	public LevelFour() {
		super(new LevelFourEndScreen(), FACTORY_1_MAX_ENEMIES + FACTORY_2_MAX_ENEMIES);
	}

	/**
	 * Initializes the {@link FormationFactory}s for generating enemies in the level.
	 * Configures the factories with a specific {@link com.example.demo.entity.plane.PlaneData}.
	 */
	@Override
	protected void initializeFactories() {
		formationFactory1 = new WallFormationFactory(new BasicEnemyData(), FACTORY_1_Y_OFFSET);
		formationFactory2 = new VFormationFactory(new AdvancedEnemyData(), FACTORY_2_Y_OFFSET);
	}

	/**
	 * Spawns {@link com.example.demo.entity.plane.EnemyPlane}s using the defined {@link FormationFactory}s.
	 * Invokes the {@link #spawnEnemyFromFactory(FormationFactory, int, int)} method.
	 */
	@Override
	protected void spawnEnemyUnits() {
		spawnEnemyFromFactory(formationFactory1, FACTORY_1_ENEMIES_AT_ONCE, FACTORY_1_MAX_ENEMIES);
		spawnEnemyFromFactory(formationFactory2, FACTORY_2_ENEMIES_AT_ONCE, FACTORY_2_MAX_ENEMIES);
	}
}
