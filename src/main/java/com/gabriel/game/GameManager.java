package com.gabriel.game;

import java.awt.event.KeyEvent;

import com.gabriel.engine.AbstractGame;
import com.gabriel.engine.GameContainer;
import com.gabriel.engine.Renderer;

public class GameManager extends AbstractGame {

	public GameManager() {
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
	}

	public static void main(String[] args) {

		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

}
