package com.samsung.business.spaceinvaders.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by lb_lb on 27.01.18.
 */
public class TouchInput {
    private static final int MIDDLE_Y = DisplayInfo.getHeight()/2;
    private static final int MIDDLE_X = DisplayInfo.getWidth()/2;
    private static final int TERTIO_X = DisplayInfo.getWidth()/3;

    private final Camera camera;
    private Vector3 touchPos = new Vector3();

    public TouchInput(Camera camera){
        this.camera = camera;
    }

    public boolean left() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            return touchPos.x <= TERTIO_X;
        }
        return false;
    }

    public boolean right() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            return touchPos.x > 2*TERTIO_X;
        }
        return false;
    }

    public boolean fire() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            return touchPos.x > TERTIO_X && touchPos.x < 2 * TERTIO_X;
        }
        return false;
    }

    public boolean exit() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            return touchPos.y > MIDDLE_Y && Gdx.input.isTouched(0)
                    && Gdx.input.isTouched(1);
        }
        return false;
    }

    public  boolean start() {
        return Gdx.input.isTouched();
    }
}
