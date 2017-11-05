package com.samsung.business.spaceinvaders.byty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by lb_lb on 01.11.17.
 */
public class WrogiPocisk implements Pocisk {
    private Rectangle rectangle;
    private com.samsung.business.spaceinvaders.zarzadzanie.ZarzadcaBytow.Byt byt;

    public WrogiPocisk(com.samsung.business.spaceinvaders.zarzadzanie.ZarzadcaBytow.Byt byt, float originX, float originY) {
        this.byt = byt;
        rectangle = new Rectangle();
        rectangle.x = originX;
        rectangle.y = originY;
        rectangle.height = 10;
        rectangle.width = 10;
    }


    @Override
    public void render(SpriteBatch batch, float czasAnimacji) {
        TextureRegion klatkaPociskWrog = byt.klatkaDoWyrenderowania(czasAnimacji);
        batch.draw(klatkaPociskWrog, rectangle.x, rectangle.y);
    }

    @Override
    public void updateState() {
        this.rectangle.y -= 200 * Gdx.graphics.getDeltaTime();
    }

    @Override
    public boolean trafilW(Smiertelny smiertelnyObiekt) {
        return smiertelnyObiekt.trafienie(this.rectangle, smiertelnyObiekt.namiary());
    }

    public boolean pozaEkranem() {
        return this.rectangle.y + 10 < 0;
    }
}
