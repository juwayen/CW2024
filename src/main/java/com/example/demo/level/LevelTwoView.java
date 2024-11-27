package com.example.demo.level;

import com.example.demo.ui.ShieldImage;
import javafx.scene.Group;

public class LevelTwoView extends LevelView {
	private static final int SHIELD_X_POSITION = 1050;
	private static final int SHIELD_Y_POSITION = 500;

	private final Group root;
	private final ShieldImage shieldImage;
	
	public LevelTwoView(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}
	
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}
	
	public void showShield() {
		shieldImage.show();
	}

	public void hideShield() {
		shieldImage.hide();
	}
}
