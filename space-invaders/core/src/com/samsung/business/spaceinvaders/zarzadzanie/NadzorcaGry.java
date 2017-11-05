package com.samsung.business.spaceinvaders.zarzadzanie;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.business.spaceinvaders.byty.Inwazja;
import com.samsung.business.spaceinvaders.ui.Score;

/**
 * Created by lb_lb on 13.10.17.
 */
public class NadzorcaGry {
    private ObserwatorGdyKoniecGry obserwatorGdyKoniecGry;
    private ObserwatorGdyWygrana obserwatorGdyWygrana;

    private GraSieToczy graSieToczy;

    private boolean koniecGry = false;
    private boolean wygrana = false;
    private Score score;

    public NadzorcaGry() {
    }

    public void render(SpriteBatch batch) {
        if (koniecGry) {
            obserwatorGdyKoniecGry.gdyKoniecGry(batch, score);
        } else if (wygrana) {
            obserwatorGdyWygrana.gdyKoniecGry(batch, score);
            graSieToczy.klatka(batch);
        } else {
            graSieToczy.klatka(batch);
        }
    }

    public void koniecGry() {
        this.koniecGry = true;
    }

    public void setObserwatorGdyKoniecGry(ObserwatorGdyKoniecGry obserwatorGdyKoniecGry) {
        this.obserwatorGdyKoniecGry = obserwatorGdyKoniecGry;
    }

    public void setObserwatorGdyWygrana(ObserwatorGdyWygrana obserwatorGdyWygrana) {
        this.obserwatorGdyWygrana = obserwatorGdyWygrana;
    }

    public void setGraSieToczy(GraSieToczy graSieToczy) {
        this.graSieToczy = graSieToczy;
    }

    public void wygrana() {
        this.wygrana = true;
    }

    public void setScore(Score score) {
        this.score = score;
    }


    public interface ObserwatorGdyKoniecGry {
        void gdyKoniecGry(SpriteBatch batch, Score score);
    }

    public interface ObserwatorGdyWygrana {
        void gdyKoniecGry(SpriteBatch batch, Score score);
    }

    public interface GraSieToczy {
        void klatka(SpriteBatch batch);
    }
}
