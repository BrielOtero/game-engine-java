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
	private Image inter = new Image("/res/img/inter.png");
	private Image block = new Image("/res/img/block.png");
	private Light light;

	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private Camera camera;

	private boolean[] collision;
	private int[] textures;
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
		inter.setLightBlock(Light.FULL);
		block.setLightBlock(Light.FULL);
	}

	@Override
	public void init(GameContainer gc) {

		gc.getRenderer().setAmbientColor(0xffb4b4b4);
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

		// Profundidad
		// r.setzDepth(0);

		camera.render(r);
		r.drawImage(levelTexture, 0, 0);

		// Draw textures with light
		for (int y = 0; y < levelH; y++) {
			for (int x = 0; x < levelW; x++) {
				if (y <= 9) {
					if (textures[x + y * levelW] == 1) {
						r.drawImage(inter, x * TS, y * TS);
					}
					if (textures[x + y * levelW] == 2) {
						r.drawImage(block, x * TS, y * TS);
					}
				}
			}
		}


		if (gc.getInput().isKey(KeyEvent.VK_B)) {
			gc.getRenderer().setAmbientColor(0xffb4b4b4);
		}
		if (gc.getInput().isKey(KeyEvent.VK_N)) {
			gc.getRenderer().setAmbientColor(0xffff0000);
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
		textures = new int[levelW * levelH];

		for (int y = 0; y < levelImage.getH(); y++) {

			for (int x = 0; x < levelImage.getW(); x++) {

				if (levelImage.getP()[x + y * levelImage.getW()] == 0xff000000
						|| levelImage.getP()[x + y * levelImage.getW()] == 0xffff0000
						|| levelImage.getP()[x + y * levelImage.getW()] == 0xff0000ff) {

					if (levelImage.getP()[x + y * levelImage.getW()] == 0xffff0000) {
						textures[x + y * levelImage.getW()] = 1;
					}

					if (levelImage.getP()[x + y * levelImage.getW()] == 0xff0000ff) {
						textures[x + y * levelImage.getW()] = 2;
					}

					collision[x + y * levelImage.getW()] = true;

				} else {

					collision[x + y * levelImage.getW()] = false;
					textures[x + y * levelImage.getW()] = 0;
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
