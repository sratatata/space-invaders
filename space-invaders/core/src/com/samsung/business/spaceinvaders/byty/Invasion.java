package com.samsung.business.spaceinvaders.byty;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.samsung.business.spaceinvaders.zarzadzanie.ShotManager;
import com.samsung.business.spaceinvaders.zarzadzanie.GraphicsManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * NadzorcaGry sprawuje piecze nad warunkami zakonczenia gry.
 *
 * Created by lb_lb on 04.11.17.
 */
public class Invasion {
    private static final int HEIGHT = 480;
    private static final int WIDTH = 800;
    private static final int ENEMY_ROWS_COUNT = 3;
    private static final int ENEMY_IN_ROW_COUNT = 10;
    private static final int ENEMY_HEIGHT = 40;
    private static final int ENEMY_WIDTH = 40;
    private static final int HORIZONTAL_PADDING_ENEMIES = 12;
    private static final int VERTICAL_PADDING_ENEMIES = 8;

    private List<Enemy> enemies;
    private List<OnDestroyed> onDestroyed;
    private List<OnInvasionDestroyed> onInvasionDestroyed;

    private Invasion() {
        onDestroyed = new ArrayList<>();
        onInvasionDestroyed = new ArrayList<>();
    }

    public static Invasion raid(GraphicsManager graphicsManager) {
        Invasion invasion = new Invasion();
        invasion.enemies = prepareEnemies(invasion, graphicsManager);
        return invasion;
    }

    private static List<Enemy> prepareEnemies(Invasion invasion, GraphicsManager graphicsManager) {
        List<Enemy> enemies = new ArrayList<Enemy>();
        int horizontalPadding = (WIDTH - ENEMY_IN_ROW_COUNT * (ENEMY_WIDTH + HORIZONTAL_PADDING_ENEMIES)) / 2;

        for (int y = 0; y < ENEMY_ROWS_COUNT; y++) {
            for (int x = 0; x < ENEMY_IN_ROW_COUNT; x++) {
                Rectangle enemyRect = new Rectangle();
                enemyRect.x = horizontalPadding + x * (ENEMY_WIDTH + HORIZONTAL_PADDING_ENEMIES);
                enemyRect.y = HEIGHT - ENEMY_HEIGHT - y * (ENEMY_HEIGHT + VERTICAL_PADDING_ENEMIES);
                enemyRect.height = ENEMY_HEIGHT;
                enemyRect.width = ENEMY_WIDTH;

                boolean canShoot = y == ENEMY_ROWS_COUNT - 1;

                long lastShotTime = TimeUtils.nanoTime();

                Enemy enemy = new Enemy(graphicsManager.find("wrog"), enemyRect, canShoot, lastShotTime);
                enemy.registerOnDestroyed(new Enemy.OnDestroyed() {
                    @Override
                    public void onDestroyed() {
                        invasion.notifyAllOnDestroyed(enemy);
                    }
                });
                enemies.add(enemy);

            }
        }

        return enemies;
    }

    public void render(SpriteBatch batch, float animationTime) {
        for (Enemy enemy : enemies) {
            enemy.render(batch, animationTime);
        }
    }

    public void update(OrthographicCamera camera, ShotManager shotManager) {
        for (Enemy enemy : enemies) {
            enemy.updateState(camera);
            enemy.shot(shotManager);
        }

        if(enemies.isEmpty()){
            notifyAllOnInvasionDestroyed();
        }
    }

    public boolean isAllDestroyed() {
        return enemies.isEmpty();
    }

    public void checkEnemyHit(Iterator<Shot> iter, Shot playerShot) {
        Iterator<Enemy> iterEnemy = enemies.iterator();
        Targetable enemyHit = null;
        while (iterEnemy.hasNext()) {
            Enemy enemy = iterEnemy.next();
            if (enemy.isHit(playerShot)) {
                iter.remove();
                iterEnemy.remove();
                enemyHit = enemy;
            }
        }
        if (enemyHit != null) {
            Enemy newEnemyWhoCanShoot = null;
            for (Enemy enemy : enemies) {
                if (enemy instanceof Targetable && enemy.rectangle().getX() == enemyHit.rectangle().getX()) {
                    newEnemyWhoCanShoot = enemy;
                }
            }
            if (newEnemyWhoCanShoot != null) {
                newEnemyWhoCanShoot.prepareToShot();
            }
        }
    }

    public void listenOnDestroyed(OnDestroyed onDestroyed){
        this.onDestroyed.add(onDestroyed);
    }

    public void notifyAllOnDestroyed(Enemy enemy){
        for(OnDestroyed g: onDestroyed){
            g.onDestroyed(enemy);
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

    public interface OnDestroyed {
        void onDestroyed(Enemy enemy);
    }

    public interface OnInvasionDestroyed {
        void onInvasionDestroyed();
    }
}
