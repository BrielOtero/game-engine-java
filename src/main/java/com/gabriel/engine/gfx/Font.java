package com.gabriel.engine.gfx;

public class Font {

	public static final Font STANDARD = new Font("/res/fonts/comic.png"); 

	private Image fontImage;
	private int[] offsets;
	private int[] widths;

	public Font(String path) {

		fontImage = new Image(path);

		// ----OLD
		// 59 Because the font start in Unicode 32 that is space to 90 that is Z
		// https://unicode-table.com/en/
		// offsets = new int[59];
		// Widths = new int[59];
		
		// ----NEW
		offsets = new int[256];
		widths = new int[256];

		int Unicode = 0;

		// 0x tell java that is unicode
		// ff alpha value
		// 0000ff the blue color in the font image
		for (int i = 0; i < fontImage.getW(); i++) {
			if (fontImage.getP()[i] == 0xff0000ff) {
				offsets[Unicode] = i;
			}
			if (fontImage.getP()[i] == 0xffffff00) {
				widths[Unicode] = i - offsets[Unicode];
				Unicode++;
			}
		}
	}

	// Getters & Setters

	public Image getFontImage() {
		return fontImage;
	}

	public void setFontImage(Image fontImage) {
		this.fontImage = fontImage;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public void setOffsets(int[] offsets) {
		this.offsets = offsets;
	}

	public int[] getWidths() {
		return widths;
	}

	public void setWidths(int[] widths) {
		this.widths = widths;
	}

}
