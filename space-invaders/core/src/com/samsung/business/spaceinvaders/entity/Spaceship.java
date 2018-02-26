package com.samsung.business.spaceinvaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.samsung.business.spaceinvaders.ui.InputManager;
import com.samsung.business.spaceinvaders.manager.ShootManager;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;

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

    private InputManager inputManager;

    public Spaceship(GraphicsManager.Graphics graphics, InputManager inputManager) {
        this.playerHitListeners = new ArrayList<>();
        this.graphics = graphics;
        prepareSpaceship();
        this.inputManager = inputManager;
    }

    public void render(SpriteBatch batch, float animationTime) {
        TextureRegion spaceshipFrame = graphics.frameToRender(animationTime);
        batch.draw(spaceshipFrame, spaceshipRectangle.x, spaceshipRectangle.y);
    }

    private void prepareSpaceship() {
        spaceshipRectangle = new Rectangle();
        spaceshipRectangle.x = Gdx.graphics.getWidth() / 2 - 64 / 2; // center the bucket horizontally
        spaceshipRectangle.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
        spaceshipRectangle.width = 40;
        spaceshipRectangle.height = 26;
    }

    private void playerShot(ShootManager shootManager) {
        if (!canShoot()) {
            return;
        }

        shootManager.addShot(new PlayerShoot(shootManager.graphicsManager.find("pocisk"), spaceshipRectangle.getX(), spaceshipRectangle.getY()));
        lastShotTime = TimeUtils.nanoTime();
    }

    public void shoot(ShootManager shootManager){
        playerShot(shootManager);
    }

    private boolean canShoot() {
        return TimeUtils.nanoTime() - lastShotTime > 600 * 1000 * 1000;
    }

    public void update(OrthographicCamera camera, ShootManager shootManager) {
        // checkClick user input
        inputManager.setLeftListener(()->{
            spaceshipRectangle.x -= 200 * Gdx.graphics.getDeltaTime();

            // make sure the spaceship stays within the screen bounds
            if (spaceshipRectangle.x < 0) spaceshipRectangle.x = 0;
        });
        inputManager.setRightListener(() -> {
            spaceshipRectangle.x += 200 * Gdx.graphics.getDeltaTime();

            // make sure the spaceship stays within the screen bounds
            if (spaceshipRectangle.x > Gdx.graphics.getWidth() - 20) spaceshipRectangle.x = Gdx.graphics.getWidth() - 20;
        });
        inputManager.setFireListener(() -> {
            playerShot(shootManager);
        });

        inputManager.update();



    }

    @Override
    public boolean isHit(Shoot shoot) {
        return shoot.hitIn(this);
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
