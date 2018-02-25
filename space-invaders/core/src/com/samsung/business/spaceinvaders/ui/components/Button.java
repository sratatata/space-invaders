package com.samsung.business.spaceinvaders.ui.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lb_lb on 25.02.18.
 */

public class Button implements Component {
    private int x = 0 , y = 0;

    @Override
    public void render(SpriteBatch batch) {

    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
}
