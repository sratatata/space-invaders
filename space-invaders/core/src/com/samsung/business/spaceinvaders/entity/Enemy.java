package com.samsung.business.spaceinvaders.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.samsung.business.spaceinvaders.manager.ShootManager;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje postaci wrogow. Wrogowie najczesciej dokonuja grupowej Inwazji.
 *
 * Created by lb_lb on 01.11.17.
 */
public class Enemy implements Targetable {
    private GraphicsManager.Graphics graphics;
    private Rectangle position;
    private boolean canShoot;
    private long lastShotTime;
    private List<OnDestroyed> onDestroyed;
    private static final float MOVE_BARIER = 150f;
    private float delta = 0;
    private int direction = 1;


    public Enemy(GraphicsManager.Graphics graphics, Rectangle rectangle, boolean canShoot, long lastShotTime) {
        onDestroyed = new ArrayList<>();
        this.graphics = graphics;
        this.position = rectangle;
        this.canShoot = canShoot;
        this.lastShotTime = lastShotTime;
        this.delta = MOVE_BARIER/2;
        this.position.x+=MOVE_BARIER/2;
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

    public void render(SpriteBatch batch, float animationTime){
        TextureRegion spaceshipFrame = graphics.frameToRender(animationTime);
        batch.draw(spaceshipFrame, position.x, position.y);
    }

    public void shot(ShootManager shootManager) {
        if (this.canShoot &&
                TimeUtils.nanoTime() - this.lastShotTime >
                        MathUtils.random(5000000000L, 15000000000L)
                ) {
            this.lastShotTime = TimeUtils.nanoTime();

            GraphicsManager.Graphics shotGraphics = shootManager.graphicsManager.find("obcyPocisk");
            shootManager.addShot(new EnemyShoot(shotGraphics, this.position.getX(), this.position.getY()));
        }
    }

    public void registerOnDestroyed(OnDestroyed onDestroyed){
        this.onDestroyed.add(onDestroyed);
    }

    public void notifyAllOnDestroyed(){
        for(OnDestroyed g: onDestroyed){
            g.onDestroyed();
        }
    }

    @Override
    public Rectangle rectangle() {
        return this.position;
    }

    @Override
    public boolean checkHit(Rectangle target, Rectangle shot) {
        return target.overlaps(shot);
    }

    @Override
    public boolean isHit(Shoot shoot) {
        boolean isHit = shoot.hitIn(this);
        if(isHit){
            notifyAllOnDestroyed();
        }
        return isHit;
    }

    public void prepareToShot() {
        canShoot = true;
    }

    public interface OnDestroyed {
        void onDestroyed();
    }
}
