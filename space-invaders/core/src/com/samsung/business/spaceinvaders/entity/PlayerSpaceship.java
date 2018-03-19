package com.samsung.business.spaceinvaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;
import com.samsung.business.spaceinvaders.manager.ShootManager;
import com.samsung.business.spaceinvaders.ui.DisplayInfo;
import com.samsung.business.spaceinvaders.ui.TouchInput;

/**
 * Reprezentuje pojazd gracza.
 * <p/>
 * Created by lb_lb on 01.11.17.
 */
public class PlayerSpaceship extends Spaceship {

    private long lastShotTime;
    private TouchInput touchInput;

    public PlayerSpaceship(GraphicsManager.Graphics graphics, Camera camera) {
        super(graphics);
        touchInput = new TouchInput(camera);
        prepareSpaceship();
    }

    private void prepareSpaceship() {
        position = new Rectangle();
        position.x = DisplayInfo.getWidth() / 2 - 64 / 2; // center the bucket horizontally
        position.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
        position.width = 40;
        position.height = 26;
    }

    private void shot(ShootManager shootManager) {
        if (!canShoot()) {
            return;
        }

        shootManager.addShot(new PlayerShoot(shootManager.graphicsManager.find("pocisk"), position.getX(), position.getY()));
        lastShotTime = TimeUtils.nanoTime();
    }

    private boolean canShoot() {
        return TimeUtils.nanoTime() - lastShotTime > 600 * 1000 * 1000;
    }

    public void update(OrthographicCamera camera, ShootManager shootManager) {
        // checkClick user input
        if (touchInput.left()){
            position.x -= 200 * Gdx.graphics.getDeltaTime();

            // make sure the spaceship stays within the screen bounds
            if (position.x < 0) position.x = 0;
        }
        if (touchInput.right()) {
            position.x += 200 * Gdx.graphics.getDeltaTime();

            // make sure the spaceship stays within the screen bounds
            if (position.x > DisplayInfo.getWidth() - 20) position.x = DisplayInfo.getWidth() - 20;
        }
        if (touchInput.fire()) {
            shot(shootManager);
        }
    }
}
