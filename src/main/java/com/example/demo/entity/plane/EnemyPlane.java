package com.example.demo.entity.plane;

import com.example.demo.entity.bullet.Bullet;
import com.example.demo.entity.bullet.BulletConfig;
import com.example.demo.service.ServiceLocator;
import com.example.demo.util.Vector;

import static com.example.demo.service.UpdateService.MILLISECOND_DELAY;

/**
 * Represents an enemy plane in the game.
 */
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

    /**
     * Constructs an instance of {@link EnemyPlane} using the provided {@link PlaneData}.
     *
     * @param planeData The {@link PlaneData} object containing the properties required for creating the {@link EnemyPlane}.
     */
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

    /**
     * Generates a random time interval within a certain range.
     *
     * @return A random {@code double} value representing the time interval in milliseconds.
     */
    private double getRandomTime() {
        return Math.random() * (maxMillisecondsPerFire - minMillisecondsPerFire + 1) + minMillisecondsPerFire;
    }

    /**
     * Initializes the {@link BulletConfig} for the {@link EnemyPlane}.
     */
    private void initializeBulletConfig() {
        bulletConfig.setShooter(this);
    }

    /**
     * Updates the position of the {@link EnemyPlane} towards its final destination.
     */
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

    /**
     * Determines whether the {@link EnemyPlane} is allowed to fire based on the elapsed time since the last shot.
     */
    @Override
    public boolean canFire() {
        millisecondsBeforeNextShot -= MILLISECOND_DELAY;

        return millisecondsBeforeNextShot <= 0;
    }

    /**
     * Fires a {@link Bullet} from the current {@link EnemyPlane} towards the {@link PlayerPlane}.
     */
    @Override
    public void fire() {
        Vector shootingPosition = getPosition().add(bulletConfig.getOffset());
        Vector directionToPlayer = shootingPosition.directionTo(player.getCenterPosition());
        bulletConfig.setDirection(directionToPlayer);
        Bullet bullet = new Bullet(bulletConfig);

        bullet.addToScene();

        millisecondsBeforeNextShot = getRandomTime();
    }

    /**
     * Marks {@link EnemyPlane} instances as not friendly.
     *
     * @return {@code false}.
     */
    @Override
    public boolean isFriendly() {
        return false;
    }
}
