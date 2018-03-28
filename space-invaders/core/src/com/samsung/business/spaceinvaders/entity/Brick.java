package com.samsung.business.spaceinvaders.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil on 2018-03-28.
 */

public class Brick {
    private final GraphicsManager.Graphics graphics;
    protected Rectangle position;
    private List<Brick.OnBrickHit> brickHitListeners = new ArrayList<>();
    protected int hitCount;

    public Brick(GraphicsManager.Graphics graphics, Rectangle rectangle, int hitCount) {
        this.graphics = graphics;
        this.position = rectangle;
        this.hitCount = hitCount;
    }

    public void updateState(OrthographicCamera camera){

    }

    public void render(SpriteBatch batch) {
        TextureRegion brickFrame;
        if (this.hitCount == 0) {
            brickFrame = graphics.frameToRender(0.000f);
        } else if (this.hitCount == 1) {
            brickFrame = graphics.frameToRender(0.025f);
        } else {
            brickFrame = graphics.frameToRender(0.050f);
        }
        batch.draw(brickFrame, position.x, position.y);
    }

    public boolean isHit(Shoot shoot) {
        boolean isHit = shoot.position().overlaps(this.position);
        if(isHit){
            notifyAllOnBrickHit();
            this.hitCount++;
        }
        return isHit;
    }

    public Rectangle position() {
        return this.position;
    }

    public void registerOnBrickHit(Brick.OnBrickHit onBrickHit){
        brickHitListeners.add(onBrickHit);
    }


    private void notifyAllOnBrickHit() {
        for (Brick.OnBrickHit g : brickHitListeners) {
            g.onBrickHit();
        }
    }

    public interface OnBrickHit {
        void onBrickHit();
    }
}
