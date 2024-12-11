package com.example.demo.service;

public class ServiceLocator {
    private static GameService gameService;
    private static UpdateService updateService;
    private static SceneService sceneService;
    private static InputService inputService;
    private static CollisionService collisionService;
    private static AudioService audioService;

    public static GameService getGameService() {
        return gameService;
    }

    public static void registerGameService(GameService gameService) {
        ServiceLocator.gameService = gameService;
    }

    public static UpdateService getUpdateService() {
        return updateService;
    }

    public static void registerUpdateService(UpdateService updateService) {
        ServiceLocator.updateService = updateService;
    }

    public static SceneService getSceneService() {
        return sceneService;
    }

    public static void registerSceneService(SceneService sceneService) {
        ServiceLocator.sceneService = sceneService;
    }

    public static InputService getInputService() {
        return inputService;
    }

    public static void registerInputService(InputService inputService) {
        ServiceLocator.inputService = inputService;
    }

    public static CollisionService getCollisionService() {
        return collisionService;
    }

    public static void registerCollisionService(CollisionService collisionService) {
        ServiceLocator.collisionService = collisionService;
    }

    public static AudioService getAudioService() {
        return audioService;
    }

    public static void registerAudioService(AudioService audioService) {
        ServiceLocator.audioService = audioService;
    }
}
