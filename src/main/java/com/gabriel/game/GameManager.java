package com.gabriel.game;

import java.awt.event.KeyEvent;

import com.gabriel.engine.AbstractGame;
import com.gabriel.engine.GameContainer;
import com.gabriel.engine.Renderer;
import com.gabriel.engine.gfx.Image;
import com.gabriel.engine.gfx.ImageTile;

public class GameManager extends AbstractGame {

	private ImageTile image;

	public GameManager() {

		image = new ImageTile("/res/explosion.png", 16, 16);

	}

	@Override
	public void update(GameContainer gc, float dt) {

		// #region Test
		if (gc.getInput().isKeyDown(KeyEvent.VK_A)) {
			System.err.println("A is pressed");
		}
		// #endregion

		temp += dt * 20;

		if (temp > 3) {
			temp = 0;
		}
	}

	float temp = 0;

	@Override
	public void render(GameContainer gc, Renderer r) {

		r.drawImageTile(image, gc.getInput().getMouseX() - 16, gc.getInput().getMouseY() - 16, (int) temp, 0);
	}

	public static void main(String[] args) {

		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

}
