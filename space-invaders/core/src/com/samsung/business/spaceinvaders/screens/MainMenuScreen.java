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

public class MainMenuScreen implements Screen , GameInputMethod{
    private final SpaceInvaders spaceInvaders;
    private BitmapFont font;
    private InputManager inputManager;

    private OrthographicCamera camera;

    private int height = Gdx.graphics.getHeight();
    private int width = Gdx.graphics.getWidth();

    public MainMenuScreen(SpaceInvaders spaceInvaders) {
        font = new BitmapFont();
        this.spaceInvaders = spaceInvaders;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


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
            spaceInvaders.start();
            dispose();
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        spaceInvaders.batch.setProjectionMatrix(camera.combined);
        spaceInvaders.batch.begin();
        font.draw(spaceInvaders.batch, "Space Invaders", 10, height/2);
        switch(Gdx.app.getType()) {
            case Android:
                font.draw(spaceInvaders.batch, "Touch screen to start", 10, height/2-50);
                break;
            case Desktop:
                font.draw(spaceInvaders.batch, "Press enter to start", 10, height/2-50);

                break;
        }
        spaceInvaders.batch.end();


        inputManager.update();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        camera.setToOrtho(false, width, height);
        camera.update();
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
