package com.gabriel.game;

import java.awt.event.KeyEvent;

import com.gabriel.engine.AbstractGame;
import com.gabriel.engine.GameContainer;
import com.gabriel.engine.Renderer;
import com.gabriel.engine.audio.SoundClip;
import com.gabriel.engine.gfx.Image;
import com.gabriel.engine.gfx.ImageTile;

public class GameManager extends AbstractGame {

	private Image image2;
	private Image image;
	private SoundClip clip;

	public GameManager() {

		image2 = new Image("/res/img/white.png");
		image = new Image("/res/img/light.png");
		image.setAlpha(true);
		clip = new SoundClip("/res/audio/test.wav");

	}

	@Override
	public void update(GameContainer gc, float dt) {

		// #region Test
		if (gc.getInput().isKeyDown(KeyEvent.VK_A)) {
			System.err.println("A is pressed");
			clip.loop();
			clip.play();
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

		for (int x = 0; x < image.getW(); x++) {

			for (int y = 0; y < image.getH(); y++) {
				r.setLightMap(x, y, image.getP()[x + y * image.getW()]);
			}

		}
		r.setzDepth(1);

		// r.drawImage(image2, 10, 10);
		r.drawImage(image2, gc.getInput().getMouseX() - 8, gc.getInput().getMouseY() - 8);
	}

	public static void main(String[] args) {

		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(3f);
		gc.start();
	}

}
