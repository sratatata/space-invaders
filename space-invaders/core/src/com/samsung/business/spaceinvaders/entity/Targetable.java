package com.samsung.business.spaceinvaders.entity;

import com.badlogic.gdx.math.Rectangle;

/**
 * Interfejs oznaczajacy obiekty, ktore moga zostac trafione/zniszczone.
 *
 * Created by lb_lb on 05.11.17.
 */
public interface Targetable {
    Rectangle rectangle();
    boolean checkHit(Rectangle cel, Rectangle shot);
    boolean isHit(Shoot shoot);
}
