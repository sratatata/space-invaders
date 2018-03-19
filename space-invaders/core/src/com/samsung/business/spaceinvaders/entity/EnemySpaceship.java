package com.samsung.business.spaceinvaders.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;
import com.samsung.business.spaceinvaders.manager.ShootManager;

/**
 * Reprezentuje postaci wrogow. Wrogowie najczesciej dokonuja grupowej Inwazji.
 *
 * Created by lb_lb on 01.11.17.
 */
public class EnemySpaceship extends Spaceship {

    private boolean canShoot;
    private long nextShotTime;
    private static final float MOVE_BARIER = 150f;
    private float delta = 0;
    private int direction = 1;


    public EnemySpaceship(GraphicsManager.Graphics graphics, Rectangle rectangle, boolean canShoot) {
        super(graphics);
        this.position = rectangle;
        this.canShoot = canShoot;
        this.delta = MOVE_BARIER/2;
        this.position.x+=MOVE_BARIER/2;
        prepareNextShot();
    }

    public void updateState(OrthographicCamera camera){
        horizontalMove();
        verticalMove();
    }

    private void verticalMove() {
        if(isBouncingOffRight() || isBouncingOffLeft()) {
            position.y -= 10;
        }
    }

    private boolean isBouncingOffLeft() {
        return delta <= (MOVE_BARIER)*-1;
    }

    private boolean isBouncingOffRight() {
        return delta >= MOVE_BARIER;
    }

    private void horizontalMove() {
        if(isBouncingOffRight()){
            switchDirection();
        }else if(isBouncingOffLeft()){
            switchDirection();
        }

        position.x += direction;
        delta += direction;
    }

    private void switchDirection() {
        direction = direction * -1;
    }

    public void shot(ShootManager shootManager) {
        if (this.canShoot && TimeUtils.nanoTime() > nextShotTime) {
            prepareNextShot();
            GraphicsManager.Graphics shotGraphics = shootManager.graphicsManager.find("obcyPocisk");
            shootManager.addShot(new EnemyShoot(shotGraphics, this.position.getX(), this.position.getY()));
        }
    }

    private void prepareNextShot() {
        this.nextShotTime = TimeUtils.nanoTime() + MathUtils.random(6000000000L);
    }

    public void prepareToShot() {
        canShoot = true;
    }

}
