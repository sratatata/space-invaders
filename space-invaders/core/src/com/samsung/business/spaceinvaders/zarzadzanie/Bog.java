package com.samsung.business.spaceinvaders.zarzadzanie;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.business.spaceinvaders.byty.Inwazja;
import com.samsung.business.spaceinvaders.byty.Pocisk;
import com.samsung.business.spaceinvaders.byty.PociskGracza;
import com.samsung.business.spaceinvaders.byty.Rakieta;
import com.samsung.business.spaceinvaders.byty.Smiertelny;
import com.samsung.business.spaceinvaders.byty.WrogiPocisk;

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
    private List<Pocisk> pociski;
    public final ZarzadcaBytow zarzadcaBytow;
    public final NadzorcaGry nadzorcaGry;
    public final Rakieta gracz;
    public final Inwazja inwazja;

    public Bog(ZarzadcaBytow zarzadcaBytow, NadzorcaGry nadzorcaGry, Rakieta gracz, Inwazja inwazja) {
        this.zarzadcaBytow = zarzadcaBytow;
        this.nadzorcaGry = nadzorcaGry;
        this.gracz = gracz;
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


                if (wrogiPocisk.pozaEkranem()) iter.remove();

                if(gracz.trafiony(pocisk)){
                    nadzorcaGry.koniecGry();
                }
            } else {
                PociskGracza naszStrzal = (PociskGracza) pocisk;
                naszStrzal.updateState();
                if (naszStrzal.pozaEkranem()) iter.remove();

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
