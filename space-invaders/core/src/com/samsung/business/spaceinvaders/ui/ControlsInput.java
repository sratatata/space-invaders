package com.samsung.business.spaceinvaders.ui;

import com.badlogic.gdx.Gdx;
import com.samsung.business.spaceinvaders.ui.components.Button;
import com.samsung.business.spaceinvaders.ui.components.Stick;

/**
 * Created by mudzio on 2018-02-27.
 */

public class ControlsInput implements GameInputMethod, Stick.MoveListener{

	private static final float THRESHOLD = 0.1f;

	private final Button fireButton;
	private final Stick moveStick;

	private boolean isStickLeft;
	private boolean isStickRight;


	public ControlsInput(Button fireButton, Stick moveStick){
		this.fireButton = fireButton;
		this.moveStick = moveStick;
		moveStick.setOnMoveListener(this);
	}

	@Override
	public boolean left() {
		return moveStick.checkClick() && isStickLeft;
	}

	@Override
	public boolean right() {
		return moveStick.checkClick() && isStickRight;
	}

	@Override
	public boolean fire() {
		return fireButton.checkClick();
	}

	@Override
	public boolean exit() {
		 return Gdx.input.isTouched(0) && Gdx.input.isTouched(1)
				&& Gdx.input.isTouched(2);
	}

	@Override
	public boolean up() {
		return false;
	}

	@Override
	public boolean down() {
		return false;
	}

	@Override
	public boolean select() {
		return false;
	}

	@Override
	public void onStickMoved(float deltaAxisX, float deltaAxisY) {
		isStickLeft = false;
		isStickRight = false;
		if (Math.abs(deltaAxisX) > THRESHOLD){
			isStickLeft = deltaAxisX < 0;
			isStickRight = deltaAxisX > 0;
		}
	}
}
