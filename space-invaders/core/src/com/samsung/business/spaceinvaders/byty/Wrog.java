package com.samsung.business.spaceinvaders.byty;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.samsung.business.spaceinvaders.zarzadzanie.Bog;
import com.samsung.business.spaceinvaders.zarzadzanie.ZarzadcaBytow;

/**
 * Reprezentuje postaci wrogow. Wrogowie najczesciej dokonuja grupowej Inwazji.
 *
 * Created by lb_lb on 01.11.17.
 */
public class Wrog implements Smiertelny{
    private ZarzadcaBytow.Byt byt;
    private  Rectangle pole;
    private boolean mozeStrzelac;
    private long czasOstatniegoStrzalu;

    public Wrog(ZarzadcaBytow.Byt byt, Rectangle pole, boolean mozeStrzelac, long czasOstatniegoStrzalu) {
        this.byt = byt;
        this.pole = pole;
        this.mozeStrzelac = mozeStrzelac;
        this.czasOstatniegoStrzalu = czasOstatniegoStrzalu;
    }

    public void updateState(OrthographicCamera camera){

    }

    public void render(SpriteBatch batch, float czasAnimacji){
        TextureRegion klatkaRakiety = byt.klatkaDoWyrenderowania(czasAnimacji);
        batch.draw(klatkaRakiety, pole.x, pole.y);
    }

    public Bog strzal(Bog bog) {
        if (this.mozeStrzelac &&
                TimeUtils.nanoTime() - this.czasOstatniegoStrzalu >
                        MathUtils.random(5000000000L, 15000000000L)
                ) {
            this.czasOstatniegoStrzalu = TimeUtils.nanoTime();

            ZarzadcaBytow.Byt bytPocisku = bog.zarzadcaBytow.znajdzByt("obcyPocisk");
            bog.dodajPocisk( (Pocisk) new WrogiPocisk(bytPocisku, this.pole.getX(), this.pole.getY()));

        }

        return bog;
    }

    @Override
    public Rectangle namiary() {
        return this.pole;
    }

    @Override
    public boolean trafienie(Rectangle cel, Rectangle pocisk) {
        return cel.overlaps(pocisk);
    }

    @Override
    public boolean trafiony(Pocisk pocisk) {
        return pocisk.trafilW(this);
    }

    public void przygotujDoStrzalu() {
        mozeStrzelac = true;
    }
}
