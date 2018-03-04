package com.samsung.business.spaceinvaders.ui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;

import static com.samsung.business.spaceinvaders.ui.components.Button.MAX_POINTERS;

/**
 * Created by lb_lb on 25.02.18.
 */

public class Stick implements Component {
    private MoveListener moveListener;
    private final GraphicsManager.Graphics background;
    private final GraphicsManager.Graphics clickIndicator;
    private final Camera camera;

    private Vector3 touchPos = new Vector3();

    private final int x;
    private final int y;
    private final int radius;
    private final int radiusIndicator;

    public Stick(int x, int y, int radius, int radiusIndicator, GraphicsManager.Graphics background, GraphicsManager.Graphics clickIndicator, Camera camera) {
        this.background = background;
        this.clickIndicator = clickIndicator;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.camera = camera;
        this.radiusIndicator = radiusIndicator;
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        TextureRegion frameBackground = background.frameToRender(delta);
        batch.draw(frameBackground, x-radius, y-radius, this.radius * 2, this.radius * 2);
        if (checkClick()){
            TextureRegion frameTouch = clickIndicator.frameToRender(delta);
            batch.draw(frameTouch, touchPos.x- radiusIndicator, touchPos.y-radiusIndicator, this.radiusIndicator * 2, this.radiusIndicator * 2);
            handleClickListener();
        }
    }

    public final void setOnMoveListener(MoveListener moveListener) {
        this.moveListener = moveListener;
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
        if(Gdx.input.isTouched(pointer)) {
            touchPos.set(Gdx.input.getX(pointer), Gdx.input.getY(pointer), 0);
            camera.unproject(touchPos);
            if ((x - touchPos.x)*(x - touchPos.x)+(y - touchPos.y)*(y - touchPos.y) <= radius*radius) {
                return true;
            }
        }
        return false;
    }


    public void handleClickListener() {
        if (moveListener != null){
            moveListener.onStickMoved((touchPos.x - x)/(float)radius, (touchPos.y - y)/(float)radius);
        }
    }

    public interface MoveListener {
        void onStickMoved(float deltaAxisX, float deltaAxisY);
    }
}
