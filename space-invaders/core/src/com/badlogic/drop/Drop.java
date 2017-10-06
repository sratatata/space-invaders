package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Drop extends ApplicationAdapter {

    private static class Wrog {
        public Rectangle pole;
        public boolean mozeStrzelac;
        public long czasOstatniegoStrzalu;

        public Wrog(Rectangle pole, boolean mozeStrzelac, long czasOstatniegoStrzalu) {
            this.pole = pole;
            this.mozeStrzelac = mozeStrzelac;
            this.czasOstatniegoStrzalu = czasOstatniegoStrzalu;
        }
    }

    private static final int WYSOKOSC = 480;
    private static final int SZEROKOSC = 800;
    private static final int ILE_LINII_WROGOW = 3;
    private static final int ILU_WROGOW_W_LINII = 14;
    private static final int WYSOKOSC_WROGA = 40;
    private static final int SZEROKOSC_WROGA = 40;
    private static final int ODSTEP_POZIOMY_MIEDZY_WROGAMI = 12;
    private static final int ODSTEP_PIONOWY_MIEDZY_WROGAMI = 8;

    private Texture dropImage;
    private Texture bucketImage;

    private Texture rakietaImage;
    private Texture pociskImage;
    private Texture pociskObcyImage;

    private Texture wrogImage;
    //	private Sound dropSound;
//	private Music rainMusic;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Rectangle rakieta;
    List<Wrog> wrogowie = new ArrayList<Wrog>();

    private List<Rectangle> naszeStrzaly = new ArrayList<Rectangle>();
    private List<Rectangle> wrogieStrzaly = new ArrayList<Rectangle>();
    private long czasOstatniegoStrzalu;
    private boolean koniecGry = false;
    private Animation<TextureRegion> rakietaAnimacja;
    private Animation<TextureRegion> wrogAnimacja;
    private Animation<TextureRegion> pociskAnimacja;
    private Animation<TextureRegion> pociskWrogAnimacja;
    float czasAnimacji;

    @Override
    public void create() {
        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        wrogImage = new Texture(Gdx.files.internal("obcy.png"));
        rakietaImage = new Texture(Gdx.files.internal("rakieta.png"));
        pociskImage = new Texture(Gdx.files.internal("pocisk-rakieta.png"));
        pociskObcyImage = new Texture(Gdx.files.internal("obcy-plazma.png"));

        rakietaAnimacja = przygotujAnimacje(rakietaImage, 4, 2);
        wrogAnimacja = przygotujAnimacje(wrogImage, 4, 2);
        pociskAnimacja = przygotujAnimacje(pociskImage, 5, 1);
        pociskWrogAnimacja = przygotujAnimacje(pociskObcyImage, 3, 3);
        // load the drop sound effect and the rain background "music"
        //dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        //rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // start the playback of the background music immediately
        //rainMusic.setLooping(true);
        //rainMusic.play();

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        // create a Rectangle to logically represent the bucket
        rakieta = new Rectangle();
        rakieta.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        rakieta.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
        rakieta.width = 64;
        rakieta.height = 64;

        int wcieciePoziome = (SZEROKOSC - ILU_WROGOW_W_LINII * (SZEROKOSC_WROGA + ODSTEP_POZIOMY_MIEDZY_WROGAMI))/2;
        for (int y = 0; y < ILE_LINII_WROGOW; y++) {
            for (int x = 0; x < ILU_WROGOW_W_LINII; x++) {
                Rectangle poleWroga = new Rectangle();
                poleWroga.x = wcieciePoziome + x * (SZEROKOSC_WROGA + ODSTEP_POZIOMY_MIEDZY_WROGAMI);
                poleWroga.y = WYSOKOSC - WYSOKOSC_WROGA - y * (WYSOKOSC_WROGA + ODSTEP_PIONOWY_MIEDZY_WROGAMI);
                poleWroga.height = WYSOKOSC_WROGA;
                poleWroga.width = SZEROKOSC_WROGA;

                boolean mozeStrzelac = y == ILE_LINII_WROGOW - 1;

                long czasOstatniegoStrzalu = TimeUtils.nanoTime();

                wrogowie.add(new Wrog(poleWroga, mozeStrzelac, czasOstatniegoStrzalu));
            }
        }

        // create the raindrops array and spawn the first raindrop
        czasAnimacji = 0f;
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


    private void naszStrzal() {
        if (!mozemyStrzelic()) {
            return;
        }
        Rectangle nowyStrzal = new Rectangle();
        nowyStrzal.x = rakieta.getX();
        nowyStrzal.y = rakieta.getY();
        ;
        nowyStrzal.width = 10;
        nowyStrzal.height = 10;
        naszeStrzaly.add(nowyStrzal);
        czasOstatniegoStrzalu = TimeUtils.nanoTime();
    }

    private boolean mozemyStrzelic() {
        return TimeUtils.nanoTime() - czasOstatniegoStrzalu > 600 * 1000 * 1000;
    }


    @Override
    public void render() {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops

        czasAnimacji += Gdx.graphics.getDeltaTime();
        TextureRegion klatkaRakiety = rakietaAnimacja.getKeyFrame(czasAnimacji, true);
        TextureRegion klatkaPocisk = pociskAnimacja.getKeyFrame(czasAnimacji, true);
        TextureRegion klatkaPociskWrog = pociskWrogAnimacja.getKeyFrame(czasAnimacji, true);
        TextureRegion klatkaWrog = wrogAnimacja.getKeyFrame(czasAnimacji, true);

        batch.begin();
        if (koniecGry){
            BitmapFont font = new BitmapFont();
            font.draw(batch, "GAME OVER", 10, 10);
        } else {
            batch.draw(klatkaRakiety, rakieta.x, rakieta.y);
            for (Rectangle wrogiStrzal : wrogieStrzaly) {
                batch.draw(klatkaPociskWrog, wrogiStrzal.x, wrogiStrzal.y);
            }
            for (Rectangle naszStrzal : naszeStrzaly) {
                batch.draw(klatkaPocisk, naszStrzal.x, naszStrzal.y);
            }
            for (Wrog wrog : wrogowie) {
                batch.draw(klatkaWrog, wrog.pole.x, wrog.pole.y);
            }
        }
        batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getY() > 360) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                rakieta.x = touchPos.x - 40 / 2;
            } else {
                naszStrzal();
            }
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) rakieta.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) rakieta.x += 200 * Gdx.graphics.getDeltaTime();


        // make sure the bucket stays within the screen bounds
        if (rakieta.x < 0) rakieta.x = 0;
        if (rakieta.x > 800 - 40) rakieta.x = 800 - 40;

        for (Wrog wrog : wrogowie) {
            if (wrog.mozeStrzelac &&
                    TimeUtils.nanoTime() - wrog.czasOstatniegoStrzalu >
                            MathUtils.random(5000000000L, 15000000000L)
                    ) {
                wrog.czasOstatniegoStrzalu = TimeUtils.nanoTime();
                Rectangle wrogiStrzal = new Rectangle();
                wrogiStrzal.x = wrog.pole.getX();
                wrogiStrzal.y = wrog.pole.getY();
                wrogiStrzal.height = 10;
                wrogiStrzal.width = 10;
                wrogieStrzaly.add(wrogiStrzal);
            }
        }

        obsluzWrogieStrzaly();

        obsluzNaszeStrzaly();


        if (wrogowie.isEmpty()) {
            koniecGry = true;
        }
    }

    private void obsluzNaszeStrzaly() {
        Iterator<Rectangle> iter = naszeStrzaly.iterator();
        while (iter.hasNext()) {
            Rectangle naszStrzal = iter.next();
            naszStrzal.y += 200 * Gdx.graphics.getDeltaTime();
            if (naszStrzal.y - 10 > WYSOKOSC) iter.remove();

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

    private void obsluzWrogieStrzaly() {
//        System.out.println("DRop obsluzWrogieStrzaly, "+wrogieStrzaly.size());
        Iterator<Rectangle> iter = wrogieStrzaly.iterator();
        while (iter.hasNext()) {
            Rectangle wrogiStrzal = iter.next();
            wrogiStrzal.y -= 200 * Gdx.graphics.getDeltaTime();
            if (wrogiStrzal.y + 10 < 0) iter.remove();

            if (wrogiStrzal.overlaps(rakieta)) {
                koniecGry = true;
            }
        }
    }

    @Override
    public void dispose() {
        // dispose of all the native resources
        dropImage.dispose();
        bucketImage.dispose();
        //dropSound.dispose();
        //	rainMusic.dispose();
        batch.dispose();
    }
}