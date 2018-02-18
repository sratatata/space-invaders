package com.samsung.business.spaceinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.samsung.business.spaceinvaders.SpaceInvaders;
import com.samsung.business.spaceinvaders.entity.Enemy;
import com.samsung.business.spaceinvaders.entity.Invasion;
import com.samsung.business.spaceinvaders.entity.Spaceship;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;
import com.samsung.business.spaceinvaders.manager.ShootManager;
import com.samsung.business.spaceinvaders.ui.InputManager;
import com.samsung.business.spaceinvaders.ui.KeyboardInput;
import com.samsung.business.spaceinvaders.ui.ScoreGuiLabel;
import com.samsung.business.spaceinvaders.ui.TouchInput;

public class GameScreen implements Screen {
    private final SpaceInvaders spaceInvaders;

    private OrthographicCamera camera;

    private ShootManager shootManager;
    private GraphicsManager graphicsManager;
    private InputManager inputManager;

    private Spaceship player;
    private Invasion invasion;

    private ScoreGuiLabel scoreGuiLabel;

    public GameScreen(SpaceInvaders spaceInvaders) {
        this.spaceInvaders = spaceInvaders;
        scoreGuiLabel = new ScoreGuiLabel();
        create();
    }

    public void create() {
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //zaladuj menadzera sterowania
        switch(Gdx.app.getType()) {
            case Android:
                inputManager = new InputManager(new TouchInput());
                break;
            case Desktop:
                inputManager = new InputManager(new KeyboardInput());
                break;
        }

        inputManager.setExitListener(()->{
            spaceInvaders.setScreen(new MainMenuScreen(spaceInvaders));
            spaceInvaders.getScore().reset();
            dispose();
        });

        //zaladuj tekstury
        graphicsManager = GraphicsManager.loadGraphics();

        //utworz rakiete gracza
        player = new Spaceship(graphicsManager.find("rakieta"), inputManager);
        player.listenOnPlayerHit(new Spaceship.OnPlayerHit() {
            @Override
            public void onPlayerHit() {
                spaceInvaders.gameOver();
                dispose();
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
                spaceInvaders.win();
                dispose();
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


        scoreGuiLabel.setScore(spaceInvaders.getScore().getValue());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        spaceInvaders.batch.setProjectionMatrix(camera.combined);

        //renderowanie gry
        spaceInvaders.batch.begin();

        player.render(spaceInvaders.batch, delta);
        shootManager.render(spaceInvaders.batch, delta);
        invasion.render(spaceInvaders.batch, delta);
        scoreGuiLabel.render(spaceInvaders.batch);
        spaceInvaders.batch.end();

        inputManager.update();

        updatGameState();
    }

    @Override
    public void resize(int width, int height) {
        camera.update();
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