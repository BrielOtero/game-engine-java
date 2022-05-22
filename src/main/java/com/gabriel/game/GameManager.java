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

	public static final int TS = 16;

	private Image levelImage = new Image("/res/img/lev.png");
	private Image skyImage = new Image("/res/img/sky.png");

	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Camera camera;

	private boolean[] collision;
	private int levelW;
	private int levelH;

	//private SoundClip backSong = new SoundClip("/res/audio/test.wav");

	public GameManager() {
		objects.add(new Player(6, 4));
		loadLevel("/res/img/levelMap.png");
		camera = new Camera("player");
		// levelImag.setAlpha(true);
		levelImage.setLightBlock((int)Light.FULL);
	}

	@Override
	public void init(GameContainer gc) {

		gc.getRenderer().setAmbientColor(-1);

		// backSong.setVolume(-10);
		// backSong.play();
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
		
		camera.render(r);
		// System.err.println(levelImage..get);
		r.drawImage(skyImage, 0, 0);
		r.drawImage(levelImage, 0, 0);

		// for (int y = 0; y < levelH; y++) {

		// 	for (int x = 0; x < levelW; x++) {

		// 		if (collision[x + y * levelW]) {

		// 			r.drawFillRect(x * TS, y * TS, TS, TS, 0xff0f0f0f);

		// 		} else {
		// 			r.drawFillRect(x * TS, y * TS, TS, TS, 0xfff9f9f9);

		// 		}
		// 	}
		// }

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
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(3f);
		gc.start();
	}

}
