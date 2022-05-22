package com.gabriel.game;

import java.awt.event.KeyEvent;

import com.gabriel.engine.GameContainer;
import com.gabriel.engine.Renderer;

public class Player extends GameObject {

	private int tileX;
	private int tileY;

	private float offX;
	private float offY;

	private float speed = 100;

	private float fallSpeed = 10;
	private float jump = -4;
	private boolean ground = false;

	private float fallDistance = 0;

	public Player(int posX, int posY) {

		this.tag = "player";
		this.tileX = posX;
		this.tileY = posY;
		this.offX = 0;
		this.offY = 0;
		this.posX = posX * GameManager.TS;
		this.posY = posY * GameManager.TS;
		this.width = GameManager.TS;
		this.height = GameManager.TS;
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {

		// #region Left and Right

		if (gc.getInput().isKey(KeyEvent.VK_D)) {

			if (gm.getCollision(tileX + 1, tileY)
					|| gm.getCollision(tileX + 1, tileY + (int) Math.signum((int) offY))) {

				if (offX < 0) {

					offX += dt * speed;

					if (offX > 0) {
						offX = 0;
					}

				} else {
					offX = 0;
				}
			} else {

				offX += dt * speed;

			}
		}

		if (gc.getInput().isKey(KeyEvent.VK_A)) {

			if (gm.getCollision(tileX - 1, tileY)
					|| gm.getCollision(tileX - 1, tileY + (int) Math.signum((int) offY))) {

				if (offX > 0) {
					offX -= dt * speed;

					if (offX < 0) {
						offX = 0;
					}

				} else {
					offX = 0;
				}

			} else {
				offX -= dt * speed;
			}

		}

		// To fix jump in the air

		// if (fallDistance > 0) {
		// if ((gm.getCollision(tileX, tileY + 1) || gm.getCollision(tileX + (int)
		// Math.signum((int) offX), tileY + 1))
		// && offY > 0) {
		// fallDistance = 0;
		// offY = 0;
		// ground = true;
		// } else
		// ground = false;
		// }

		// #endregion End of Left and Right

		// #region Jump and Gravity

		fallDistance += dt * fallSpeed;

		if (gc.getInput().isKey(KeyEvent.VK_W) && ground) {

			fallDistance = jump;
			ground = false;
		}

		offY += fallDistance;

		if (fallDistance < 0) {

			if ((gm.getCollision(tileX, tileY - 1) || gm.getCollision(tileX + (int) Math.signum((int) offX), tileY - 1))
					&& offY < 0) {
				fallDistance = 0;
				offY = 0;
			}

		}

		if (fallDistance > 0) {

			// System.err.println(gm.getCollision(tileX, tileY + 1));

			if ((gm.getCollision(tileX, tileY + 1) || gm.getCollision(tileX + (int) Math.signum((int) offX), tileY + 1))
					&& offY > 0) {
				fallDistance = 0;
				offY = 0;
				ground = true;
			}
		}

		// #endregion End of Jump and Gravity

		// Final position

		if (offY > GameManager.TS / 2) {

			tileY++;
			offY -= GameManager.TS;
		}

		if (offY < -GameManager.TS / 2) {

			tileY--;
			offY += GameManager.TS;
		}

		if (offX > GameManager.TS / 2) {

			tileX++;
			offX -= GameManager.TS;
		}

		if (offX < -GameManager.TS / 2) {

			tileX--;
			offX += GameManager.TS;
		}

		posX = tileX * GameManager.TS + offX;
		posY = tileY * GameManager.TS + offY;

		// Shooting
		if (gc.getInput().isKey(KeyEvent.VK_UP)) {
			gm.addObject(new Bullet(tileX, tileY, offX + width / 2, offY + height / 2, 0));
		}
		if (gc.getInput().isKey(KeyEvent.VK_RIGHT)) {
			gm.addObject(new Bullet(tileX, tileY, offX + width / 2, offY + height / 2, 1));
		}
		if (gc.getInput().isKey(KeyEvent.VK_DOWN)) {
			gm.addObject(new Bullet(tileX, tileY, offX + width / 2, offY + height / 2, 2));
		}
		if (gc.getInput().isKey(KeyEvent.VK_LEFT)) {
			gm.addObject(new Bullet(tileX, tileY, offX + width / 2, offY + height / 2, 3));
		}

	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawFillRect((int) posX, (int) posY, width, height, 0xff00ff00);
	}

}
