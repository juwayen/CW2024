package com.example.demo.entity.plane;

import com.example.demo.entity.bullet.Bullet;
import com.example.demo.entity.bullet.BulletConfig;
import com.example.demo.service.ServiceLocator;
import com.example.demo.util.Vector;

import static com.example.demo.service.UpdateService.MILLISECOND_DELAY;

public class EnemyPlane extends Plane {
    private final Vector finalPosition;
    private final Vector direction;
    private final BulletConfig bulletConfig;
    private final double minMillisecondsPerFire;
    private final double maxMillisecondsPerFire;
    private final double speed;
    private final PlayerPlane player;

    private boolean hasReachedFinalPosition;
    private double millisecondsBeforeNextShot;

    public EnemyPlane(PlaneData planeData) {
        super(planeData);

        this.finalPosition = planeData.getFinalPosition();
        this.direction = planeData.getInitialPosition().directionTo(finalPosition);
        this.bulletConfig = planeData.getBulletConfig();
        this.minMillisecondsPerFire = planeData.getMinMillisecondsPerFire();
        this.maxMillisecondsPerFire = planeData.getMaxMillisecondsPerFire();
        this.speed = planeData.getSpeed();
        this.player = ServiceLocator.getGameService().getPlayer();

        this.hasReachedFinalPosition = false;
        this.millisecondsBeforeNextShot = getRandomTime();

        initializeBulletConfig();
    }

    private void initializeBulletConfig() {
        bulletConfig.setShooter(this);
    }

    private double getRandomTime() {
        return Math.random() * (maxMillisecondsPerFire - minMillisecondsPerFire + 1) + minMillisecondsPerFire;
    }

    @Override
    public void updatePosition() {
        if (hasReachedFinalPosition)
            return;

        move(direction, speed);

        boolean hasReachedFinalPositionY = finalPosition.subtract(getPosition()).getY() <= 0;

        if (hasReachedFinalPositionY) {
            setPosition(finalPosition);
            hasReachedFinalPosition = true;
        }
    }

    @Override
    public boolean canFire() {
        millisecondsBeforeNextShot -= MILLISECOND_DELAY;

        return millisecondsBeforeNextShot <= 0;
    }

    @Override
    public void fire() {
        Vector shootingPosition = getPosition().add(bulletConfig.getOffset());
        Vector directionToPlayer = shootingPosition.directionTo(player.getCenterPosition());
        bulletConfig.setDirection(directionToPlayer);
        Bullet bullet = new Bullet(bulletConfig);

        bullet.addToScene();

        millisecondsBeforeNextShot = getRandomTime();
    }

    @Override
    public boolean isFriendly() {
        return false;
    }
}
