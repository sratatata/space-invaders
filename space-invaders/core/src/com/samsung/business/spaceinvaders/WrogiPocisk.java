package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by lb_lb on 01.11.17.
 */
public class WrogiPocisk {
    Rectangle rectangle;

    public WrogiPocisk(float originX, float originY) {
        rectangle = new Rectangle();
        rectangle.x = originX;
        rectangle.y = originY;
        rectangle.height = 10;
        rectangle.width = 10;
    }
}
