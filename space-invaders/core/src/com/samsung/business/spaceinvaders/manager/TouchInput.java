package com.samsung.business.spaceinvaders.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by lb_lb on 27.01.18.
 */
public class TouchInput implements GameInputMethod {
    final int MIDDLE_Y = Gdx.graphics.getHeight()/2;
    final int MIDDLE_X = Gdx.graphics.getWidth()/2;

    /**
     * When left down rectangle of screen is touched
     * @return when touched true
     */
    @Override
    public boolean left() {
        return Gdx.input.isTouched()
                && Gdx.input.getX() <= MIDDLE_X
                && Gdx.input.getY() > MIDDLE_Y;
    }

    /**
     * When right down rectangle of screen is touched
     * @return when touched true
     */
    @Override
    public boolean right() {
        return Gdx.input.isTouched()
                && Gdx.input.getX() > MIDDLE_X
                && Gdx.input.getY() > MIDDLE_Y;
    }

    /**
     * When upper rectangle of screen is touched
     * @return when touched true
     */
    @Override
    public boolean fire() {
        return Gdx.input.isTouched()
                && Gdx.input.getY() < MIDDLE_Y;
    }

    @Override
    public boolean exit() {
        //todo add exit button
        return false;
    }
}
