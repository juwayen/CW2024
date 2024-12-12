package com.example.demo.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a visual health bar display designed to indicate the player's current health status.
 */
public class HealthDisplay extends StackPane {
	private static final double BAR_HEIGHT = 32;
	private static final double BAR_WIDTH = 256;
	private static final double BAR_OUTLINE_WIDTH = 8;

    private final Rectangle healthBar;
	private final int totalHealth;

	private int currentHealth;

	/**
	 * Setter method for the current health.
	 * Updates the health bar display accordingly by invoking {@link #updateHealthBar()}.
	 *
	 * @param health The new health value to set, which is constrained within valid limits.
	 */
	public void setHealth(int health) {
		this.currentHealth = Math.max(0, Math.min(health, totalHealth));
		updateHealthBar();
	}

	/**
	 * Constructs a visual health bar display at the specified position and initializes it with the total health value.
	 *
	 * @param xPosition The x-coordinate for the position of the health bar.
	 * @param yPosition The y-coordinate for the position of the health bar.
	 * @param totalHealth The total health value represented by the health bar.
	 */
	public HealthDisplay(double xPosition, double yPosition, int totalHealth) {
		this.healthBar = new Rectangle();
		this.totalHealth = totalHealth;

		this.currentHealth = totalHealth;

		setTranslateX(xPosition);
		setTranslateY(yPosition);

		initializeHealthBar();
	}

	/**
	 * Initializes the health bar display by setting its dimensions, color, and background.
	 */
	private void initializeHealthBar() {
		healthBar.setWidth(BAR_WIDTH);
		healthBar.setHeight(BAR_HEIGHT);
		healthBar.setFill(Color.RED);

		Rectangle backgroundBar = new Rectangle(BAR_WIDTH + BAR_OUTLINE_WIDTH * 2, BAR_HEIGHT + BAR_OUTLINE_WIDTH * 2);
		backgroundBar.setFill(Color.BLACK);

		getChildren().addAll(backgroundBar, healthBar);
	}

	/**
	 * Updates the visual representation of the health bar based on the current health value.
	 */
	private void updateHealthBar() {
		double healthPercentage = (double) currentHealth / totalHealth;
		healthBar.setWidth(BAR_WIDTH * healthPercentage);

		healthBar.setTranslateX(-BAR_WIDTH / 2.0 + healthBar.getWidth() / 2.0);
	}

	/**
	 * Resets the current health value to the initial value by invoking {@link #setHealth(int)}
	 */
	public void reset() {
		setHealth(totalHealth);
	}
}