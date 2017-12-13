package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.business.spaceinvaders.byty.Invasion;
import com.samsung.business.spaceinvaders.byty.Spaceship;
import com.samsung.business.spaceinvaders.byty.Enemy;
import com.samsung.business.spaceinvaders.ui.Score;
import com.samsung.business.spaceinvaders.zarzadzanie.ShotManager;
import com.samsung.business.spaceinvaders.zarzadzanie.GameManager;
import com.samsung.business.spaceinvaders.zarzadzanie.GraphicsManager;

public class SpaceInvaders extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private float animationTime;

    private ShotManager shotManager;
    private GameManager gameManager;
    private GraphicsManager graphicsManager;

    private Spaceship player;
    private Invasion invasion;

    @Override
    public void create() {
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        // create the raindrops array and spawn the first raindrop
        animationTime = 0f;

        //zaladuj nadzorce gry
        gameManager = new GameManager();

        //zaladui ui
        Score score = new Score(0);
        gameManager.setScore(score);

        //zaladuj tekstury
        graphicsManager = GraphicsManager.loadGraphics();

        //utworz rakiete gracza
        player = new Spaceship(graphicsManager.find("rakieta"));
        player.listenOnPlayerHit(new Spaceship.OnPlayerHit() {
            @Override
            public void onPlayerHit() {
                gameManager.gameOver();
            }
        });

        //przygotuj raid wroga
        invasion = Invasion.raid(graphicsManager);
        invasion.listenOnDestroyed(new Invasion.OnDestroyed() {
            @Override
            public void onDestroyed(Enemy enemy) {
                score.addScore(100);
            }
        });

        invasion.listenOnInvasionDestroyed(new Invasion.OnInvasionDestroyed() {
            @Override
            public void onInvasionDestroyed() {
                gameManager.win();
            }
        });

        gameManager.setObserverOnGameOver(new GameManager.ObserverOnGameOver() {
            @Override
            public void onGameOver(SpriteBatch batch, Score s) {
                BitmapFont font = new BitmapFont();
                font.draw(batch, "GAME OVER " + s.getScore(), 10, 230);
            }
        });
        gameManager.setObserverOnWin(new GameManager.ObserverOnWin() {
            @Override
            public void onGameFinished(SpriteBatch batch, Score s) {
                BitmapFont font = new BitmapFont();
                font.draw(batch, "YOU WON! " + s.getScore(), 10, 230);
            }
        });
        gameManager.setGameOngoing(new GameManager.GameOnGoingShitName() {
            @Override
            public void frame(SpriteBatch batch) {
                animationTime += Gdx.graphics.getDeltaTime();
                player.render(batch, animationTime);
                shotManager.render(batch, animationTime);
                invasion.render(batch, animationTime);
                score.render(batch);
            }
        });

        //zaladuj system zarzadzania pociskami
        shotManager = new ShotManager(graphicsManager, player, invasion);
    }

    @Override
    public void render() {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        batch.begin();
        gameManager.render(batch);
        batch.end();

        updatGameState();
    }

    private void updatGameState() {
        //zaktualizuj stan i polozenie gracza i wrogow
        player.update(camera, shotManager);
        invasion.update(camera, shotManager);

        //zaktualizuj stan pociskow
        shotManager.updateShots();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}