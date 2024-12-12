package com.example.demo.service;

import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.level.*;
import com.example.demo.screen.GameOverScreen;
import com.example.demo.screen.GameScreen;
import com.example.demo.screen.StartScreen;
import com.example.demo.screen.WinScreen;
import com.example.demo.ui.*;
import com.example.demo.util.Signal;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for managing the game's lifecycle.
 */
public class GameService {
    private final SceneService sceneService;
    private final AudioService audioService;
    private final Signal sceneReset;
    private final StartScreen startScreen;
    private final List<Level> levelsOrdered;

    private PlayerPlane player;
    private UserInterface userInterface;
    private Level currentLevel;
    private int nextLevelIndex;

    /**
     * Getter method for the scene reset {@link Signal}.
     *
     * @return The {@link Signal} associated with resetting the current scene.
     */
    public Signal getSceneResetSignal() {
        return sceneReset;
    }

    /**
     * Getter method for the {@link PlayerPlane} instance.
     *
     * @return The {@link PlayerPlane} instance.
     */
    public PlayerPlane getPlayer() {
        return player;
    }

    /**
     * Constructs a new instance of {@link GameService}.
     * Initializes various services and components required for the game's functionality.
     */
    public GameService() {
        this.sceneService = ServiceLocator.getSceneService();
        this.audioService = ServiceLocator.getAudioService();
        this.sceneReset = new Signal();
        this.startScreen = new StartScreen();
        this.levelsOrdered = new ArrayList<>();

        this.nextLevelIndex = 0;
    }

    /**
     * Initializes the game by invoking {@link #initialize()}, then launches the game.
     */
    public void launchGame() {
        initialize();
        sceneService.addNodeToMiddleLayer(startScreen);
        startScreen.start();
        audioService.startBackgroundMusic();
    }

    /**
     * Initializes essential components for the game service.
     */
    private void initialize() {
        player = new PlayerPlane();
        userInterface = new UserInterface();
        initializeLevels();
        connectSignals();
    }

    /**
     * Initializes and orders the {@link Level}s for the game.
     */
    private void initializeLevels() {
        levelsOrdered.add(new LevelOne());
        levelsOrdered.add(new LevelTwo());
        levelsOrdered.add(new LevelThree());
        levelsOrdered.add(new LevelFour());
        levelsOrdered.add(new LevelFive());
    }

    /**
     * Establishes signal connections for the {@link GameService} to respond to game events.
     * Connects the entered level {@link com.example.demo.util.Signal} from {@link PlayerPlane} to the {@link #onPlayerEnteredLevel()} method.
     * Connects the exited level {@link com.example.demo.util.Signal} from {@link PlayerPlane} to the {@link #onPlayerExitedLevel()} method.
     * Connects the continued {@link com.example.demo.util.Signal} from {@link StartScreen} to the {@link #startGame()} method.
     */
    private void connectSignals() {
        player.getEnteredLevelSignal().connect(this::onPlayerEnteredLevel);
        player.getExitedLevelSignal().connect(this::onPlayerExitedLevel);
        startScreen.getContinuedSignal().connect(this::startGame);

        for (Level level : levelsOrdered) {
            level.getLevelWon().connect(this::onLevelWon);
            level.getLevelLost().connect(this::loseGame);
        }
    }

    /**
     * Invokes the {@link Level#startLevel()} method to run the current level.
     */
    private void onPlayerEnteredLevel() {
        currentLevel.startLevel();
    }

    /**
     * Connects the continued {@link com.example.demo.util.Signal} of the level end {@link GameScreen} to the {@link #onLevelEndScreenContinued()}.
     */
    private void onPlayerExitedLevel() {
        GameScreen levelEndScreen = currentLevel.getLevelEndScreen();
        sceneService.addNodeToMiddleLayer(levelEndScreen);
        levelEndScreen.getContinuedSignal().connect(this::onLevelEndScreenContinued);
        levelEndScreen.start();
    }

    /**
     * Transitions the game to the next level or ends the game if the final level is completed.
     */
    private void onLevelEndScreenContinued() {
        GameScreen levelEndScreen = currentLevel.getLevelEndScreen();
        sceneService.removeNodeFromMiddleLayer(levelEndScreen);
        levelEndScreen.getContinuedSignal().clearConnections();

        boolean isFinalLevelWon = nextLevelIndex >= levelsOrdered.size();

        if (isFinalLevelWon)
            winGame();
        else {
            sceneReset.emit();
            goToNextLevel();
        }
    }

    /**
     * Starts the game by initializing the scene, player, UI, and background music.
     */
    private void startGame() {
        sceneService.clearMiddleLayer();

        player.addToScene();

        sceneService.addNodeToTopLayer(userInterface);
        audioService.startBackgroundMusic();

        nextLevelIndex = 0;

        goToNextLevel();
    }

    /**
     * Advances the game to the next level.
     */
    private void goToNextLevel() {
        currentLevel = levelsOrdered.get(nextLevelIndex++);

        userInterface.setVisible(true);
        player.playEnterTransition();
    }

    private void onLevelWon() {
        userInterface.setVisible(false);
        player.playExitTransition();
    }

    /**
     * Prepares and transitions the game to the {@link WinScreen}, then invokes {@link #stopGame()}.
     */
    private void winGame() {
        WinScreen winScreen = new WinScreen();
        winScreen.getContinuedSignal().connect(this::startGame);

        sceneService.clearMiddleLayer();
        sceneService.addNodeToMiddleLayer(winScreen);
        audioService.stopBackgroundMusic();
        audioService.playSound(AudioService.Sound.GAME_WON);
        winScreen.start();
        stopGame();
    }

    /**
     * Prepares and transitions the game to the {@link GameOverScreen}, then invokes {@link #stopGame()}.
     */
    private void loseGame() {
        GameOverScreen gameOverScreen = new GameOverScreen();
        gameOverScreen.getContinuedSignal().connect(this::startGame);

        sceneService.clearMiddleLayer();
        sceneService.addNodeToMiddleLayer(gameOverScreen);
        gameOverScreen.start();
        stopGame();
    }

    /**
     * Stops the current game session, resetting critical components.
     */
    private void stopGame() {
        sceneReset.emit();
        player.removeFromScene();
        sceneService.clearTopLayer();
    }
}
