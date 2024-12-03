package com.example.demo.screen;

import com.example.demo.controller.Input;
import com.example.demo.entity.Updatable;
import com.example.demo.signal.Signal;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static com.example.demo.Main.*;

public class GameScreen extends Group implements Updatable {
    private static final double LABEL_MILLISECONDS_DELAY = 50;

    private final String labelText;
    private final Signal continued;
    private final Font pixelFont;

    public Signal getContinuedSignal() {
        return continued;
    }

    protected GameScreen(String labelText) {
        this.labelText = labelText;
        this.continued = new Signal();
        this.pixelFont = Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/ArcadeClassic.ttf"), 64 / OUTPUT_SCALE);
    }

    public void activate() {
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(GAME_WIDTH / OUTPUT_SCALE, GAME_HEIGHT / OUTPUT_SCALE);

        Label label = new Label();
        label.setFont(pixelFont);
        label.setTextFill(Color.WHITE);
        label.setTextAlignment(TextAlignment.CENTER);

        stackPane.getChildren().add(label);

        getChildren().add(stackPane);

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