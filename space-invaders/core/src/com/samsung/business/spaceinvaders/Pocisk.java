package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Reperezentuje operacje pocisku
 *
 * Created by lb_lb on 05.11.17.
 */
public interface Pocisk {
    void render(SpriteBatch batch, float czasAnimacji);

    void updateState();
}
