package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpaceInvaders extends ApplicationAdapter {

    private static final int WYSOKOSC = 480;


    private SpriteBatch batch;
    private OrthographicCamera camera;



    private List<WrogiPocisk> wrogieStrzaly = new ArrayList<>();
    float czasAnimacji;

    NadzorcaGry nadzorcaGry;
	ZarzadcaBytow zarzadcaBytow;

    Rakieta player;
    Inwazja inwazja;

    ZarzadcaBytow.Byt pocisk;
    ZarzadcaBytow.Byt obcyPocisk;

    @Override
    public void create() {
		zarzadcaBytow = ZarzadcaBytow.zaladujByty();

        player = new Rakieta(zarzadcaBytow.znajdzByt("rakieta"));

        pocisk = zarzadcaBytow.znajdzByt("pocisk");
        obcyPocisk = zarzadcaBytow.znajdzByt("obcyPocisk");

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        inwazja = Inwazja.nalot(zarzadcaBytow, wrogieStrzaly);

        // create the raindrops array and spawn the first raindrop
        czasAnimacji = 0f;

        nadzorcaGry = new NadzorcaGry();

        nadzorcaGry.setObserwatorGdyKoniecGry(batch -> {
            BitmapFont font = new BitmapFont();
            font.draw(batch, "GAME OVER", 10, 10);
        });

        nadzorcaGry.setObserwatorGdyWygrana(batch ->{
            BitmapFont font = new BitmapFont();
            font.draw(batch, "YOU WON!", 10, 10);
        });

        nadzorcaGry.setGraSieToczy(batch -> {
            czasAnimacji += Gdx.graphics.getDeltaTime();

            player.render(batch, czasAnimacji);

            TextureRegion klatkaPociskWrog = obcyPocisk.klatkaDoWyrenderowania(czasAnimacji);

            for (WrogiPocisk wrogiPocisk : wrogieStrzaly) {
                batch.draw(klatkaPociskWrog, wrogiPocisk.rectangle.x, wrogiPocisk.rectangle.y);
            }

            TextureRegion klatkaPocisk = pocisk.klatkaDoWyrenderowania(czasAnimacji);
            for (Rectangle naszStrzal : player.naszeStrzaly) {
                batch.draw(klatkaPocisk, naszStrzal.x, naszStrzal.y);
            }

            inwazja.render(batch, czasAnimacji);

        });

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

        updateState();
    }

    private void updateState() {
        player.updateState(camera);

        inwazja.updateState(camera);

        obsluzWrogieStrzaly();

        obsluzNaszeStrzaly();


        if (inwazja.wszyscyZgineli()) {
            nadzorcaGry.wygrana();
        }
    }

    private void obsluzNaszeStrzaly() {
        Iterator<Rectangle> iter = player.naszeStrzaly.iterator();
        while (iter.hasNext()) {
            Rectangle naszStrzal = iter.next();
            naszStrzal.y += 200 * Gdx.graphics.getDeltaTime();
            if (naszStrzal.y - 10 > WYSOKOSC) iter.remove();

            inwazja.jakiWrogTrafiony(iter, naszStrzal);
        }
    }



    private void obsluzWrogieStrzaly() {
//        System.out.println("DRop obsluzWrogieStrzaly, "+wrogieStrzaly.size());
        Iterator<WrogiPocisk> iter = wrogieStrzaly.iterator();
        while (iter.hasNext()) {
            WrogiPocisk wrogiPocisk = iter.next();
            wrogiPocisk.rectangle.y -= 200 * Gdx.graphics.getDeltaTime();
            if (wrogiPocisk.rectangle.y + 10 < 0) iter.remove();

            if (wrogiPocisk.rectangle.overlaps(player.rakietaRectangle)) {
                nadzorcaGry.koniecGry();
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}