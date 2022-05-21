package com.gabriel.engine;

import java.awt.image.DataBufferInt;

import com.gabriel.engine.gfx.Font;
import com.gabriel.engine.gfx.Image;
import com.gabriel.engine.gfx.ImageTile;

public class Renderer {

	// Pixel Width & Pixel Height
	private int pW;
	private int pH;

	// Pixels
	private int[] p;

	private Font font = Font.STANDARD;

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
	// color. In RGB is (255,0,255).
	public void setPixel(int x, int y, int value) {

		if ((x < 0 || x >= pW || y < 0 || y >= pH) || value == 0xffff00ff) {

			return;
		}

		p[x + y * pW] = value;
	}

	public void drawText(String text, int offX, int offY, int color) {

		text = text.toUpperCase();
		int offset = 0;

		for (int i = 0; i < text.length(); i++) {

			int unicode = text.codePointAt(i) - 32;

			for (int y = 0; y < font.getFontImage().getH(); y++) {

				for (int x = 0; x < font.getWidths()[unicode]; x++) {

					if (font.getFontImage().getP()[(x + font.getOffsets()[unicode])+ y * font.getFontImage().getW()] == 0xffffffff) {
						setPixel(x + offX + offset, y + offY, color);
					}
				}
			}
			offset += font.getWidths()[unicode];
		}

	}

	public void drawImage(Image image, int offX, int offY) {

		// Don't Render Code.
		if (offX < -image.getW()) {
			return;
		}

		if (offY < -image.getH()) {
			return;
		}

		if (offX >= pW) {
			return;
		}

		if (offY >= pH) {
			return;
		}

		int newX = 0;
		int newY = 0;
		int newWidth = image.getW();
		int newHeight = image.getH();

		// Clipping Code.
		if (offX < 0) {
			newX -= offX;
			// System.err.println(newX);
		}

		if (offY < 0) {
			newY -= offY;
			// System.err.println(newY);
		}

		if (newWidth + offX >= pW) {
			newWidth -= newWidth + offX - pW;
			// System.err.println(newWidth);
		}

		if (newHeight + offY >= pH) {
			newHeight -= newHeight + offY - pH;
			// System.err.println(newHeight);
		}

		for (int y = newY; y < newHeight; y++) {

			for (int x = newX; x < newWidth; x++) {

				setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
			}
		}
	}

	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {

		// Don't Render Code.
		if (offX < -image.getTileW()) {
			return;
		}

		if (offY < -image.getTileH()) {
			return;
		}

		if (offX >= pW) {
			return;
		}

		if (offY >= pH) {
			return;
		}

		int newX = 0;
		int newY = 0;
		int newWidth = image.getTileW();
		int newHeight = image.getTileH();

		// Clipping Code.
		if (offX < 0) {
			newX -= offX;
			// System.err.println(newX);
		}

		if (offY < 0) {
			newY -= offY;
			// System.err.println(newY);
		}

		if (newWidth + offX >= pW) {
			newWidth -= newWidth + offX - pW;
			// System.err.println(newWidth);
		}

		if (newHeight + offY >= pH) {
			newHeight -= newHeight + offY - pH;
			// System.err.println(newHeight);
		}

		for (int y = newY; y < newHeight; y++) {

			for (int x = newX; x < newWidth; x++) {

				setPixel(x + offX, y + offY, image.getP()[(x + tileX * image.getTileW())
						+ (y + tileY * image.getTileH()) * image.getW()]);
			}
		}

	}

}
