package com.samsung.business.spaceinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.samsung.business.spaceinvaders.SpaceInvaders;
import com.samsung.business.spaceinvaders.ui.GameInputMethod;
import com.samsung.business.spaceinvaders.ui.InputManager;
import com.samsung.business.spaceinvaders.ui.KeyboardInput;

public class GameWinScreen implements Screen, GameInputMethod {
    private final SpaceInvaders spaceInvaders;
    private BitmapFont font;
    private OrthographicCamera camera;
    private InputManager inputManager;

    public GameWinScreen(SpaceInvaders spaceInvaders) {
        font = new BitmapFont();

        // tell the camera to update its matrices.
        camera = new OrthographicCamera();
        camera.setToOrtho(false, com.samsung.business.spaceinvaders.ui.Screen.getWidth(), com.samsung.business.spaceinvaders.ui.Screen.getHeight());
        this.spaceInvaders = spaceInvaders;

        //zaladuj menadzera sterowania
        switch(Gdx.app.getType()) {
            case Android:
                inputManager = new InputManager(this);
                break;
            case Desktop:
                inputManager = new InputManager(new KeyboardInput());

                break;
        }
        inputManager.setSelectListener(()->{
            spaceInvaders.restart();
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        spaceInvaders.batch.setProjectionMatrix(camera.combined);

        spaceInvaders.batch.begin();
        font.draw(spaceInvaders.batch, "GAME WON! " + spaceInvaders.getScore().getValue(), 10, com.samsung.business.spaceinvaders.ui.Screen.getHeight()/2);
        switch(Gdx.app.getType()) {
            case Android:
                font.draw(spaceInvaders.batch, "Touch screen to restart", 10, com.samsung.business.spaceinvaders.ui.Screen.getHeight()/2-50);
                break;
            case Desktop:
                font.draw(spaceInvaders.batch, "Press enter to restart", 10, com.samsung.business.spaceinvaders.ui.Screen.getHeight()/2-50);

                break;
        }
        spaceInvaders.batch.end();

        inputManager.update();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        font.dispose();
    }

    @Override
    public boolean left() {
        return false;
    }

    @Override
    public boolean right() {
        return false;
    }

    @Override
    public boolean fire() {
        return false;
    }

    @Override
    public boolean exit() {
        return false;
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
        return Gdx.input.isTouched();
    }
}
