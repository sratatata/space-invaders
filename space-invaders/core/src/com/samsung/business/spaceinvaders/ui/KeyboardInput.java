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
        //todo quit the game;
        return false;
    }
}
