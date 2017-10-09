package com.samsung.business.spaceinvaders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.samsung.business.spaceinvaders.SpaceInvaders;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Space Invaders";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new SpaceInvaders(), config);
	}
}