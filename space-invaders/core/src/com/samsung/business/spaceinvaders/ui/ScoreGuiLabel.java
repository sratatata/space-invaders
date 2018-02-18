package com.samsung.business.spaceinvaders.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lb_lb on 18.02.18.
 */

public class ScoreGuiLabel {
    private final static int MARGIN_TOP = 10;
    private final static int MARGIN_LEFT = 130;
    private int score;

    public ScoreGuiLabel() {
        this.score = 0;
    }

    public void setScore(int score ){

        this.score = score;
    }

    public void render(SpriteBatch batch){
        BitmapFont font = new BitmapFont();
        font.draw(batch, String.valueOf(score),  MARGIN_LEFT, Gdx.graphics.getHeight() - MARGIN_TOP);
    }

}
