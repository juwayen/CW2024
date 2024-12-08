package com.example.demo.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthDisplay extends StackPane {
	private static final double BAR_HEIGHT = 32;
	private static final double BAR_WIDTH = 256;
	private static final double BAR_OUTLINE_WIDTH = 8;

    private final Rectangle healthBar;
	private final int totalHealth;

	private int currentHealth;

	public HealthDisplay(double xPosition, double yPosition, int totalHealth) {
		this.healthBar = new Rectangle();
		this.totalHealth = totalHealth;

		this.currentHealth = totalHealth;

		setTranslateX(xPosition);
		setTranslateY(yPosition);

		initialize();
	}

	private void initialize() {
		healthBar.setWidth(BAR_WIDTH);
		healthBar.setHeight(BAR_HEIGHT);
		healthBar.setFill(Color.RED);

		Rectangle backgroundBar = new Rectangle(BAR_WIDTH + BAR_OUTLINE_WIDTH * 2, BAR_HEIGHT + BAR_OUTLINE_WIDTH * 2);
		backgroundBar.setFill(Color.BLACK);

		getChildren().addAll(backgroundBar, healthBar);
	}

	public void setHealth(int health) {
		this.currentHealth = Math.max(0, Math.min(health, totalHealth));
		updateHealthBar();
	}

	private void updateHealthBar() {
		double healthPercentage = (double) currentHealth / totalHealth;
		healthBar.setWidth(BAR_WIDTH * healthPercentage);

		healthBar.setTranslateX(-BAR_WIDTH / 2.0 + healthBar.getWidth() / 2.0);
	}

	public void reset() {
		setHealth(totalHealth);
	}
}