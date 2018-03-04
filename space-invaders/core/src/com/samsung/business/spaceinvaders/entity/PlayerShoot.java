package com.samsung.business.spaceinvaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;
import com.samsung.business.spaceinvaders.ui.Screen;

/**
 * Created by lb_lb on 05.11.17.
 */
public class PlayerShoot implements Shoot {
    private static final int HEIGHT = Screen.getHeight();

    private Rectangle rectangle;
    private GraphicsManager.Graphics graphics;

    public PlayerShoot(GraphicsManager.Graphics graphics, float originX, float originY) {
        this.graphics = graphics;
        rectangle = new Rectangle();
        rectangle.x = originX;
        rectangle.y = originY;
        rectangle.height = 10;
        rectangle.width = 10;
    }

    @Override
    public void render(SpriteBatch batch, float animationTime) {
        TextureRegion shotFrame = graphics.frameToRender(animationTime);
        batch.draw(shotFrame, rectangle.x, rectangle.y);
    }

    @Override
    public void updateState() {
        this.rectangle.y += 200 * Gdx.graphics.getDeltaTime();
    }

    @Override
    public boolean hitIn(Targetable targetableObject) {
        return targetableObject.checkHit(this.rectangle, targetableObject.rectangle());
    }

    @Override
    public boolean isOutsideScreen() {
        return this.rectangle.y - 10 > HEIGHT;
    }
}
