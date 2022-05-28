package com.gabriel.game;

import java.awt.Button;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.gabriel.engine.AbstractGame;
import com.gabriel.engine.GameContainer;
import com.gabriel.engine.Renderer;
import com.gabriel.engine.audio.SoundClip;
import com.gabriel.engine.gfx.Image;
import com.gabriel.engine.gfx.ImageTile;
import com.gabriel.engine.gfx.Light;

public class GameManager extends AbstractGame {

	public static final int TS = 32;

	private Image levelTexture = new Image("/res/img/levelMapTexture.png");
	private Light light;

	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Camera camera;

	private boolean[] collision;
	private int levelW;
	private int levelH;

	private SoundClip backSong = new SoundClip("/res/audio/music.wav");

	public int getLevelW() {
		return levelW;
	}

	public int getLevelH() {
		return levelH;
	}

	public GameManager() {
		objects.add(new Player(15, 0));
		loadLevel("/res/img/levelMap.png");
		camera = new Camera("player");
		light = new Light(200, 0xffffc72a);
		levelTexture.setLightBlock(Light.NONE);
	}

	@Override
	public void init(GameContainer gc) {

		// gc.getRenderer().setAmbientColor(0xff000000);

		// backSong.setVolume(-10);
		backSong.play();
	}

	@Override
	public void update(GameContainer gc, float dt) {

		for (int i = 0; i < objects.size(); i++) {

			objects.get(i).update(gc, this, dt);

			if (objects.get(i).isDead()) {

				objects.remove(i);
				i--;
			}
		}

		camera.update(gc, this, dt);

	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		// r.drawText(String.format("%s", gc.getFps()), 0, 0, 0xff00ffff);
		
		// r.setzDepth(0);
		camera.render(r);
		 r.drawImage(levelTexture, 0, 0);
		 

		// for (int y = 0; y < levelH; y++) {

		// 	for (int x = 0; x < levelW; x++) {

		// 		if (collision[x + y * levelW]) {

		// 			r.drawFillRect(x * TS, y * TS, TS, TS, 0xff0f0f0f);

		// 		} else {
		// 			r.drawFillRect(x * TS, y * TS, TS, TS, 0xfff9f9f9);

		// 		}
		// 	}
		// }
		
		//if ((int) objects.get(0).posX > 200) {
			if (gc.getInput().isKey(KeyEvent.VK_B)) {
	
			gc.getRenderer().setAmbientColor(0xff6b6b6b);
		}
		if (gc.getInput().isKey(KeyEvent.VK_N)) {
			gc.getRenderer().setAmbientColor(0xff000000);
		}

		if (gc.getInput().isKey(KeyEvent.VK_M)) {
			gc.getRenderer().setAmbientColor(0xffffffff);
		}

		for (GameObject obj : objects) {
			obj.render(gc, r);
		}

	}

	public void loadLevel(String path) {

		Image levelImage = new Image(path);

		levelW = levelImage.getW();
		levelH = levelImage.getH();
		collision = new boolean[levelW * levelH];

		for (int y = 0; y < levelImage.getH(); y++) {

			for (int x = 0; x < levelImage.getW(); x++) {

				if (levelImage.getP()[x + y * levelImage.getW()] == 0xff000000) {

					collision[x + y * levelImage.getW()] = true;

				} else {

					collision[x + y * levelImage.getW()] = false;
				}
			}

		}
	}

	public void addObject(GameObject object) {
		objects.add(object);
	}

	public GameObject getObject(String tag) {

		for (int i = 0; i < objects.size(); i++) {

			if (objects.get(i).getTag().equals(tag)) {

				return objects.get(i);
			}
		}

		return null;
	}

	public boolean getCollision(int x, int y) {

		if (x < 0 || x >= levelW || y < 0 || y >= levelH) {

			return true;
		}

		return collision[x + y * levelW];
	}

	public static void main(String[] args) {

		GameContainer gc = new GameContainer(new GameManager());
		gc.setWidth(900);
		gc.setHeight(400);
		gc.setScale(2f);
		gc.start();
	}

}
