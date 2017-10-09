package com.samsung.business.spaceinvaders;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by lb_lb on 08.10.17.
 */
public class ZarzadcaBytow {
    public void render(){

    }

    public void create(){

    }

    public void dispose(){

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
