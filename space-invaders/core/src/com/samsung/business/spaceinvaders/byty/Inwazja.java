package com.samsung.business.spaceinvaders.byty;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.samsung.business.spaceinvaders.zarzadzanie.Bog;
import com.samsung.business.spaceinvaders.zarzadzanie.ZarzadcaBytow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * NadzorcaGry sprawuje piecze nad warunkami zakonczenia gry.
 *
 * Created by lb_lb on 04.11.17.
 */
public class Inwazja {
    private static final int WYSOKOSC = 480;
    private static final int SZEROKOSC = 800;
    private static final int ILE_LINII_WROGOW = 3;
    private static final int ILU_WROGOW_W_LINII = 10;
    private static final int WYSOKOSC_WROGA = 40;
    private static final int SZEROKOSC_WROGA = 40;
    private static final int ODSTEP_POZIOMY_MIEDZY_WROGAMI = 12;
    private static final int ODSTEP_PIONOWY_MIEDZY_WROGAMI = 8;

    private List<Wrog> wrogowie;

    private Inwazja() {
    }

    public static Inwazja nalot(ZarzadcaBytow zarzadcaBytow) {
        Inwazja inwazja = new Inwazja();
        inwazja.wrogowie = dodajWrogow(zarzadcaBytow);
        return inwazja;
    }

    private static List<Wrog> dodajWrogow(ZarzadcaBytow zarzadcaBytow) {
        List<Wrog> wrogowie = new ArrayList<Wrog>();
        int wcieciePoziome = (SZEROKOSC - ILU_WROGOW_W_LINII * (SZEROKOSC_WROGA + ODSTEP_POZIOMY_MIEDZY_WROGAMI)) / 2;

        for (int y = 0; y < ILE_LINII_WROGOW; y++) {
            for (int x = 0; x < ILU_WROGOW_W_LINII; x++) {
                Rectangle poleWroga = new Rectangle();
                poleWroga.x = wcieciePoziome + x * (SZEROKOSC_WROGA + ODSTEP_POZIOMY_MIEDZY_WROGAMI);
                poleWroga.y = WYSOKOSC - WYSOKOSC_WROGA - y * (WYSOKOSC_WROGA + ODSTEP_PIONOWY_MIEDZY_WROGAMI);
                poleWroga.height = WYSOKOSC_WROGA;
                poleWroga.width = SZEROKOSC_WROGA;

                boolean mozeStrzelac = y == ILE_LINII_WROGOW - 1;

                long czasOstatniegoStrzalu = TimeUtils.nanoTime();

                Wrog wrog = new Wrog(zarzadcaBytow.znajdzByt("wrog"), poleWroga, mozeStrzelac, czasOstatniegoStrzalu);
                wrogowie.add(wrog);

            }
        }

        return wrogowie;
    }

    public void render(SpriteBatch batch, float czasAnimacji) {
        for (Wrog wrog : wrogowie) {
            wrog.render(batch, czasAnimacji);
        }
    }

    public void aktualizuj(OrthographicCamera camera, Bog bog) {
        for (Wrog wrog : wrogowie) {
            wrog.updateState(camera);
            wrog.strzal(bog);
        }
    }

    public boolean wszyscyZgineli() {
        return wrogowie.isEmpty();
    }

    public void jakiWrogTrafiony(Iterator<Pocisk> iter, Pocisk naszStrzal) {
        Iterator<Wrog> iterWrog = wrogowie.iterator();
        Smiertelny trafionyWrog = null;
        while (iterWrog.hasNext()) {
            Wrog wrog = iterWrog.next();
            if (wrog.trafiony(naszStrzal)) {
                iter.remove();
                iterWrog.remove();
                trafionyWrog = wrog;
            }
        }
        if (trafionyWrog != null) {
            Wrog nowyWrogMogacyStrzelac = null;
            for (Wrog wrog : wrogowie) {
                if (wrog instanceof Smiertelny && wrog.namiary().getX() == trafionyWrog.namiary().getX()) {
                    nowyWrogMogacyStrzelac = wrog;
                }
            }
            if (nowyWrogMogacyStrzelac != null) {
                nowyWrogMogacyStrzelac.przygotujDoStrzalu();
            }
        }
    }
}
