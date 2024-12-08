package com.example.demo.entity.state;

import com.example.demo.entity.Entity;
import com.example.demo.entity.bullet.Bullet;

public class BulletMovingState implements EntityState {
    @Override
    public void enter(Entity entity) {
        if (!(entity instanceof Bullet bullet))
            return;

        bullet.getMovingTimeline().play();
    }

    @Override
    public void exit(Entity entity) {
        if (!(entity instanceof Bullet bullet))
            return;

        bullet.getMovingTimeline().stop();
    }
}
