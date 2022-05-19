package com.gabriel.game;

import java.awt.event.KeyEvent;

import com.gabriel.engine.AbstractGame;
import com.gabriel.engine.GameContainer;
import com.gabriel.engine.Renderer;
import com.gabriel.engine.gfx.Image;

public class GameManager extends AbstractGame {

	private Image image;

	public GameManager() {



		image= new Image("/test.png");
	}

	@Override
	public void update(GameContainer gc, float dt) {

		// #region Test
		if (gc.getInput().isKeyDown(KeyEvent.VK_A)) {
			System.err.println("A is pressed");
		}
		// #endregion
	}

	@Override
	public void render(GameContainer gc, Renderer r) {

		r.drawImage(image, gc.getInput().getMouseX(), gc.getInput().getMouseY());
	}

	public static void main(String[] args) {

		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

}
