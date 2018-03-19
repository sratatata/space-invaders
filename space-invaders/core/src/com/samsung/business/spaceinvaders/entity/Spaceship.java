package com.samsung.business.spaceinvaders.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.samsung.business.spaceinvaders.manager.GraphicsManager;

import java.util.ArrayList;
import java.util.List;


public class Spaceship {

	private final GraphicsManager.Graphics graphics;
	protected Rectangle position;
	private List<OnSpaceshipHit> spaceshipHitListeners = new ArrayList<>();

	public Spaceship(GraphicsManager.Graphics graphics) {
		this.graphics = graphics;
	}

	public void render(SpriteBatch batch, float animationTime) {
		TextureRegion spaceshipFrame = graphics.frameToRender(animationTime);
		batch.draw(spaceshipFrame, position.x, position.y);
	}

	public boolean isHit(Shoot shoot) {
		boolean isHit = shoot.position().overlaps(this.position);
		if(isHit){
			notifyAllOnSpaceshipHit();
		}
		return isHit;
	}

	public Rectangle position() {
		return this.position;
	}

	public void registerOnSpaceshipHit(OnSpaceshipHit onSpaceshipHit){
		spaceshipHitListeners.add(onSpaceshipHit);
	}


	private void notifyAllOnSpaceshipHit() {
		for (OnSpaceshipHit g : spaceshipHitListeners) {
			g.onSpaceshipHit();
		}
	}

	public interface OnSpaceshipHit {
		void onSpaceshipHit();
	}
}
