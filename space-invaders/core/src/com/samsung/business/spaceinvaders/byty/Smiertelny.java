package com.samsung.business.spaceinvaders.byty;

import com.badlogic.gdx.math.Rectangle;

/**
 * Interfejs oznaczajacy obiekty, ktore moga zostac trafione/zniszczone.
 *
 * Created by lb_lb on 05.11.17.
 */
public interface Smiertelny {
    Rectangle namiary();
    boolean trafienie(Rectangle cel, Rectangle pocisk);
    boolean trafiony(Pocisk pocisk);
}
