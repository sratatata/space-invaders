package com.samsung.business.spaceinvaders.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.business.spaceinvaders.entity.EnemyShoot;
import com.samsung.business.spaceinvaders.entity.Invasion;
import com.samsung.business.spaceinvaders.entity.PlayerShoot;
import com.samsung.business.spaceinvaders.entity.Shoot;
import com.samsung.business.spaceinvaders.entity.PlayerSpaceship;

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
public class ShootManager {
    private List<Shoot> shoots;
    public final GraphicsManager graphicsManager;
    public final PlayerSpaceship player;
    public final Invasion invasion;
    private OnMissingShootListener onMissingShootListener;

    public ShootManager(GraphicsManager graphicsManager, PlayerSpaceship player, Invasion invasion) {
        this.graphicsManager = graphicsManager;
        this.player = player;
        this.invasion = invasion;
        this.shoots = new ArrayList<>();
    }

    public ShootManager addShot(Shoot shoot) {
        shoots.add(shoot);
        return this;
    }

    public void updateShots() {
        Iterator<Shoot> iter = shoots.iterator();
        while (iter.hasNext()) {
            Shoot shoot = iter.next();
            shoot.updateState();
            if (shoot.isOutsideScreen()) {
                iter.remove();

                notifyMissingShootListenerForPlayerShoots(shoot);
            } else {
                if (shoot instanceof EnemyShoot) {
                    player.isHit(shoot);
                } else {
                    invasion.checkEnemyHit(iter, shoot);
                }
            }
        }

    }

    private void notifyMissingShootListenerForPlayerShoots(Shoot shoot) {
        if(shoot instanceof PlayerShoot){
            onMissingShootListener.onMissingShoot();
        }
    }

    public void render(SpriteBatch batch, float animationTime) {
        for (Shoot p : shoots) {
            p.render(batch, animationTime);
        }
    }

    public void onMissed(OnMissingShootListener onMissingShootListener) {
        this.onMissingShootListener = onMissingShootListener;
    }

    public interface OnMissingShootListener{
        void onMissingShoot();
    }
}
