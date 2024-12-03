package com.example.demo.screen;

import com.example.demo.controller.Input;
import com.example.demo.entity.Updatable;
import com.example.demo.signal.Signal;
import com.example.demo.util.ImageUtils;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static com.example.demo.Main.OUTPUT_SCALE;

public class StartScreen extends Group implements Updatable {
    private static final double LABEL_LEFT_PADDING = 150.0;
    private static final double LABEL_TOP_PADDING = 822.5;
    private static final double IMAGE_LEFT_PADDING = 296.0;
    private static final double IMAGE_TOP_PADDING = 122.5;

    private final Signal started;
    private final Font pixelFont;

    public Signal getStartedSignal() {
        return started;
    }

    public StartScreen() {
        this.started = new Signal();
        this.pixelFont = Font.loadFont(getClass().getResourceAsStream("/com/example/demo/fonts/ArcadeClassic.ttf"), 64 / OUTPUT_SCALE);

        initialize();
    }

    private void initialize() {
        addToGameLoop();

        ImageView planeImage = new ImageView(ImageUtils.getImageFromName("start_menu_plane.png"));
        planeImage.setTranslateX(IMAGE_LEFT_PADDING / OUTPUT_SCALE);
        planeImage.setTranslateY(IMAGE_TOP_PADDING / OUTPUT_SCALE);

        Label label = new Label("Press Any Key To Start");
        label.setFont(pixelFont);
        label.setTextFill(Color.WHITE);
        label.setTranslateX(LABEL_LEFT_PADDING / OUTPUT_SCALE);
        label.setTranslateY(LABEL_TOP_PADDING / OUTPUT_SCALE);

        getChildren().add(planeImage);
        getChildren().add(label);
    }

    @Override
    public void update() {
        if (Input.isAnyKeyActive()) {
            fadeout();
            removeFromGameLoop();
        }
    }

    private void fadeout() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.25), this);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> started.emit());
        fadeTransition.play();
    }
}
