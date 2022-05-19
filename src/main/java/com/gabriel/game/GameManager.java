package com.gabriel.game;

import java.awt.event.KeyEvent;
import java.net.URISyntaxException;

import com.gabriel.engine.AbstractGame;
import com.gabriel.engine.GameContainer;
import com.gabriel.engine.Renderer;
import com.gabriel.engine.gfx.Image;

public class GameManager extends AbstractGame {

	private Image image;

	public GameManager() {

		try {
			System.err.println(GameManager.class.getResource("/img/test.png").toURI().getPath());
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			image = new Image(GameManager.class.getResource("/img/test.png").toURI().getPath());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
