package com.samsung.business.spaceinvaders.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lb_lb on 05.11.17.
 *
 * ScoreManager sluzy do przechowywania punktacji
 */
public class ScoreManager {

    private int score;

    public ScoreManager(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getValue() {
        return score;
    }

    public void reset() {
        score = 0;
    }
}
