package com.samsung.business.spaceinvaders.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.business.spaceinvaders.entity.EnemyShot;
import com.samsung.business.spaceinvaders.entity.Invasion;
import com.samsung.business.spaceinvaders.entity.Shot;
import com.samsung.business.spaceinvaders.entity.Spaceship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Czlowiek strzela a Bog kule nosi.
 *
 * Obiekt zarzadza strzalami wrogow i gracza.
 *
 * Created by lb_lb on 05.11.17.
 */
public class ShotManager {
    private List<Shot> shots;
    public final GraphicsManager graphicsManager;
    public final Spaceship player;
    public final Invasion invasion;

    public ShotManager(GraphicsManager graphicsManager, Spaceship player, Invasion invasion) {
        this.graphicsManager = graphicsManager;
        this.player = player;
        this.invasion = invasion;
        this.shots = new ArrayList<>();
    }

    public ShotManager addShot(Shot shot) {
        shots.add(shot);
        return this;
    }

    public void updateShots() {
        Iterator<Shot> iter = shots.iterator();
        while (iter.hasNext()) {
            Shot shot = iter.next();
            shot.updateState();
            if (shot.isOutsideScreen()) {
                iter.remove();
            }
            if (shot instanceof EnemyShot) {
                player.isHit(shot);
            } else {
                invasion.checkEnemyHit(iter, shot);
            }
        }

    }

    public void render(SpriteBatch batch, float animationTime) {
        for (Shot p : shots) {
            p.render(batch, animationTime);
        }
    }

}
