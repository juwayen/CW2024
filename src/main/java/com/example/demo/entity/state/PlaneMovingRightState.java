package com.example.demo.entity.state;

import com.example.demo.entity.Entity;
import com.example.demo.entity.plane.Plane;

public class PlaneMovingRightState implements EntityState {
    @Override
    public void enter(Entity entity) {
        if (!(entity instanceof Plane plane))
            return;

        plane.getMovingRightTimeline().play();
    }

    @Override
    public void exit(Entity entity) {
        if (!(entity instanceof Plane plane))
            return;

        plane.getMovingRightTimeline().stop();
    }
}
