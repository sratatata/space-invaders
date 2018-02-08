package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameOverScreen implements Screen {
    private final SpaceInvaders spaceInvaders;
    private BitmapFont font;

    public GameOverScreen(SpaceInvaders spaceInvaders) {
        font = new BitmapFont();
        this.spaceInvaders = spaceInvaders;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        font.draw(spaceInvaders.batch, "GAME OVER " + spaceInvaders.getScore().getValue(), 10, 230);
    }

    @Override
    public void resize(int width, int height) {

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
}
