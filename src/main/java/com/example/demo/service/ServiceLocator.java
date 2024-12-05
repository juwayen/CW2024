package com.example.demo.service;

public class ServiceLocator {
    private static GameLoopService gameLoopService;
    private static SceneService sceneService;
    private static InputService inputService;
    private static CollisionService collisionService;

    public static GameLoopService getGameLoopService() {
        return gameLoopService;
    }

    public static void setGameLoopService(GameLoopService gameLoopService) {
        ServiceLocator.gameLoopService = gameLoopService;
    }

    public static SceneService getSceneService() {
        return sceneService;
    }

    public static void setSceneService(SceneService sceneService) {
        ServiceLocator.sceneService = sceneService;
    }

    public static InputService getInputService() {
        return inputService;
    }

    public static void setInputService(InputService inputService) {
        ServiceLocator.inputService = inputService;
    }

    public static CollisionService getCollisionService() {
        return collisionService;
    }

    public static void setCollisionService(CollisionService collisionService) {
        ServiceLocator.collisionService = collisionService;
    }
}
