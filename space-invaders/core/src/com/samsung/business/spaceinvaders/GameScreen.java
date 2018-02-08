package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.business.spaceinvaders.entity.Invasion;
import com.samsung.business.spaceinvaders.entity.Spaceship;
import com.samsung.business.spaceinvaders.entity.Enemy;
import com.samsung.business.spaceinvaders.ui.InputManager;
import com.samsung.business.spaceinvaders.ui.KeyboardInput;
import com.samsung.business.spaceinvaders.ui.TouchInput;
import com.samsung.business.spaceinvaders.ui.Score;
import com.samsung.business.spaceinvaders.manager.ShootManager;
import com.samsung.business.spaceinvaders.manager.GameManager;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;

public class GameScreen implements Screen {
    private final SpaceInvaders spaceInvaders;

    private OrthographicCamera camera;

    private float animationTime;

    private ShootManager shootManager;
    private GameManager gameManager;
    private GraphicsManager graphicsManager;
    private InputManager inputManager;

    private Spaceship player;
    private Invasion invasion;

    public GameScreen(SpaceInvaders spaceInvaders) {
        this.spaceInvaders = spaceInvaders;
        create();
    }

    public void create() {
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



        animationTime = 0f;

        //zaladuj nadzorce gry
        gameManager = new GameManager();

        //zaladuj menadzera sterowania
        switch(Gdx.app.getType()) {
            case Android:
                inputManager = new InputManager(new TouchInput());
                break;
            case Desktop:
                inputManager = new InputManager(new KeyboardInput());
                break;
        }

        //zaladuj tekstury
        graphicsManager = GraphicsManager.loadGraphics();

        //utworz rakiete gracza
        player = new Spaceship(graphicsManager.find("rakieta"), inputManager);
        player.listenOnPlayerHit(new Spaceship.OnPlayerHit() {
            @Override
            public void onPlayerHit() {
                gameManager.gameOver();
            }
        });

        //przygotuj raid wroga
        invasion = Invasion.raid(graphicsManager);

        invasion.listenOnDestroyed(new Invasion.OnEnemyDestroyed() {
            @Override
            public void onEnemyDestroyed(Enemy enemy) {
                spaceInvaders.getScore().addScore(100);
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
//                BitmapFont font = new BitmapFont();
//                font.draw(batch, "GAME OVER " + s.getValue(), 10, 230);
            }
        });
        gameManager.setObserverOnWin(new GameManager.ObserverOnWin() {
            @Override
            public void onGameFinished(SpriteBatch batch, Score s) {
                BitmapFont font = new BitmapFont();
                font.draw(batch, "YOU WON! " + s.getValue(), 10, 230);
            }
        });
        gameManager.setNextFrameListener(new GameManager.OnNextFrameListener() {
            @Override
            public void frame(SpriteBatch batch) {
                animationTime += Gdx.graphics.getDeltaTime();
                player.render(batch, animationTime);
                shootManager.render(batch, animationTime);
                invasion.render(batch, animationTime);
                spaceInvaders.getScore().render(batch);
            }
        });

        //zaladuj system zarzadzania pociskami
        shootManager = new ShootManager(graphicsManager, player, invasion);
        shootManager.onMissed(()->{
            spaceInvaders.getScore().addScore(-10);
        });
    }


    private void updatGameState() {
        //zaktualizuj stan i polozenie gracza i wrogow
        player.update(camera, shootManager);
        invasion.update(camera, shootManager);

        //zaktualizuj stan pociskow
        shootManager.updateShots();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
        spaceInvaders.batch.setProjectionMatrix(camera.combined);

        //renderowanie gry
        spaceInvaders.batch.begin();
        gameManager.render(spaceInvaders.batch);
        spaceInvaders.batch.end();

        updatGameState();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}