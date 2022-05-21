package com.gabriel.engine.gfx;

public class Font {

	private Image fontImage;
	private int[] offsets;
	private int[] Widths;

	public Font(String path) {

		fontImage = new Image(path);

		// 58 Because the font start in Unicode 32 that is space to 90 that is Z
		offsets = new int[58];
		Widths = new int[58];

		int Unicode = 0;

		// 0x tell java that is unicode
		// ff alpha value
		// 0000ff the blue color in the font image
		for (int i = 0; i < fontImage.getW(); i++) {
			if (fontImage.getP()[i] == 0xff0000ff) {
				offsets[Unicode] = i;
			}
			if (fontImage.getP()[i] == 0xffffff00) {
				Widths[Unicode] = i - offsets[Unicode];
				Unicode++;
			}
		}
	}
}
                   