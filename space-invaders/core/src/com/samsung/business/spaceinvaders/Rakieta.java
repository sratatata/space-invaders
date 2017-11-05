package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lb_lb on 01.11.17.
 */
public class Rakieta {
    public final ZarzadcaBytow.Byt byt;

    protected Rectangle rakietaRectangle;
    private long czasOstatniegoStrzalu;


    public List<Rectangle> naszeStrzaly = new ArrayList<Rectangle>();

    public Rakieta(ZarzadcaBytow.Byt byt) {
        this.byt = byt;
        dodajRakiete();
    }

    public void render(SpriteBatch batch, float czasAnimacji){
        TextureRegion klatkaRakiety = byt.klatkaDoWyrenderowania(czasAnimacji);
        batch.draw(klatkaRakiety, rakietaRectangle.x, rakietaRectangle.y);
    }

    private void dodajRakiete() {
        rakietaRectangle = new Rectangle();
        rakietaRectangle.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        rakietaRectangle.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
        rakietaRectangle.width = 40;
        rakietaRectangle.height = 40;
    }

    private void naszStrzal(Bog bog) {
        if (!mozemyStrzelic()) {
            return;
        }
        Pocisk pocisk = new PociskGracza(bog.zarzadcaBytow.znajdzByt("pocisk"), rakietaRectangle.getX(), rakietaRectangle.getY());

        bog.dodajPocisk(pocisk);
        czasOstatniegoStrzalu = TimeUtils.nanoTime();
    }

    private boolean mozemyStrzelic() {
        return TimeUtils.nanoTime() - czasOstatniegoStrzalu > 600 * 1000 * 1000;
    }

    public void aktualizuj(OrthographicCamera camera, Bog bog){
        // process user input
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getY() > 360) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                rakietaRectangle.x = touchPos.x - 40 / 2;
            } else {
                naszStrzal(bog);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            rakietaRectangle.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            rakietaRectangle.x += 200 * Gdx.graphics.getDeltaTime();


        // make sure the bucket stays within the screen bounds
        if (rakietaRectangle.x < 0) rakietaRectangle.x = 0;
        if (rakietaRectangle.x > 800 - 20) rakietaRectangle.x = 800 - 20;
    }

}
