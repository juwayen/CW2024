package com.example.demo.entity;

import com.example.demo.GameController;
import com.example.demo.service.*;
import com.example.demo.util.*;
import javafx.geometry.Bounds;
import javafx.scene.image.*;

import static com.example.demo.service.GameLoopService.MILLISECOND_DELAY;

public abstract class Entity extends ImageView implements Updatable, Collidable {
	private final GameController gameController;
	private final Signal removed;
	private final GameLoopService gameLoopService;
	private final SceneService sceneService;
	private final CollisionService collisionService;

	public Signal getRemovedSignal() {
		return removed;
	}

	public Entity(GameController gameController, String imageName, Vector initialPos) {
		this.gameController = gameController;
		this.removed = new Signal();
		this.gameLoopService = ServiceLocator.getGameLoopService();
		this.sceneService = ServiceLocator.getSceneService();
		this.collisionService = ServiceLocator.getCollisionService();

		setImage(ImageUtils.getImageFromName(imageName));
		setPosition(initialPos);

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
		gameLoopService.removeFromLoop(this);
		collisionService.disableCollision(this);
		sceneService.removeNodeFromMiddleLayer(this);

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
}
