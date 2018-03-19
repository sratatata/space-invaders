package com.samsung.business.spaceinvaders.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;

/**
 * Reperezentuje operacje pocisku
 *
 * Created by lb_lb on 05.11.17.
 */
public abstract class Shoot {
    protected Rectangle position;
    protected GraphicsManager.Graphics graphics;

    public Shoot(GraphicsManager.Graphics graphics, float originX, float originY) {
        this.graphics = graphics;
        position = new Rectangle();
        position.x = originX;
        position.y = originY;
        position.height = 10;
        position.width = 10;
    }


    public abstract boolean isOutsideScreen();

    public abstract void updateState();

    public void render(SpriteBatch batch, float animationTime) {
        TextureRegion shotFrame = graphics.frameToRender(animationTime);
        batch.draw(shotFrame, position.x, position.y);
    }

    public Rectangle position(){
        return position;
    }
}
