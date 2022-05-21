package com.gabriel.game;

import java.awt.event.KeyEvent;

import com.gabriel.engine.AbstractGame;
import com.gabriel.engine.GameContainer;
import com.gabriel.engine.Renderer;
import com.gabriel.engine.audio.SoundClip;
import com.gabriel.engine.gfx.Image;
import com.gabriel.engine.gfx.ImageTile;

public class GameManager extends AbstractGame {

	private Image image3;
	private Image image2;
	private ImageTile image;
	private SoundClip clip;

	public GameManager() {

		image3 = new Image("/res/img/transparent.png");
		image3.setAlpha(true);
		image2 = new Image("/res/img/explosion.png");
		image = new ImageTile("/res/img/transparent.png", 16, 16);
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

		// r.drawImage(image3, gc.getInput().getMouseX(), gc.getInput().getMouseY());
		r.drawImage(image2, 10, 10);
		r.drawImageTile(image, gc.getInput().getMouseX() - 8, gc.getInput().getMouseY() - 8, 1, 1);
		// r.drawFillRect(gc.getInput().getMouseX()-200, gc.getInput().getMouseY()-200,
		// 400, 400, 0xffffccff);
	}

	public static void main(String[] args) {

		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(3f);
		gc.start();
	}

}
