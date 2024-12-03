package com.example.demo.entity;

import com.example.demo.controller.*;
import com.example.demo.signal.Signal;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;
import javafx.geometry.Bounds;
import javafx.scene.image.*;

import static com.example.demo.Main.OUTPUT_SCALE;
import static com.example.demo.controller.GameLoop.MILLISECOND_DELAY;

public abstract class Entity extends ImageView implements Updatable, Collidable {
	private static final CollisionEngine COLLISION_ENGINE = CollisionEngine.getInstance();

	private final GameController gameController;
	private final Signal removed;

	public Signal getRemovedSignal() {
		return removed;
	}

	public Entity(GameController gameController, String imageName, double initialXPos, double initialYPos) {
		this.gameController = gameController;
		this.removed = new Signal();

		setImage(ImageUtils.getImageFromName(imageName));
		setPosition(new Vector(initialXPos, initialYPos));

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

	public Vector getPosition() {
		return new Vector(getTranslateX(), getTranslateY()).multiply(OUTPUT_SCALE);
	}

	public void setPosition(Vector position) {
		setTranslateX(position.getX() / OUTPUT_SCALE);
		setTranslateY(position.getY() / OUTPUT_SCALE);
	}

	public void addToScene() {
		addToGameLoop();
		COLLISION_ENGINE.enableCollision(this);
		gameController.addNodeToRoot(this);
	}

	public void removeFromScene() {
		removeFromGameLoop();
		COLLISION_ENGINE.disableCollision(this);
		gameController.removeNodeFromRoot(this);
		removed.emit();
		clearSignalsConnections();
	}

	protected void clearSignalsConnections() {
		removed.clearConnections();
	}

	public void move(Vector direction, double magnitude) {
		Vector scaledVector = direction.multiply(magnitude * MILLISECOND_DELAY);
		Vector newPosition = getPosition().add(scaledVector);
		setPosition(newPosition);
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
