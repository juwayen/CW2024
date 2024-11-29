package com.example.demo.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

import static com.example.demo.Main.IMAGE_PATH;

public class HeartDisplay extends HBox {
	private static final String HEART_IMAGE_NAME = "heart.png";
	private static final int HEART_HEIGHT = 50;

	private final int numberOfHeartsToDisplay;
	private final Image heartImage;
	
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.numberOfHeartsToDisplay = heartsToDisplay;
		this.heartImage = new Image(Objects.requireNonNull(getClass().getResource(IMAGE_PATH + HEART_IMAGE_NAME)).toExternalForm());

		relocate(xPosition, yPosition);

		initialize();
	}
	
	private void initialize() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(heartImage);

			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			getChildren().add(heart);
		}
	}
	
	public void removeHeart() {
		if (!getChildren().isEmpty())
			getChildren().remove(0);
	}

	public void reset() {
		getChildren().clear();
		initialize();
	}
}
