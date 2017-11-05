package com.samsung.business.spaceinvaders.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lb_lb on 05.11.17.
 */
public class Score {
    private int score;

    public Score(int score) {
        this.score = score;
    }

    public void render(SpriteBatch batch){
        BitmapFont font = new BitmapFont();
        font.draw(batch, String.valueOf(score), 10, 470);
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }
}
