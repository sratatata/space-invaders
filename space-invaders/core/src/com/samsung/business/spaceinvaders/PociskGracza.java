package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by lb_lb on 05.11.17.
 */
public class PociskGracza implements Pocisk {
    Rectangle rectangle;
    private ZarzadcaBytow.Byt byt;

    public PociskGracza(ZarzadcaBytow.Byt byt, float originX, float originY) {
        this.byt = byt;
        rectangle = new Rectangle();
        rectangle.x = originX;
        rectangle.y = originY;
        rectangle.height = 10;
        rectangle.width = 10;
    }

    @Override
    public void render(SpriteBatch batch, float czasAnimacji) {
        TextureRegion klatkaPocisk = byt.klatkaDoWyrenderowania(czasAnimacji);
        batch.draw(klatkaPocisk, rectangle.x, rectangle.y);
    }

    @Override
    public void updateState() {
        this.rectangle.y += 200 * Gdx.graphics.getDeltaTime();
    }
}
