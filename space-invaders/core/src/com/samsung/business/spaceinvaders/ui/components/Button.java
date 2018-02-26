package com.samsung.business.spaceinvaders.ui.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;
import com.samsung.business.spaceinvaders.ui.InputManager;

/**
 * Created by lb_lb on 25.02.18.
 */

public class Button implements Component {
    private OnClickListener listener;
    private final GraphicsManager.Graphics graphics;

    private int x = 0, y = 0;

    public Button(GraphicsManager.Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void render(SpriteBatch batch, float animationTime) {
        TextureRegion frame = graphics.frameToRender(animationTime);
        batch.draw(frame, x, y);
        checkClick();
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void checkClick() {
        //TODO calculate click radius from center point of button
        if (x + 80 >= Gdx.graphics.getWidth()-Gdx.input.getX()
                && y + 80 >= Gdx.graphics.getHeight()- Gdx.input.getY()
                && (Gdx.input.isButtonPressed(0) || Gdx.input.isTouched())
                ) {
            listener.onClick();
        }
    }
}
