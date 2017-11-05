package com.samsung.business.spaceinvaders.zarzadzanie;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.samsung.business.spaceinvaders.byty.Inwazja;

/**
 * Created by lb_lb on 13.10.17.
 */
public class NadzorcaGry {
    private ObserwatorGdyKoniecGry obserwatorGdyKoniecGry;
    private ObserwatorGdyWygrana obserwatorGdyWygrana;

    private GraSieToczy graSieToczy;

    private boolean koniecGry = false;
    private boolean wygrana = false;

    public NadzorcaGry() {
    }

    public void render(SpriteBatch batch) {
        if (koniecGry) {
            obserwatorGdyKoniecGry.gdyKoniecGry(batch);
        } else if (wygrana) {
            obserwatorGdyWygrana.gdyKoniecGry(batch);
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


    public interface ObserwatorGdyKoniecGry {
        void gdyKoniecGry(SpriteBatch batch);
    }

    public interface ObserwatorGdyWygrana {
        void gdyKoniecGry(SpriteBatch batch);
    }

    public interface GraSieToczy {
        void klatka(SpriteBatch batch);
    }
}
