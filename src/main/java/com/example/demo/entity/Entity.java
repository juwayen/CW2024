package com.example.demo.entity;

import com.example.demo.service.*;
import com.example.demo.util.*;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.*;

import static com.example.demo.service.UpdateService.MILLISECOND_DELAY;

/**
 * Abstract base class for all game entities.
 */
public abstract class Entity extends ImageView implements Updatable, Collidable {
	private final GameService gameService;
	private final UpdateService updateService;
	private final SceneService sceneService;
	private final CollisionService collisionService;
	private final Signal removed;

	/**
	 * Getter method for the removed {@link Signal} associated with the removal of this entity from the scene.
	 *
	 * @return The {@link Signal} instance that is emitted when the entity is removed.
	 */
	public Signal getRemovedSignal() {
		return removed;
	}

	/**
	 * Constructs an {@link Entity} and initializes its position and appearance using the provided image and position.
	 *
	 * @param image The {@link Image} to be used for the visual representation.
	 * @param initialPos The {@link Vector} representing the initial position.
	 */
	public Entity(Image image, Vector initialPos) {
		this.gameService = ServiceLocator.getGameService();
		this.updateService = ServiceLocator.getUpdateService();
		this.sceneService = ServiceLocator.getSceneService();
		this.collisionService = ServiceLocator.getCollisionService();
		this.removed = new Signal();

		setImage(image);
		setPosition(initialPos);

		connectSignals();
	}

	/**
	 * Establishes signal connections for the {@link Entity} to respond to game events.
	 * Connects the scene reset {@link com.example.demo.util.Signal} from {@link GameService} to the {@link #onSceneReset()} method.
	 */
	private void connectSignals() {
		gameService.getSceneResetSignal().connect(this::onSceneReset);
	}

	/**
	 * Handles the resetting of the scene by invoking the {@link #removeFromScene()} method.
	 */
	protected void onSceneReset() {
		removeFromScene();
	}

	/**
	 * Removes the {@link Entity} from the scene by invoking {@link #disableInteraction()}, {@link #disableVisuals()}, and {@link #emitRemovedSignal()}.
	 */
	public void removeFromScene() {
		disableInteraction();
		disableVisuals();
		emitRemovedSignal();
	}

	/**
	 * Disables the interaction capabilities by unregistering it from the {@link UpdateService} and {@link CollisionService}.
	 */
	protected void disableInteraction() {
		updateService.removeFromLoop(this);
		collisionService.disableCollision(this);
	}

	/**
	 * Disables the visual component by invoking {@link SceneService#removeNodeFromMiddleLayer(Node)}.
	 */
	protected void disableVisuals() {
		sceneService.removeNodeFromMiddleLayer(this);
	}

	/**
	 * Emits the removed {@link Signal} and invokes {@link #clearSignalsConnections()}.
	 */
	protected void emitRemovedSignal() {
		removed.emit();
		clearSignalsConnections();
	}

	/**
	 * Invokes {@link Signal#clearConnections()} on all {@link Signal}s.
	 */
	protected void clearSignalsConnections() {
		removed.clearConnections();
	}

	/**
	 * Adds the current instance to the scene by registering it with {@link UpdateService}, {@link CollisionService}, and {@link SceneService}.
	 */
	public void addToScene() {
		updateService.addToLoop(this);
		collisionService.enableCollision(this);
		sceneService.addNodeToMiddleLayer(this);
	}

	/**
	 * Retrieves the current position as a {@link Vector}.
	 *
	 * @return A {@link Vector} representing the current position.
	 */
	public Vector getPosition() {
		return new Vector(getTranslateX(), getTranslateY());
	}

	/**
	 * Updates the position by setting its translation coordinates.
	 *
	 * @param position The {@link Vector} representing the new position to set.
	 */
	public void setPosition(Vector position) {
		setTranslateX(position.getX());
		setTranslateY(position.getY());
	}

	/**
	 * Moves in a specified direction by a given magnitude.
	 *
	 * @param direction The {@link Vector} representing the direction of movement.
	 * @param magnitude The magnitude of movement.
	 */
	public void move(Vector direction, double magnitude) {
		Vector scaledVector = direction.multiply(magnitude * MILLISECOND_DELAY);
		Vector newPosition = getPosition().add(scaledVector);
		setPosition(newPosition);
	}

	/**
	 * Update method to be overridden by subclasses.
	 */
	@Override
	public void update() {}

	/**
	 * Retrieves the hitbox.
	 *
	 * @return A {@link Bounds} object representing the hitbox.
	 */
	@Override
	public Bounds getHitbox() {
		return getBoundsInParent();
	}

	/**
	 * Handles collision logic.
	 *
	 * @param collidable The other {@link Collidable} object involved in the collision.
	 */
	@Override
	public void onCollision(Collidable collidable) {}
}
