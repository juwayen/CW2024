package com.example.demo.entity;

import com.example.demo.controller.*;
import com.example.demo.util.Vector;
import javafx.geometry.Bounds;
import javafx.scene.image.*;

import java.util.Objects;

import static com.example.demo.Main.IMAGE_PATH;
import static com.example.demo.controller.GameLoop.MILLISECOND_DELAY;

public abstract class Entity extends ImageView implements Updatable, Collidable {
	private static final CollisionEngine COLLISION_ENGINE = CollisionEngine.getInstance();

	private final GameController gameController;

	public Entity(GameController gameController, String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.gameController = gameController;

		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + imageName)).toExternalForm()));
		setPreserveRatio(true);
		setFitHeight(imageHeight);
		relocate(initialXPos, initialYPos);

		initialize();
	}

	private void initialize() {
		connectSignals();
	}

	private void connectSignals() {
		gameController.getSceneResetSignal().connect(this::onSceneReset);
	}

	protected void onSceneReset() {
		removeFromScene();
	}

	public void addToScene() {
		GameLoop.addToLoop(this);
		COLLISION_ENGINE.enableCollision(this);
		gameController.addNodeToRoot(this);
	}

	public void removeFromScene() {
		GameLoop.removeFromLoop(this);
		COLLISION_ENGINE.disableCollision(this);
		gameController.removeNodeFromRoot(this);
	}

	public void move(Vector direction, double magnitude) {
		Vector scaledVector = direction.duplicate();
		scaledVector.multiply(magnitude * MILLISECOND_DELAY);

		double newX = getTranslateX() + scaledVector.getX();
		double newY = getTranslateY() + scaledVector.getY();

		setTranslateX(newX);
		setTranslateY(newY);
	}

	@Override
	public void update() {}

	@Override
	public void onCollision(Collidable collidable) {}

	@Override
	public Bounds getHitbox() {
		return getBoundsInParent();
	}

	@Override
	public abstract boolean isFriendly();
}
