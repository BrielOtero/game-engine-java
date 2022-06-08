package com.gabriel.game;

import com.gabriel.engine.GameContainer;
import com.gabriel.engine.Renderer;
import com.gabriel.engine.gfx.*;

public class Bullet extends GameObject {

	private int tileX;
	private int tileY;

	private float offX;
	private float offY;

	private int direction;
	private float speed = 300;
	private boolean up = false;
	private int dChange = 0;
	private Light lightFire;

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
			dChange=0;
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

		lightFire = new Light(20, 0xffff0000);
		r.drawFillRect((int) posX - 2, (int) posY - 2, 7, 7, 0xffff0000);
		r.drawLight(lightFire, (int) posX - 2, (int) posY - 2);
	}

}
