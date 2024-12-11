package com.example.demo.entity;

import com.example.demo.service.*;
import com.example.demo.util.*;
import javafx.geometry.Bounds;
import javafx.scene.image.*;

import static com.example.demo.service.UpdateService.MILLISECOND_DELAY;

public abstract class Entity extends ImageView implements Updatable, Collidable {
	private final GameService gameService;
	private final UpdateService updateService;
	private final SceneService sceneService;
	private final CollisionService collisionService;
	private final Signal removed;

	public Signal getRemovedSignal() {
		return removed;
	}

	public Entity(Image image, Vector initialPos) {
		this.gameService = ServiceLocator.getGameService();
		this.updateService = ServiceLocator.getUpdateService();
		this.sceneService = ServiceLocator.getSceneService();
		this.collisionService = ServiceLocator.getCollisionService();
		this.removed = new Signal();

		setImage(image);
		setPosition(initialPos);

		initialize();
	}

	private void initialize() {
		connectSignals();
	}

	private void connectSignals() {
		gameService.getSceneResetSignal().connect(this::onSceneReset);
	}

	protected void onSceneReset() {
		removeFromScene();
	}

	public Vector getPosition() {
		return new Vector(getTranslateX(), getTranslateY());
	}

	public void setPosition(Vector position) {
		setTranslateX(position.getX());
		setTranslateY(position.getY());
	}

	public void addToScene() {
		updateService.addToLoop(this);
		collisionService.enableCollision(this);
		sceneService.addNodeToMiddleLayer(this);
	}

	public void removeFromScene() {
		disableInteraction();
		disableVisuals();

		emitRemovedSignal();
	}

	protected void disableInteraction() {
		updateService.removeFromLoop(this);
		collisionService.disableCollision(this);
	}

	protected void disableVisuals() {
		sceneService.removeNodeFromMiddleLayer(this);
	}

	protected void emitRemovedSignal() {
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
	public Bounds getHitbox() {
		return getBoundsInParent();
	}

	@Override
	public void onCollision(Collidable collidable) {}
}
