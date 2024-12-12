package com.example.demo.screen;

import com.example.demo.service.UpdateService;
import com.example.demo.service.InputService;
import com.example.demo.service.ServiceLocator;
import com.example.demo.service.Updatable;
import com.example.demo.util.Signal;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static com.example.demo.Main.*;

/**
 * Represents a screen in the game that displays a centered label with animated text.
 */
public class GameScreen extends StackPane implements Updatable {
    private static final String FONT_PATH = "/com/example/demo/fonts/ArcadeClassic.ttf";
    private static final double FONT_SIZE = 64;
    private static final double LABEL_MILLISECONDS_DELAY = 50;

    private final UpdateService updateService;
    private final InputService inputService;
    private final String labelText;
    private final Signal continued;
    private final Font pixelFont;
    private final Label label;

    /**
     * Getter method for the continued {@link Signal}.
     *
     * @return The continued {@link Signal}.
     */
    public Signal getContinuedSignal() {
        return continued;
    }

    /**
     * Getter method for the {@link Label} instance displayed on the game screen.
     *
     * @return The {@link Label} instance associated with this screen.
     */
    protected Label getLabel() {
        return label;
    }

    /**
     * Constructs a new {@link GameScreen} instance with a specified label text.
     *
     * @param labelText The text to be displayed as the animated label on this screen.
     */
    protected GameScreen(String labelText) {
        this.updateService = ServiceLocator.getUpdateService();
        this.inputService = ServiceLocator.getInputService();
        this.labelText = labelText;
        this.continued = new Signal();
        this.pixelFont = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), FONT_SIZE);
        this.label = new Label();

        getChildren().add(label);
        setPrefSize(GAME_WIDTH, GAME_HEIGHT);
    }

    /**
     * Starts the game screen by initializing the label properties and invoking the {@link #animateText(Label)} method.
     */
    public void start() {
        label.setText("");

        label.setFont(pixelFont);
        label.setTextFill(Color.WHITE);
        label.setTextAlignment(TextAlignment.CENTER);

        animateText(label);
    }

    /**
     * Animates the text of the provided {@link Label} by progressively appending characters.
     *
     * @param label The {@link Label} to animate text for.
     */
    private void animateText(Label label) {
        Timeline timeline = new Timeline();

        for (int i = 0; i < labelText.length(); i++) {
            char currentChar = labelText.charAt(i);

            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(LABEL_MILLISECONDS_DELAY * i),
                    event -> label.setText(label.getText() + currentChar)
            );

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setOnFinished(event -> updateService.addToLoop(this));

        timeline.play();
    }

    /**
     * Update method to check if the user pressed any key.
     * Emits the continued {@link Signal} when any key is pressed.
     */
    @Override
    public void update() {
        if (inputService.isAnyKeyActive()) {
            continued.emit();
            updateService.removeFromLoop(this);
        }
    }
}