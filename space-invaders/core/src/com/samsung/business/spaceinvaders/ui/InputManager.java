package com.samsung.business.spaceinvaders.ui;

/**
 * Created by lb_lb on 27.01.18.
 */
public class InputManager {

    private LeftListener leftListener;
    private RightListener rightListener;
    private FireListener fireListener;
    private ExitListener exitListener;
    private SelectListener selectListener;

    private GameInputMethod gameInputMethod;

    public InputManager(GameInputMethod gameInputMethod) {
        this.gameInputMethod = gameInputMethod;
        leftListener = new LeftListener() {
            @Override
            public void navigateLeft() {
                //do nothing
            }
        };
        rightListener = new RightListener() {
            @Override
            public void navigateRight() {
                //do nothing
            }
        };
        fireListener = new FireListener() {
            @Override
            public void fire() {
                //do nothing
            }
        };

        exitListener = new ExitListener() {
            @Override
            public void exit() {
                //do nothing
            }
        };

        selectListener = new SelectListener() {
            @Override
            public void select() {
                //do nothing
            }
        };
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

    private void select() {
        if(gameInputMethod.select())
            selectListener.select();
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
        select();
    }

    public void setSelectListener(SelectListener listener) {
        this.selectListener = listener;
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

    public interface SelectListener{
        void select();
    }
}
