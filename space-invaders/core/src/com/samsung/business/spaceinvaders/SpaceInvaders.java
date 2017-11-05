package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.business.spaceinvaders.byty.Rakieta;
import com.samsung.business.spaceinvaders.zarzadzanie.Bog;
import com.samsung.business.spaceinvaders.zarzadzanie.ZarzadcaBytow;

public class SpaceInvaders extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private float czasAnimacji;

    private com.samsung.business.spaceinvaders.zarzadzanie.Bog bog;
    private com.samsung.business.spaceinvaders.zarzadzanie.NadzorcaGry nadzorcaGry;
    private com.samsung.business.spaceinvaders.zarzadzanie.ZarzadcaBytow zarzadcaBytow;

    private com.samsung.business.spaceinvaders.byty.Rakieta player;
    private com.samsung.business.spaceinvaders.byty.Inwazja inwazja;

    @Override
    public void create() {
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        // create the raindrops array and spawn the first raindrop
        czasAnimacji = 0f;

        //zaladuj tekstury
        zarzadcaBytow = ZarzadcaBytow.zaladujByty();

        //utworz rakiete gracza
        player = new Rakieta(zarzadcaBytow.znajdzByt("rakieta"));

        //przygotuj nalot wroga
        inwazja = com.samsung.business.spaceinvaders.byty.Inwazja.nalot(zarzadcaBytow);

        //zaladuj nadzorce gry
        nadzorcaGry = new com.samsung.business.spaceinvaders.zarzadzanie.NadzorcaGry(inwazja);
        nadzorcaGry.setObserwatorGdyKoniecGry(batch -> {
            BitmapFont font = new BitmapFont();
            font.draw(batch, "GAME OVER", 10, 10);
        });
        nadzorcaGry.setObserwatorGdyWygrana(batch -> {
            BitmapFont font = new BitmapFont();
            font.draw(batch, "YOU WON!", 10, 10);
        });
        nadzorcaGry.setGraSieToczy(batch -> {
            czasAnimacji += Gdx.graphics.getDeltaTime();
            player.render(batch, czasAnimacji);
            bog.render(batch, czasAnimacji);
            inwazja.render(batch, czasAnimacji);
        });

        //zaladuj system zarzadzania pociskami
        bog = new Bog(zarzadcaBytow, nadzorcaGry, player, inwazja);
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
            nadzorcaGry.render(batch);
        batch.end();

        aktualizujStanGry();
    }

    private void aktualizujStanGry() {
        //zaktualizuj stan i polozenie gracza i wrogow
        player.aktualizuj(camera, bog);
        inwazja.aktualizuj(camera, bog);

        //zaktualizuj stan pociskow
        bog.kuleNosi();

        //sprawdz czy gra sie zakonczyla
        nadzorcaGry.sprawdzWarunekKonca();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}