package com.gabriel.game;

import com.gabriel.engine.GameContainer;
import com.gabriel.engine.Renderer;
import com.gabriel.engine.gfx.*;

public class Bullet extends GameObject {

	private int tileX;
	private int tileY;

	private float offX;
	private float offY;

	private Light light;

	private Image ball = new Image("/res/img/ball.png");

	private int direction;
	private float speed = 300;
	private boolean up = false;
	private int dChange = 0;

	public Bullet(int tileX, int tileY, float offX, float offY, int direction) {

		this.direction = direction;
		this.tileX = tileX;
		this.tileY = tileY;
		this.offX = offX;
		this.offY = offY;
		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;

	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {

		switch (direction) {
			case 0:
				offX += speed * dt;

				if (up) {
					offY -= speed * dt;
					dChange++;
				} else {
					offY += speed * dt;
				}
				break;
			case 1:
				offX -= speed * dt;

				if (up) {
					offY -= speed * dt;
					dChange++;
				} else {
					offY += speed * dt;
				}
				break;
		}

		// Final position

		if (offY > GameManager.TS) {

			tileY++;
			offY -= GameManager.TS;
		}

		if (offY < 0) {

			tileY--;
			offY += GameManager.TS;
		}

		if (offX > GameManager.TS) {

			tileX++;
			offX -= GameManager.TS;
		}

		if (offX < 0) {

			tileX--;
			offX += GameManager.TS;
		}

		if (gm.getCollision(tileX, tileY)) {
			// this.dead = true;
			up = true;
			dChange = 0;
		}
		if (dChange > 4 && up) {
			dChange = 0;
			up = up ? false : true;
		}

		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;

	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		light = new Light(10, 0xffffff00);
		r.drawImage(ball, (int) posX -2, (int) posY -2);
		r.drawLight(light, (int) posX , (int) posY);
	}

}
