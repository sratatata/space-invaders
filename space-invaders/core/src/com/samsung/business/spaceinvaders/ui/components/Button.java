package com.samsung.business.spaceinvaders.ui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;

/**
 * Created by lb_lb on 25.02.18.
 */

public class Button implements Component {

    public static final int MAX_POINTERS = 5;

    private OnClickListener listener;
    private final GraphicsManager.Graphics background;
    private final Camera camera;
    private final int x;
    private final int y;
    private final int radius;
    private Vector3 touchPos = new Vector3();

    public Button(int x, int y, int radius, GraphicsManager.Graphics background, Camera camera) {
        this.background = background;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.camera = camera;
    }

    @Override
    public void render(SpriteBatch batch, float animationTime) {
        TextureRegion frame = background.frameToRender(animationTime);
        batch.draw(frame, x, y);
        if (checkClick()){
            handleClickListener();
        }
    }

    public boolean checkClick(){
        for (int pointer = 0; pointer < MAX_POINTERS; pointer++){
            if (checkClick(pointer)){
                return true;
            }
        }
        return false;
    }

    private boolean checkClick(int pointer){
        if(Gdx.input.isTouched(pointer) || Gdx.input.isButtonPressed(0)) {
            touchPos.set(Gdx.input.getX(pointer), Gdx.input.getY(pointer), 0);
            camera.unproject(touchPos);
            if ((x - touchPos.x)*(x - touchPos.x)+(y - touchPos.y)*(y - touchPos.y) <= radius*radius) {
                return true;
            }
        }
        return false;
    }

    public void handleClickListener() {
        if (listener != null){
            listener.onClick();
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }
}
