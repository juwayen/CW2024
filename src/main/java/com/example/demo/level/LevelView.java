package com.example.demo.level;

import com.example.demo.ui.GameOverImage;
import com.example.demo.ui.HeartDisplay;
import com.example.demo.ui.WinImage;
import javafx.scene.Group;

public class LevelView {
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(0, 0);
		this.gameOverImage = new GameOverImage(0, 0);
	}

	public void initializeImages() {
		root.getChildren().add(gameOverImage);
		gameOverImage.moveToCenter();

		root.getChildren().add(winImage);
		winImage.moveToCenter();
	}
	
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void showWinImage() {
		winImage.show();
	}
	
	public void showGameOverImage() {
		gameOverImage.show();
	}
	
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}
}
