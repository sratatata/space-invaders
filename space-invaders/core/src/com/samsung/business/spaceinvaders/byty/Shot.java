package com.samsung.business.spaceinvaders.byty;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Reperezentuje operacje pocisku
 *
 * Created by lb_lb on 05.11.17.
 */
public interface Shot {
    void render(SpriteBatch batch, float animationTime);

    void updateState();

    boolean hitIn(Targetable targetableObject);
}
