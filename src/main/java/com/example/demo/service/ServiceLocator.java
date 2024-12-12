package com.example.demo.service;

/**
 * Serves as a centralized registry for the services used in the game.
 */
public class ServiceLocator {
    private static GameService gameService;
    private static UpdateService updateService;
    private static SceneService sceneService;
    private static InputService inputService;
    private static CollisionService collisionService;
    private static AudioService audioService;

    /**
     * Getter method for the registered {@link GameService} instance.
     *
     * @return The currently registered {@link GameService} instance.
     */
    public static GameService getGameService() {
        return gameService;
    }

    /**
     * Setter method for the {@link GameService} instance.
     *
     * @param gameService The {@link GameService} instance to be registered.
     */
    public static void registerGameService(GameService gameService) {
        ServiceLocator.gameService = gameService;
    }

    /**
     * Getter method for the registered {@link UpdateService} instance.
     *
     * @return The currently registered {@link UpdateService} instance.
     */
    public static UpdateService getUpdateService() {
        return updateService;
    }

    /**
     * Setter method for the {@link UpdateService} instance.
     *
     * @param updateService The {@link UpdateService} instance to be registered.
     */
    public static void registerUpdateService(UpdateService updateService) {
        ServiceLocator.updateService = updateService;
    }

    /**
     * Getter method for the registered {@link SceneService} instance.
     *
     * @return The currently registered {@link SceneService} instance.
     */
    public static SceneService getSceneService() {
        return sceneService;
    }

    /**
     * Setter method for the {@link SceneService} instance.
     *
     * @param sceneService The {@link SceneService} instance to be registered.
     */
    public static void registerSceneService(SceneService sceneService) {
        ServiceLocator.sceneService = sceneService;
    }

    /**
     * Getter method for the registered {@link InputService} instance.
     *
     * @return The currently registered {@link InputService} instance.
     */
    public static InputService getInputService() {
        return inputService;
    }

    /**
     * Setter method for the {@link InputService} instance.
     *
     * @param inputService The {@link InputService} instance to be registered.
     */
    public static void registerInputService(InputService inputService) {
        ServiceLocator.inputService = inputService;
    }

    /**
     * Getter method for the registered {@link CollisionService} instance.
     *
     * @return The currently registered {@link CollisionService} instance.
     */
    public static CollisionService getCollisionService() {
        return collisionService;
    }

    /**
     * Setter method for the {@link CollisionService} instance.
     *
     * @param collisionService The {@link CollisionService} instance to be registered.
     */
    public static void registerCollisionService(CollisionService collisionService) {
        ServiceLocator.collisionService = collisionService;
    }

    /**
     * Getter method for the registered {@link AudioService} instance.
     *
     * @return The currently registered {@link AudioService} instance.
     */
    public static AudioService getAudioService() {
        return audioService;
    }

    /**
     * Setter method for the {@link AudioService} instance.
     *
     * @param audioService The {@link AudioService} instance to be registered.
     */
    public static void registerAudioService(AudioService audioService) {
        ServiceLocator.audioService = audioService;
    }
}
