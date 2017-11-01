package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lb_lb on 01.11.17.
 */
public class Wrog {
    private static final int WYSOKOSC = 480;
    private static final int SZEROKOSC = 800;
    private static final int ILE_LINII_WROGOW = 3;
    private static final int ILU_WROGOW_W_LINII = 10;
    private static final int WYSOKOSC_WROGA = 40;
    private static final int SZEROKOSC_WROGA = 40;
    private static final int ODSTEP_POZIOMY_MIEDZY_WROGAMI = 12;
    private static final int ODSTEP_PIONOWY_MIEDZY_WROGAMI = 8;

    ZarzadcaBytow.Byt byt;
    public Rectangle pole;
    public boolean mozeStrzelac;
    public long czasOstatniegoStrzalu;
    private OnStrzalListener strzalListener;

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

    public void setStrzalListener(OnStrzalListener strzalListener) {
        this.strzalListener = strzalListener;
    }

    public void strzal() {
        if (this.mozeStrzelac &&
                TimeUtils.nanoTime() - this.czasOstatniegoStrzalu >
                        MathUtils.random(5000000000L, 15000000000L)
                ) {
            this.czasOstatniegoStrzalu = TimeUtils.nanoTime();
            WrogiPocisk pocisk = new WrogiPocisk(this.pole.getX(), this.pole.getY());

            strzalListener.onStrzal(pocisk);

        }
    }

    public static List<Wrog> dodajWrogow(ZarzadcaBytow zarzadcaBytow, List<WrogiPocisk> wrogiePociski) {
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

    public interface OnStrzalListener{
        void onStrzal(WrogiPocisk pocisk);
    }
}
