package com.samsung.business.spaceinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.samsung.business.spaceinvaders.SpaceInvaders;
import com.samsung.business.spaceinvaders.ui.DisplayInfo;
import com.samsung.business.spaceinvaders.ui.TouchInput;

public class MainMenuScreen extends AbstractScreen {
    private final SpaceInvaders spaceInvaders;
    private BitmapFont font;

    private OrthographicCamera camera;
    private TouchInput touchInput;

    private int height = DisplayInfo.getHeight();
	private int width = DisplayInfo.getWidth();

    public MainMenuScreen(SpaceInvaders spaceInvaders) {
        font = new BitmapFont();
        this.spaceInvaders = spaceInvaders;

        // tell the camera to update its matrices.
        camera = new OrthographicCamera();
        camera.setToOrtho(false, DisplayInfo.getWidth(), DisplayInfo.getHeight());
        touchInput = new TouchInput(camera);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        spaceInvaders.batch.setProjectionMatrix(camera.combined);
        spaceInvaders.batch.begin();
        font.draw(spaceInvaders.batch, "Space Invaders", 10, height/2);
        font.draw(spaceInvaders.batch, "Touch screen to start", 10, height/2-50);

        spaceInvaders.batch.end();
        if (touchInput.start()){
            spaceInvaders.start();
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
