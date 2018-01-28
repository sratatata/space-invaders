package com.samsung.business.spaceinvaders.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Reperezentuje operacje pocisku
 *
 * Created by lb_lb on 05.11.17.
 */
public interface Shoot {
    void render(SpriteBatch batch, float animationTime);

    void updateState();

    boolean hitIn(Targetable targetableObject);

    boolean isOutsideScreen();
}
