package com.example.demo.screen;

import com.example.demo.controller.Input;
import com.example.demo.controller.Updatable;
import com.example.demo.signal.Signal;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static com.example.demo.Main.*;

public class GameScreen extends StackPane implements Updatable {
    private static final String FONT_PATH = "/com/example/demo/fonts/ArcadeClassic.ttf";
    private static final double FONT_SIZE = 64;
    private static final double LABEL_MILLISECONDS_DELAY = 50;

    private final String labelText;
    private final Signal continued;
    private final Font pixelFont;
    private final Label label;

    public Signal getContinuedSignal() {
        return continued;
    }

    protected Label getLabel() {
        return label;
    }

    protected GameScreen(String labelText) {
        this.labelText = labelText;
        this.continued = new Signal();
        this.pixelFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), FONT_SIZE / OUTPUT_SCALE);
        this.label = new Label();

        setPrefSize(GAME_WIDTH / OUTPUT_SCALE, GAME_HEIGHT / OUTPUT_SCALE);
    }

    public void start() {
        label.setFont(pixelFont);
        label.setTextFill(Color.WHITE);
        label.setTextAlignment(TextAlignment.CENTER);

        getChildren().add(label);

        animateText(label);
    }

    private void animateText(Label label) {
        Timeline timeline = new Timeline();

        for (int i = 0; i < labelText.length(); i++) {
            final char currentChar = labelText.charAt(i);

            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(LABEL_MILLISECONDS_DELAY * i),
                    event -> label.setText(label.getText() + currentChar)
            );

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setOnFinished(event -> addToGameLoop());

        timeline.play();
    }

    @Override
    public void update() {
        if (Input.isAnyKeyActive()) {
            continued.emit();
            removeFromGameLoop();
        }
    }
}