package com.samsung.business.spaceinvaders.ui;

/**
 * Created by lb_lb on 27.01.18.
 */
public class InputManager {

    private LeftListener leftListener;
    private RightListener rightListener;
    private FireListener fireListener;
    private ExitListener exitListener;

    private GameInputMethod gameInputMethod;

    public InputManager(GameInputMethod gameInputMethod) {
        this.gameInputMethod = gameInputMethod;
    }

    public void left(){
        if(gameInputMethod.left())
            leftListener.navigateLeft();
    }

    public void right(){
        if(gameInputMethod.right())
            rightListener.navigateRight();
    }

    public void fire(){
        if(gameInputMethod.fire())
            fireListener.fire();
    }

    public void exit(){
        if(gameInputMethod.exit())
            exitListener.exit();
    }

    public void setLeftListener(LeftListener leftListener) {
        this.leftListener = leftListener;
    }

    public void setRightListener(RightListener rightListener) {
        this.rightListener = rightListener;
    }

    public void setFireListener(FireListener fireListener) {
        this.fireListener = fireListener;
    }

    public void setExitListener(ExitListener exitListener) {
        this.exitListener = exitListener;
    }

    public void update() {
        left();
        right();
        fire();
        exit();

    }

    public interface LeftListener{
        void navigateLeft();
    }

    public interface RightListener{
        void navigateRight();
    }

    public interface FireListener{
        void fire();
    }

    public interface ExitListener{
        void exit();
    }
}
