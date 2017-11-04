package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
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

    List<Wrog> wrogowie;

    private Inwazja(){

    }

    public static Inwazja nalot(ZarzadcaBytow zarzadcaBytow, List<WrogiPocisk> wrogiePociski){
        Inwazja inwazja = new Inwazja();
        inwazja.wrogowie = dodajWrogow(zarzadcaBytow, wrogiePociski);
        return  inwazja;
    }

    private static List<Wrog> dodajWrogow(ZarzadcaBytow zarzadcaBytow, List<WrogiPocisk> wrogiePociski) {
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
                wrog.setStrzalListener((wrogiStrzal) ->{
                    wrogiePociski.add(wrogiStrzal);

                });
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

    public void updateState(OrthographicCamera camera) {
        for (Wrog wrog : wrogowie) {
            wrog.updateState(camera);
            wrog.strzal();
        }
    }

    public boolean wszyscyZgineli() {
        return wrogowie.isEmpty();
    }

    public void jakiWrogTrafiony(Iterator<Rectangle> iter, Rectangle naszStrzal) {
        Iterator<Wrog> iterWrog = wrogowie.iterator();
        Wrog trafionyWrog = null;
        while (iterWrog.hasNext()) {
            Wrog wrog = iterWrog.next();
            if (naszStrzal.overlaps(wrog.pole)) {
                //	dropSound.play();
                iter.remove();
                iterWrog.remove();
                trafionyWrog = wrog;
            }
        }
        if (trafionyWrog != null) {
            Wrog nowyWrogMogacyStrzelac = null;
            for (Wrog wrog : wrogowie) {
                if (wrog.pole.getX() == trafionyWrog.pole.getX()) {
                    nowyWrogMogacyStrzelac = wrog;
                }
            }
            if (nowyWrogMogacyStrzelac != null) {
                nowyWrogMogacyStrzelac.mozeStrzelac = true;
            }
        }
    }
}
