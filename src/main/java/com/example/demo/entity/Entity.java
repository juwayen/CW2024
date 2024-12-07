package com.example.demo.entity;

import com.example.demo.Controller;
import com.example.demo.service.*;
import com.example.demo.util.*;
import javafx.geometry.Bounds;
import javafx.scene.image.*;

import static com.example.demo.service.GameLoopService.MILLISECOND_DELAY;

public abstract class Entity extends ImageView implements Updatable, Collidable {
	private final Controller controller;
	private final Signal removed;
	private final GameLoopService gameLoopService;
	private final SceneService sceneService;
	private final CollisionService collisionService;

	public Signal getRemovedSignal() {
		return removed;
	}

	public Entity(Controller controller, Image image, Vector initialPos) {
		this.controller = controller;
		this.removed = new Signal();
		this.gameLoopService = ServiceLocator.getGameLoopService();
		this.sceneService = ServiceLocator.getSceneService();
		this.collisionService = ServiceLocator.getCollisionService();

		setImage(image);
		setPosition(initialPos);

		initialize();
	}

	private void initialize() {
		connectSignals();
	}

	private void connectSignals() {
		controller.getSceneResetSignal().connect(this::onSceneReset);
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
		gameLoopService.addToLoop(this);
		collisionService.enableCollision(this);
		sceneService.addNodeToMiddleLayer(this);
	}

	public void removeFromScene() {
		disableInteraction();
		disableVisuals();

		emitRemovedSignal();
	}

	protected void disableInteraction() {
		gameLoopService.removeFromLoop(this);
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
