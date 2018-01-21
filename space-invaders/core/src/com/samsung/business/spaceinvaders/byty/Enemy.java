package com.samsung.business.spaceinvaders.byty;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.samsung.business.spaceinvaders.zarzadzanie.ShotManager;
import com.samsung.business.spaceinvaders.zarzadzanie.GraphicsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje postaci wrogow. Wrogowie najczesciej dokonuja grupowej Inwazji.
 *
 * Created by lb_lb on 01.11.17.
 */
public class Enemy implements Targetable {
    private GraphicsManager.Graphics graphics;
    private Rectangle rectangle;
    private boolean canShoot;
    private long lastShotTime;
    private List<OnDestroyed> onDestroyed;


    public Enemy(GraphicsManager.Graphics graphics, Rectangle rectangle, boolean canShoot, long lastShotTime) {
        onDestroyed = new ArrayList<>();
        this.graphics = graphics;
        this.rectangle = rectangle;
        this.canShoot = canShoot;
        this.lastShotTime = lastShotTime;
    }

    public void updateState(OrthographicCamera camera){

    }

    public void render(SpriteBatch batch, float animationTime){
        TextureRegion spaceshipFrame = graphics.frameToRender(animationTime);
        batch.draw(spaceshipFrame, rectangle.x, rectangle.y);
    }

    public ShotManager shot(ShotManager shotManager) {
        if (this.canShoot &&
                TimeUtils.nanoTime() - this.lastShotTime >
                        MathUtils.random(5000000000L, 15000000000L)
                ) {
            this.lastShotTime = TimeUtils.nanoTime();

            GraphicsManager.Graphics shotGraphics = shotManager.graphicsManager.find("obcyPocisk");
            shotManager.addShot( (Shot) new EnemyShot(shotGraphics, this.rectangle.getX(), this.rectangle.getY()));

        }

        return shotManager;
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
        return this.rectangle;
    }

    @Override
    public boolean checkHit(Rectangle target, Rectangle shot) {
        return target.overlaps(shot);
    }

    @Override
    public boolean isHit(Shot shot) {
        boolean isHit = shot.hitIn(this);
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
