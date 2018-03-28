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
 * Created by Kamil on 2018-03-28.
 */

public class Walls {
    private static final int HEIGHT = DisplayInfo.getHeight();
    private static final int WIDTH = DisplayInfo.getWidth();
    private static final int PADDING_TOP = 300;
    private static final int WALLS_COUNT = 3;
    private static final int WALL_ROWS_COUNT = 2;
    private static final int BRICKS_IN_WALL_ROW = 3;
    private static final int BRICK_HEIGHT = 26;
    private static final int BRICK_WIDTH = 40;

    private List<Brick> bricks;
    private List<OnBrickDestroyed> onBrickDestroyed;

    private Walls() {
        onBrickDestroyed = new ArrayList<>();
    }

    public static Walls build(GraphicsManager graphicsManager) {
        Walls walls = new Walls();
        walls.bricks = prepareBricks(walls, graphicsManager);
        return walls;
    }

    private static List<Brick> prepareBricks(Walls walls, GraphicsManager graphicsManager) {
        List<Brick> bricks = new ArrayList<Brick>();
        int horizontalPadding = (WIDTH - WALLS_COUNT * BRICKS_IN_WALL_ROW * BRICK_WIDTH) / 4;

        for (int y = 0; y < WALL_ROWS_COUNT; y++) {
            for (int x = 0; x < WALLS_COUNT * BRICKS_IN_WALL_ROW; x++) {
                Rectangle brickRect = new Rectangle();
                if (x >= 0 && x < 3) {
                    brickRect.x = 1 * horizontalPadding + x * BRICK_WIDTH;
                } else if (x >= 3 && x < 6) {
                    brickRect.x = 2 * horizontalPadding + x * BRICK_WIDTH;
                } else if (x >= 6 && x < 9) {
                    brickRect.x = 3 * horizontalPadding + x * BRICK_WIDTH;
                }
                brickRect.y = HEIGHT - ( PADDING_TOP )- (y + 1) * BRICK_HEIGHT;
                brickRect.height = BRICK_HEIGHT;
                brickRect.width = BRICK_WIDTH;

                Brick brick = new Brick(graphicsManager.find("sciana"), brickRect, 0);
                brick.registerOnBrickHit(new Brick.OnBrickHit() {
                    @Override
                    public void onBrickHit() {
                        walls.notifyAllOnBrickDestroyed(brick);
                    }
                });
                bricks.add(brick);

            }
        }

        return bricks;
    }

    public void render(SpriteBatch batch) {
        for (Brick brick : bricks) {
            brick.render(batch);
        }
    }

    public void update(OrthographicCamera camera, ShootManager shootManager) {
        for (Brick brick : bricks) {
            brick.updateState(camera);
        }
    }

    public void checkBrickHit(Iterator<Shoot> iter, Shoot enemyShoot) {
        Iterator<Brick> iterBrick = bricks.iterator();
        Brick brickHit = null;
        while (iterBrick.hasNext()) {
            Brick brick = iterBrick.next();
            if (brick.isHit(enemyShoot)) {
                iter.remove();
                if (brick.hitCount >=3) {
                    iterBrick.remove();
                    brickHit = brick;
                    break;
                }
                break;
            }
        }
    }

    public void listenOnDestroyed(Walls.OnBrickDestroyed onBrickDestroyed){
        this.onBrickDestroyed.add(onBrickDestroyed);
    }

    public void notifyAllOnBrickDestroyed(Brick brick){
        for(Walls.OnBrickDestroyed g: onBrickDestroyed){
            g.onBrickDestroyed(brick);
        }
    }

    public interface OnBrickDestroyed {
        void onBrickDestroyed(Brick brick);
    }
}
