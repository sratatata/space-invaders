package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.business.spaceinvaders.ui.Score;

public class SpaceInvaders extends Game {
    public SpriteBatch batch;
    private Score score;

    public void create() {
        batch = new SpriteBatch();
//        this.setScreen(new MainMenuScreen(this));
        this.setScreen(new GameScreen(this));
        score = new Score(0);

    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
    }

    public Score getScore() {
        return score;
    }
}
