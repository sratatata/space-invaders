package com.samsung.business.spaceinvaders.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by lb_lb on 27.01.18.
 */
public class KeyboardInput implements GameInputMethod{
    @Override
    public boolean left(){
        return Gdx.input.isKeyPressed(Input.Keys.LEFT);
    }

    @Override
    public boolean right(){
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }

    @Override
    public boolean fire(){
        return Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }

    @Override
    public boolean exit(){
        return Gdx.input.isKeyPressed(Input.Keys.ESCAPE);
    }

    @Override
    public boolean up() {
        return Gdx.input.isKeyPressed(Input.Keys.UP);
    }

    @Override
    public boolean down() {
        return Gdx.input.isKeyPressed(Input.Keys.DOWN);
    }

    @Override
    public boolean select() {
        return Gdx.input.isKeyPressed(Input.Keys.ENTER);
    }
}
