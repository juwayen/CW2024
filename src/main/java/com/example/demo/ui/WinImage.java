package com.example.demo.ui;

public class WinImage extends ImageParent {
	private static final String IMAGE_NAME = "you_win.png";
	private static final int HEIGHT = 500;

	public WinImage(double xPosition, double yPosition) {
		super(IMAGE_NAME, HEIGHT, xPosition, yPosition);
	}
}
