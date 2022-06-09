package com.gabriel.game;

import com.gabriel.engine.GameContainer;
import com.gabriel.engine.Renderer;

public class Camera {

	private float offX;
	private float offY;

	private String targetTag;
	private GameObject target = null;

	public Camera(String tag) {
		this.targetTag = tag;

	}

	public void update(GameContainer gc, GameManager gm, float dt) {

		if (target == null) {

			target = gm.getObject(targetTag);

		}

		if (target == null) {
			return;
		}

		float targetX = (target.getPosX() + target.getWidth() / 2) - gc.getWidth() / 2;
		float targetY = (target.getPosY() + target.getHeight() / 2) - gc.getHeight() / 2;

		// Smooth Camera
		 offX -= dt * (offX - targetX) * 1;
		 offY -= dt * (offY - targetY+100) * 1;

		// offX = targetX;
		// offY = targetY-155;

		if (offX < 0){
			offX = 0;
		}

		if (offY < 0){
			offY = 0;
		}
		if (offX + gc.getWidth() > gm.getLevelW() * GameManager.TS){
			offX = gm.getLevelW() * GameManager.TS - gc.getWidth();
		}

		if (offY + gc.getHeight() > gm.getLevelH() * GameManager.TS){
			offY = gm.getLevelH() * GameManager.TS - gc.getHeight();
		}

	

	}

	public void render(Renderer r) {

		r.setCamX((int) offX);
		r.setCamY((int) offY);
	}

	// Getters & Setters

	public float getOffX() {
		return offX;
	}

	public void setOffX(float offX) {
		this.offX = offX;
	}

	public float getOffY() {
		return offY;
	}

	public void setOffY(float offY) {
		this.offY = offY;
	}

	public String getTargetTag() {
		return targetTag;
	}

	public void setTargetTag(String targetTag) {
		this.targetTag = targetTag;
	}

	public GameObject getTarget() {
		return target;
	}

	public void setTarget(GameObject target) {
		this.target = target;
	}

}
