package com.example.demo.level;

import java.util.*;

import com.example.demo.signal.Signal;
import com.example.demo.entity.player.PlayerPlane;
import com.example.demo.entity.EntityDestructible;
import com.example.demo.entity.FighterPlane;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.util.Duration;

public abstract class LevelParent {
	private static final String IMAGE_PATH = "/com/example/demo/images/";
	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final PlayerPlane player;
	private final Scene scene;
	private final ImageView background;

	private final List<EntityDestructible> friendlyUnits;
	private final List<EntityDestructible> enemyUnits;
	private final List<EntityDestructible> playerProjectiles;
	private final List<EntityDestructible> enemyProjectiles;

	private final LevelView levelView;

	private int currentNumberOfEnemies;

	protected final Signal levelWinSignal = new Signal();

	public LevelParent(String backgroundImageName, double screenWidth, double screenHeight) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.player = new PlayerPlane(this);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.playerProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(player);
	}

	public Signal getLevelWinSignal() {
		return levelWinSignal;
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.initializeImages();
		levelView.showHeartDisplay();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	protected void updateScene() {
		spawnEnemyUnits();
		updateEntities();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handlePlayerProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedEntities();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		root.getChildren().add(background);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(EntityDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateEntities() {
		friendlyUnits.forEach(EntityDestructible::updateEntity);
		enemyUnits.forEach(EntityDestructible::updateEntity);
		playerProjectiles.forEach(EntityDestructible::updateEntity);
		enemyProjectiles.forEach(EntityDestructible::updateEntity);
	}

	private void removeAllDestroyedEntities() {
		removeDestroyedEntities(friendlyUnits);
		removeDestroyedEntities(enemyUnits);
		removeDestroyedEntities(playerProjectiles);
		removeDestroyedEntities(enemyProjectiles);
	}

	private void removeDestroyedEntities(List<EntityDestructible> entities) {
		List<EntityDestructible> destroyedEntities = entities.stream().filter(EntityDestructible::isDestroyed)
				.toList();
		root.getChildren().removeAll(destroyedEntities);
		entities.removeAll(destroyedEntities);
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handlePlayerProjectileCollisions() {
		handleCollisions(playerProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private void handleCollisions(List<EntityDestructible> entities1, List<EntityDestructible> entities2) {
		for (EntityDestructible entity : entities2) {
			for (EntityDestructible otherEntity : entities1) {
				if (entity.getBoundsInParent().intersects(otherEntity.getBoundsInParent())) {
					entity.takeDamage();
					otherEntity.takeDamage();
				}
			}
		}
	}

	private void handleEnemyPenetration() {
		for (EntityDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				player.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(player.getHealth());
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			player.incrementKillCount();
		}
	}

	private boolean enemyHasPenetratedDefenses(EntityDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	protected void stopLevel() {
		timeline.stop();
	}

	protected PlayerPlane getPlayer() {
		return player;
	}

	public Group getRoot() {
		return root;
	}

	public List<EntityDestructible> getPlayerProjectiles() {
		return playerProjectiles;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(EntityDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean isPlayerDestroyed() {
		return player.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}
}
