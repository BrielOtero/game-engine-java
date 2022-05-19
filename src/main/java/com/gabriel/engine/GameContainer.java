package com.gabriel.engine;

//Java Runnable is an interface used to execute code on a concurrent thread.
public class GameContainer implements Runnable {

	// The Java Virtual Machine allows an application to have multiple threads of
	// execution running concurrently.
	private Thread thread;

	// Set if the game is running.
	private boolean running = false;

	// Limit the fps to 60.
	private final double update_cap = 1.0 / 60.0;

	public GameContainer() {

	}

	public void start() {
		thread = new Thread(this);
		thread.run();

	}

	public void stop() {

	}

	public void run() {

		running = true;

		double firstTime = 0;


		/** 
		 * System. The System class contains several useful class fields and methods.
		 * 
		 * NanoTime. Returns the current value of the running Java Virtual Machine's
		 * high-resolution time source, in nanoseconds
		 */
		double lastTime = System.nanoTime()/1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;

		while (running) {
			firstTime=System.nanoTime()/1000000000.0;
		}
	}

	private void dispose() {

	}

}
