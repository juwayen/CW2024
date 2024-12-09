package com.example.demo.factory;

import com.example.demo.Controller;
import com.example.demo.entity.plane.EnemyPlane;
import com.example.demo.util.Vector;

public abstract class EnemyPlaneFactory implements Factory {
    private final Controller controller;

    private double initialX;
    private Vector finalPosition;

    protected Controller getController() {
        return controller;
    }

    protected double getInitialX() {
        return initialX;
    }

    public void setInitialX(double initialX) {
        this.initialX = initialX;
    }

    protected Vector getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(Vector finalPosition) {
        this.finalPosition = finalPosition;
    }

    public EnemyPlaneFactory(Controller controller) {
        this.controller = controller;
    }

    @Override
    public abstract EnemyPlane create();
}
