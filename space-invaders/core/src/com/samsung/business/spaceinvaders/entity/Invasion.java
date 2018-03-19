package com.samsung.business.spaceinvaders.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;
import com.samsung.business.spaceinvaders.manager.ShootManager;
import com.samsung.business.spaceinvaders.ui.DisplayInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * NadzorcaGry sprawuje piecze nad warunkami zakonczenia gry.
 *
 * Created by lb_lb on 04.11.17.
 */
public class Invasion {
    private static final int HEIGHT = DisplayInfo.getHeight();
    private static final int WIDTH = DisplayInfo.getWidth();
    private static final int PADDING_TOP = 10;
    private static final int ENEMY_ROWS_COUNT = 3;
    private static final int ENEMY_IN_ROW_COUNT = 10;
    private static final int ENEMY_HEIGHT = 40;
    private static final int ENEMY_WIDTH = 40;
    private static final int HORIZONTAL_PADDING_ENEMIES = 12;
    private static final int VERTICAL_PADDING_ENEMIES = 8;

    private List<EnemySpaceship> enemies;
    private List<OnEnemyDestroyed> onEnemyDestroyed;
    private List<OnInvasionDestroyed> onInvasionDestroyed;

    private Invasion() {
        onEnemyDestroyed = new ArrayList<>();
        onInvasionDestroyed = new ArrayList<>();
    }

    public static Invasion raid(GraphicsManager graphicsManager) {
        Invasion invasion = new Invasion();
        invasion.enemies = prepareEnemies(invasion, graphicsManager);
        return invasion;
    }

    private static List<EnemySpaceship> prepareEnemies(Invasion invasion, GraphicsManager graphicsManager) {
        List<EnemySpaceship> enemies = new ArrayList<EnemySpaceship>();
        int horizontalPadding = (WIDTH - ENEMY_IN_ROW_COUNT * (ENEMY_WIDTH + HORIZONTAL_PADDING_ENEMIES)) / 2;

        for (int y = 0; y < ENEMY_ROWS_COUNT; y++) {
            for (int x = 0; x < ENEMY_IN_ROW_COUNT; x++) {
                Rectangle enemyRect = new Rectangle();
                enemyRect.x = horizontalPadding + x * (ENEMY_WIDTH + HORIZONTAL_PADDING_ENEMIES);
                enemyRect.y = HEIGHT - ( PADDING_TOP )- ENEMY_HEIGHT - y * (ENEMY_HEIGHT + VERTICAL_PADDING_ENEMIES);
                enemyRect.height = ENEMY_HEIGHT;
                enemyRect.width = ENEMY_WIDTH;

                boolean canShoot = y == ENEMY_ROWS_COUNT - 1;

                EnemySpaceship enemy = new EnemySpaceship(graphicsManager.find("wrog"), enemyRect, canShoot);
                enemy.registerOnSpaceshipHit(new Spaceship.OnSpaceshipHit() {
                    @Override
                    public void onSpaceshipHit() {
                        invasion.notifyAllOnEnemyDestroyed(enemy);
                    }
                });
                enemies.add(enemy);

            }
        }

        return enemies;
    }

    public void render(SpriteBatch batch, float animationTime) {
        for (EnemySpaceship enemy : enemies) {
            enemy.render(batch, animationTime);
        }
    }

    public void update(OrthographicCamera camera, ShootManager shootManager) {
        for (EnemySpaceship enemy : enemies) {
            enemy.updateState(camera);
            enemy.shot(shootManager);
        }

        if(enemies.isEmpty()){
            notifyAllOnInvasionDestroyed();
        }
    }

    public boolean isAllDestroyed() {
        return enemies.isEmpty();
    }

    public void checkEnemyHit(Iterator<Shoot> iter, Shoot playerShoot) {
        Iterator<EnemySpaceship> iterEnemy = enemies.iterator();
        Spaceship enemyHit = null;
        while (iterEnemy.hasNext()) {
            EnemySpaceship enemy = iterEnemy.next();
            if (enemy.isHit(playerShoot)) {
                iter.remove();
                iterEnemy.remove();
                enemyHit = enemy;
                break;
            }
        }
        if (enemyHit != null) {
            EnemySpaceship newEnemyWhoCanShoot = null;
            for (EnemySpaceship enemy : enemies) {
                if (enemy.position().getX() == enemyHit.position().getX()) {
                    newEnemyWhoCanShoot = enemy;
                }
            }
            if (newEnemyWhoCanShoot != null) {
                newEnemyWhoCanShoot.prepareToShot();
            }
        }
    }

    public void listenOnDestroyed(OnEnemyDestroyed onEnemyDestroyed){
        this.onEnemyDestroyed.add(onEnemyDestroyed);
    }

    public void notifyAllOnEnemyDestroyed(EnemySpaceship enemy){
        for(OnEnemyDestroyed g: onEnemyDestroyed){
            g.onEnemyDestroyed(enemy);
        }
    }

    public void listenOnInvasionDestroyed(OnInvasionDestroyed listener) {
        this.onInvasionDestroyed.add(listener);
    }

   public void notifyAllOnInvasionDestroyed(){
        for(OnInvasionDestroyed g: onInvasionDestroyed){
            g.onInvasionDestroyed();
        }
    }

    public interface OnEnemyDestroyed {
        void onEnemyDestroyed(EnemySpaceship enemy);
    }

    public interface OnInvasionDestroyed {
        void onInvasionDestroyed();
    }
}
