package com.gabriel.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.gabriel.engine.gfx.Font;
import com.gabriel.engine.gfx.Image;
import com.gabriel.engine.gfx.ImageRequest;
import com.gabriel.engine.gfx.ImageTile;

public class Renderer {

	private Font font = Font.STANDARD;
	private ArrayList<ImageRequest> imageRequest = new ArrayList<ImageRequest>();

	// Pixel Width & Pixel Height
	private int pW;
	private int pH;

	// Pixels
	private int[] p;

	// z Buffer
	private int[] zb;

	// Light Map
	private int[] lm;

	// Light Block
	private int[] lb;

	private int ambientColor = 0xff6b6b6b;
	private int zDepth = 0;

	private boolean processing = false;

	public Renderer(GameContainer gc) {

		pW = gc.getWidth();
		pH = gc.getHeight();
		p = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		zb = new int[p.length];
		lm = new int[p.length];
		lb = new int[p.length];

	}

	public void clear() {

		for (int i = 0; i < p.length; i++) {

			p[i] = 0;
			zb[i] = 0;
			lm[i] = ambientColor;
			lb[i] = 0;

		}
	}

	public void process() {

		processing = true;

		Collections.sort(imageRequest, new Comparator<ImageRequest>() {

			@Override
			public int compare(ImageRequest i0, ImageRequest i1) {

				if (i0.zDepth < i1.zDepth) {
					return -1;
				}

				if (i0.zDepth > i1.zDepth) {
					return 1;
				}

				return 0;
			}

		});

		for (int i = 0; i < imageRequest.size(); i++) {

			ImageRequest ir = imageRequest.get(i);

			// System.err.println(ir.zDepth);

			setzDepth(ir.zDepth);
			drawImage(ir.image, ir.offX, ir.offY);

		}

		for (int i = 0; i < p.length; i++) {

			float r = ((lm[i] >> 16) & 0xff) / 255f;
			float g = ((lm[i] >> 8) & 0xff) / 255f;
			float b = (lm[i] & 0xff) / 255f;

			p[i] = ((int) (((p[i] >> 16) & 0xff) * r) << 16 | (int) (((p[i] >> 8) & 0xff) * g) << 8
					| (int) ((p[i] & 0xff) * b));
		}

		imageRequest.clear();
		processing = false;

	}

	// ----- OLD
	// 0xffff00ff is a Pink color that not render. Let's use this color as invisible
	// color. In RGB is (255,0,255).

	// ----- NEW
	// In new version value == 0xffff00ff is change to ((value >> 24) & 0xff) == 0)
	// that make posible render transparent pixel.
	public void setPixel(int x, int y, int value) {

		int alpha = ((value >> 24) & 0xff);

		if ((x < 0 || x >= pW || y < 0 || y >= pH) || alpha == 0) {

			return;
		}

		int index = x + y * pW;

		if (zb[index] > zDepth) {

			return;
		}

		zb[index] = zDepth;

		if (alpha == 255) {

			p[index] = value;

		} else {

			int pixelColor = p[index];

			int newRed = ((pixelColor >> 16) & 0xff)
					- (int) ((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));

			int newGreen = ((pixelColor >> 8) & 0xff)
					- (int) ((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));

			int newBlue = (pixelColor & 0xff) - (int) (((pixelColor & 0xff) - (value & 0xff)) * (alpha / 255f));

			p[index] = (newRed << 16 | newGreen << 8 | newBlue);
		}

	}

	public void setLightMap(int x, int y, int value) {

		if ((x < 0 || x >= pW || y < 0 || y >= pH)) {

			return;
		}

		int baseColor = lm[x + y * pW];

		int maxRed = Math.max((baseColor >> 16) & 0xff, (value >> 16) & 0xff);
		int maxGreen = Math.max((baseColor >> 8) & 0xff, (value >> 8) & 0xff);
		int maxBlue = Math.max(baseColor & 0xff, value & 0xff);

		lm[x + y * pW] = (maxRed << 16 | maxGreen << 8 | maxBlue);

	}

	// Draw Text
	public void drawText(String text, int offX, int offY, int color) {

		int offset = 0;

		for (int i = 0; i < text.length(); i++) {

			int unicode = text.codePointAt(i);

			for (int y = 0; y < font.getFontImage().getH(); y++) {

				for (int x = 0; x < font.getWidths()[unicode]; x++) {

					if (font.getFontImage().getP()[(x + font.getOffsets()[unicode])
							+ y * font.getFontImage().getW()] == 0xffffffff) {
						setPixel(x + offX + offset, y + offY, color);
					}
				}
			}
			offset += font.getWidths()[unicode];
		}

	}

	// Draw Image
	public void drawImage(Image image, int offX, int offY) {

		if (image.isAlpha() && !processing) {

			imageRequest.add(new ImageRequest(image, zDepth, offX, offY));
			return;

		}

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

	// Draw Image Tile
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {

		if (image.isAlpha() && !processing) {

			imageRequest.add(new ImageRequest(image.getTileImage(tileX, tileY), zDepth, offX, offY));
			return;

		}

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

	// Draw Rectangle
	public void drawRect(int offX, int offY, int width, int height, int color) {

		for (int y = 0; y <= height; y++) {
			setPixel(offX, y + offY, color);
			setPixel(offX + width, y + offY, color);
		}

		for (int x = 0; x <= width; x++) {
			setPixel(x + offX, offY, color);
			setPixel(x + offX, offY + height, color);
		}
	}

	// Draw Fill Rectangle
	public void drawFillRect(int offX, int offY, int width, int height, int color) {

		// Don't Render Code.
		if (offX < -width) {
			return;
		}

		if (offY < -height) {
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
		int newWidth = width;
		int newHeight = height;

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
				setPixel(x + offX, y + offY, color);
			}
		}
	}

	// Getters & Setters

	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}

}
