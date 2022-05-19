package com.gabriel.engine;

import java.awt.image.DataBufferInt;

import com.gabriel.engine.gfx.Image;

public class Renderer {

	// Pixel Width & Pixel Height
	private int pW;
	private int pH;

	// Pixels
	private int[] p;

	public Renderer(GameContainer gc) {

		pW = gc.getWidth();
		pH = gc.getHeight();
		p = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();

	}

	public void clear() {

		for (int i = 0; i < p.length; i++) {
			p[i] = 0;

		}
	}

	// 0xffff00ff is a Pink color that not render. Let's use this color as invisible
	// color.
	public void setPixel(int x, int y, int value) {

		if ((x < 0 || x >= pW || y < 0 || y >= pH) || value == 0xffff00ff) {

			return;
		}

		p[x + y * pW] = value;
	}

	public void drawImage(Image image, int offX, int offY) {

		for (int y = 0; y < image.getH(); y++) {

			for (int x = 0; x < image.getW(); x++) {

				setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
			}
		}
	}

}
