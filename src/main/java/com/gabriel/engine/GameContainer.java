package com.gabriel.engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

//Java Runnable is an interface used to execute code on a concurrent thread.
public class GameContainer implements Runnable {

	// The Java Virtual Machine allows an application to have multiple threads of
	// execution running concurrently.
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;

	// Set if the game is running.
	private boolean running = false;

	// Limit the fps to 60.
	private final double update_cap=1.0/60.0;

	private int width = 320;
	private int height = 240;
	private float scale = 3f;
	private String title = "GabrEngine v0.0.1";

	public GameContainer(AbstractGame game) {
		this.game = game;
	}

	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		thread = new Thread(this);
		thread.run();

	}

	public GameContainer() {

	}

	public void stop() {

	}

	public void run() {

		running = true;

		boolean render = false;

		double firstTime = 0;

		/**
		 * System. The System class contains several useful class fields and methods.
		 * 
		 * NanoTime. Returns the current value of the running Java Virtual Machine's
		 * high-resolution time source, in nanoseconds
		 */
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;

		double frameTime = 0;
		int frames = 0;
		int fps = 0;

		while (running) {

			// false: Limit fps ON
			render = true;

			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;

			unprocessedTime += passedTime;
			frameTime += passedTime;

			while (unprocessedTime >= update_cap) {
				unprocessedTime -= update_cap;
				render = true;

				game.update(this, (float) update_cap);

				// #region Test

				// System.err.println("Update");

				// if(input.isKey(KeyEvent.VK_A)){
				// System.err.println("A is pressed");
				// }

				// if(input.isKeyDown(KeyEvent.VK_A)){
				// System.err.println("A is pressed Down");
				// }

				// if(input.isKeyUp(KeyEvent.VK_A)){
				// System.err.println("A is pressed Up");
				// }

				// if(input.isButton(MouseEvent.BUTTON1)){
				// System.err.println("Button 1 is pressed");
				// }

				// if(input.isButtonDown(MouseEvent.BUTTON1)){
				// System.err.println("Button 1 is pressed Down");
				// }

				// if(input.isButtonUp(MouseEvent.BUTTON1)){
				// System.err.println("Button 1 is pressed Up");
				// }

				// The scroll test alone
				// System.err.println("The scroll is:"+input.getScroll());

				// System.err.println("X: "+input.getMouseX()+"Y: "+input.getMouseY());

				// #endregion

				input.update();

				if (frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
					// System.err.println("FPS: " + fps);
				}
			}

			if (render) {
				renderer.clear();
				game.render(this, renderer);
				renderer.process();
				renderer.drawText("FPS:" + fps, 0, 0, 0xff00ffff);
				window.update();
				frames++;

			} else {

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		dispose();
	}

	private void dispose() {

	}

	// Getters & Setters

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

}
