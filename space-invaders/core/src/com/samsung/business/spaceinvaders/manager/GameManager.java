package com.samsung.business.spaceinvaders.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.business.spaceinvaders.ui.Score;

/**
 * Created by lb_lb on 13.10.17.
 */
public class GameManager {
    private ObserverOnGameOver observerOnGameOver;
    private ObserverOnWin observerOnWin;

    private GameOnGoingShitName gameOnGoingShitName;

    private boolean gameOver = false;
    private boolean win = false;
    private Score score;

    public GameManager() {
    }

    public void render(SpriteBatch batch) {
        if (gameOver) {
            observerOnGameOver.onGameOver(batch, score);
        } else if (win) {
            observerOnWin.onGameFinished(batch, score);
            gameOnGoingShitName.frame(batch);
        } else {
            gameOnGoingShitName.frame(batch);
        }
    }

    public void gameOver() {
        this.gameOver = true;
    }

    public void setObserverOnGameOver(ObserverOnGameOver observerOnGameOver) {
        this.observerOnGameOver = observerOnGameOver;
    }

    public void setObserverOnWin(ObserverOnWin observerOnWin) {
        this.observerOnWin = observerOnWin;
    }

    public void setGameOngoing(GameOnGoingShitName gameOnGoingShitName) {
        this.gameOnGoingShitName = gameOnGoingShitName;
    }

    public void win() {
        this.win = true;
    }

    public void setScore(Score score) {
        this.score = score;
    }


    public interface ObserverOnGameOver {
        //todo nazwa
        void onGameOver(SpriteBatch batch, Score score);
    }

    public interface ObserverOnWin {
        //todo nazwa
        void onGameFinished(SpriteBatch batch, Score score);
    }

    public interface GameOnGoingShitName {
        void frame(SpriteBatch batch);
    }
}
