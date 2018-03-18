package com.samsung.business.spaceinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.samsung.business.spaceinvaders.SpaceInvaders;
import com.samsung.business.spaceinvaders.ui.DisplayInfo;
import com.samsung.business.spaceinvaders.ui.TouchInput;

public class GameWinScreen extends AbstractScreen {
    private final SpaceInvaders spaceInvaders;
    private BitmapFont font;
    private OrthographicCamera camera;
    private TouchInput touchInput;

    public GameWinScreen(SpaceInvaders spaceInvaders) {
        font = new BitmapFont();

        // tell the camera to update its matrices.
        camera = new OrthographicCamera();
        camera.setToOrtho(false, DisplayInfo.getWidth(), DisplayInfo.getHeight());
        touchInput = new TouchInput(camera);
        this.spaceInvaders = spaceInvaders;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        spaceInvaders.batch.setProjectionMatrix(camera.combined);

        spaceInvaders.batch.begin();
        font.draw(spaceInvaders.batch, "GAME WON! " + spaceInvaders.getScore().getValue(), 10, DisplayInfo.getHeight()/2);
        font.draw(spaceInvaders.batch, "Touch screen to restart", 10, DisplayInfo.getHeight()/2-50);

        spaceInvaders.batch.end();
        if (touchInput.start()){
            spaceInvaders.restart();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
