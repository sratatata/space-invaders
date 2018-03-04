package com.samsung.business.spaceinvaders.ui;

import com.badlogic.gdx.Gdx;

/**
 * Created by lb_lb on 27.01.18.
 */
public class TouchInput implements GameInputMethod {
    final int MIDDLE_Y = Screen.getHeight()/2;
    final int MIDDLE_X = Screen.getWidth()/2;

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

    /**
     * When upper rectangle of screen is touched with two fingers
     * @return when touched true
     */
    @Override
    public boolean exit() {
        return Gdx.input.isTouched(0) && Gdx.input.isTouched(1)
                && Gdx.input.getY() < MIDDLE_Y;
    }

    @Override
    public boolean up() {
        return false;
    }

    @Override
    public boolean down() {
        return false;
    }

    @Override
    public boolean select() {
        return false;
    }
}
