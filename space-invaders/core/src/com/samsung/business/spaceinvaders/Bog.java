package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Czlowiek strzela a Bog kule nosi.
 *
 * Obiekt zarzadza strzalami wrogow i gracza.
 *
 * Created by lb_lb on 05.11.17.
 */
public class Bog {
    private static final int WYSOKOSC = 480;
    private List<Pocisk> pociski;
    public ZarzadcaBytow zarzadcaBytow;
    public NadzorcaGry nadzorcaGry;
    public Rakieta player;
    public Inwazja inwazja;

    public Bog(ZarzadcaBytow zarzadcaBytow, NadzorcaGry nadzorcaGry, Rakieta player, Inwazja inwazja) {
        this.zarzadcaBytow = zarzadcaBytow;
        this.nadzorcaGry = nadzorcaGry;
        this.player = player;
        this.inwazja = inwazja;
        this.pociski = new ArrayList<>();
    }

    public Bog dodajPocisk(Pocisk pocisk) {
        pociski.add(pocisk);
        return this;
    }

    public void kuleNosi() {
        Iterator<Pocisk> iter = pociski.iterator();
        while (iter.hasNext()) {
            Pocisk pocisk = iter.next();
            if (pocisk instanceof WrogiPocisk) {
                WrogiPocisk wrogiPocisk = (WrogiPocisk) pocisk;
                wrogiPocisk.updateState();

                if (wrogiPocisk.rectangle.y + 10 < 0) iter.remove();

                if (wrogiPocisk.rectangle.overlaps(player.rakietaRectangle)) {
                    nadzorcaGry.koniecGry();
                }
            } else {
                PociskGracza naszStrzal = (PociskGracza) pocisk;
                naszStrzal.updateState();
                if (naszStrzal.rectangle.y - 10 > WYSOKOSC) iter.remove();

                inwazja.jakiWrogTrafiony(iter, naszStrzal);
            }
        }

    }

    public void render(SpriteBatch batch, float czasAnimacji) {
        for (Pocisk p : pociski) {
            p.render(batch, czasAnimacji);
        }
    }

}
