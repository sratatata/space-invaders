package com.samsung.business.spaceinvaders.byty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.samsung.business.spaceinvaders.zarzadzanie.ShotManager;
import com.samsung.business.spaceinvaders.zarzadzanie.GraphicsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje pojazd gracza.
 * <p/>
 * Created by lb_lb on 01.11.17.
 */
public class Spaceship implements Targetable {
    private final GraphicsManager.Graphics graphics;

    private Rectangle spaceshipRectangle;
    private long lastShotTime;
    private List<OnPlayerHit> playerHitListeners;

    public Spaceship(GraphicsManager.Graphics graphics) {
        this.playerHitListeners = new ArrayList<>();
        this.graphics = graphics;
        prepareSpaceship();
    }

    public void render(SpriteBatch batch, float animationTime) {
        TextureRegion spaceshipFrame = graphics.frameToRender(animationTime);
        batch.draw(spaceshipFrame, spaceshipRectangle.x, spaceshipRectangle.y);
    }

    private void prepareSpaceship() {
        spaceshipRectangle = new Rectangle();
        spaceshipRectangle.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        spaceshipRectangle.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
        spaceshipRectangle.width = 40;
        spaceshipRectangle.height = 40;
    }

    private void playerShot(ShotManager shotManager) {
        if (!canShoot()) {
            return;
        }

        shotManager.addShot(new PlayerShot(shotManager.graphicsManager.find("pocisk"), spaceshipRectangle.getX(), spaceshipRectangle.getY()));
        lastShotTime = TimeUtils.nanoTime();
    }

    private boolean canShoot() {
        return TimeUtils.nanoTime() - lastShotTime > 600 * 1000 * 1000;
    }

    public void update(OrthographicCamera camera, ShotManager shotManager) {
        // process user input
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getY() > 360) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                spaceshipRectangle.x = touchPos.x - 40 / 2;
            } else {
                playerShot(shotManager);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            spaceshipRectangle.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            spaceshipRectangle.x += 200 * Gdx.graphics.getDeltaTime();


        // make sure the bucket stays within the screen bounds
        if (spaceshipRectangle.x < 0) spaceshipRectangle.x = 0;
        if (spaceshipRectangle.x > 800 - 20) spaceshipRectangle.x = 800 - 20;
    }

    @Override
    public boolean isHit(Shot shot) {
        return shot.hitIn(this);
    }


    @Override
    public Rectangle rectangle() {
        return this.spaceshipRectangle;
    }

    @Override
    public boolean checkHit(Rectangle target, Rectangle shot) {
        if (shot.overlaps((target))) {
            notifyAllOnPlayerHit();
            return true;
        } else
            return false;
    }

    public void listenOnPlayerHit(OnPlayerHit onPlayerHit){
        playerHitListeners.add(onPlayerHit);
    }

    private void notifyAllOnPlayerHit() {
        for (OnPlayerHit g : playerHitListeners) {
            g.onPlayerHit();
        }
    }

    public interface OnPlayerHit {
        void onPlayerHit();
    }

}
