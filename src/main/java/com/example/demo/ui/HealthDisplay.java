package com.example.demo.ui;

import com.example.demo.util.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HealthDisplay extends HBox {
	private static final String HEART_IMAGE_NAME = "heart.png";
	private static final int HEART_HEIGHT = 50;

	private final int numberOfHeartsToDisplay;
	private final Image heartImage;
	
	public HealthDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.numberOfHeartsToDisplay = heartsToDisplay;
		this.heartImage = ImageUtils.getImageFromName(HEART_IMAGE_NAME);

		setTranslateX(xPosition);
		setTranslateY(yPosition);

		initialize();
	}
	
	private void initialize() {
		setHeartCount(numberOfHeartsToDisplay);
	}

	public void setHeartCount(int heartCount) {
		getChildren().clear();

		for (int i = 0; i < heartCount; i++) {
			ImageView heart = new ImageView(heartImage);

			heart.setPreserveRatio(true);
			heart.setFitHeight(HEART_HEIGHT);
			getChildren().add(heart);
		}
	}

	public void reset() {
		getChildren().clear();
		initialize();
	}
}
