package com.example.demo.level;

import com.example.demo.entity.boss.Boss;

public class LevelTwo extends LevelParent {
	private static final String BACKGROUND_IMAGE_NAME = "background_2.png";

	private final Boss boss;

	private LevelTwoView levelView;

    public LevelTwo( double screenWidth, double screenHeight) {
		super(BACKGROUND_IMAGE_NAME, screenWidth, screenHeight);
		boss = new Boss();
	}

	@Override
	protected void updateScene() {
		super.updateScene();
		updateShieldImage();
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getPlayer());
	}

	@Override
	protected void checkIfGameOver() {
		if (isPlayerDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelTwoView(getRoot(), getPlayer().getHealth());
        return levelView;
	}

	private void updateShieldImage() {
		if (boss.getIsShielded()) {
			levelView.showShield();
		} else {
			levelView.hideShield();
		}
	}
}
