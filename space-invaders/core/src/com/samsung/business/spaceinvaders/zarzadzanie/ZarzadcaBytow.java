package com.samsung.business.spaceinvaders.zarzadzanie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lb_lb on 08.10.17.
 */
public class ZarzadcaBytow {
    public Map<String, Byt> byty = new HashMap<>();

    public void render(){

    }

    public void create(){

    }

    public void dispose(){

    }

    public static ZarzadcaBytow zaladujByty(){
        ZarzadcaBytow zarzadcaBytow = new ZarzadcaBytow();
        zarzadcaBytow.dodajByt("rakieta", Byt.wczytajZPliku(Gdx.files.internal("rakieta.png"), 4, 2));
        zarzadcaBytow.dodajByt("wrog", Byt.wczytajZPliku(Gdx.files.internal("obcy.png"), 4, 2));
        zarzadcaBytow.dodajByt("pocisk", Byt.wczytajZPliku(Gdx.files.internal("pocisk-rakieta.png"), 5, 1));
        zarzadcaBytow.dodajByt("obcyPocisk", Byt.wczytajZPliku(Gdx.files.internal("obcy-plazma.png"), 3, 3));

        return zarzadcaBytow;
    }

    public Byt znajdzByt(String name){
        return byty.get(name);
    }

    private void dodajByt(String rakieta, Byt byt) {
        byty.put(rakieta, byt);
    }

    public static class Byt{
        private static final boolean ZAPETLONA = true; //warto nazywac stale w kodzie, zeby jasne bylo do czego ona sluzy
        private Texture tekstura;
        private Animation<TextureRegion> animacja;

        public Byt(Texture tekstura, int klatkiKolumny, int klatkiWiersze) {
            this.tekstura = tekstura;
            this.animacja = przygotujAnimacje(tekstura, klatkiKolumny, klatkiWiersze);
        }

        public static Byt wczytajZPliku(FileHandle textureFile, int klatkiKolumny, int klatkiWiersze){
            Texture tekstura = new Texture(textureFile);
            return new Byt(tekstura, klatkiKolumny, klatkiWiersze);
        }

        public TextureRegion klatkaDoWyrenderowania(float czasAnimacji){
            return animacja.getKeyFrame(czasAnimacji, ZAPETLONA);
        }

        private Animation<TextureRegion> przygotujAnimacje(Texture tekstura, int klatkiKolumny, int klatkiWiersze){
            TextureRegion[][] tmp = TextureRegion.split(tekstura,
                    tekstura.getWidth()/klatkiKolumny,
                    tekstura.getHeight()/klatkiWiersze);
            TextureRegion[] teksturaKlatki = new TextureRegion[klatkiWiersze * klatkiKolumny];
            int indeks = 0;
            for (int i = 0; i < klatkiWiersze; i++){
                for (int j = 0; j < klatkiKolumny; j++){
                    teksturaKlatki[indeks++] = tmp[i][j];
                }
            }
            return new Animation<TextureRegion>(0.025f, teksturaKlatki);
        }
    }
}
