/**
 * 
 */
package com.team.engine.gfx;

/**
 * @author Pedro
 *
 */
public class Font {
	
	public static final Font STANDARD = new Font("/standarFont.png");
	
	private Image fontImage;
	private int[] offsets;
	private int[] widths;

	public Font(String path) {
		
		fontImage = new Image(path);
		//System.out.println(fontImage.getWidth());
		
		offsets = new int[59]; // poner los numeros, la longitud de la imagen?
		widths = new int[59];
		
		int unicode = 0; 
		
		for(int i=0; i < fontImage.getWidth(); i++) {
			
			
			if(fontImage.getPixel()[i] == 0xff0000ff) {
				offsets[unicode] = i; 
				//System.out.println(offsets[unicode]);
			}
			
			if(fontImage.getPixel()[i] == 0xffffff00) {
				widths[unicode] = i - offsets[unicode];
				unicode++;
				
			}

			
			
		}
	}

	public Image getFontImage() {
		return fontImage;
	}

	public void setFontImage(Image fontImage) {
		this.fontImage = fontImage;
	}

	public int[] getWidths() {
		return widths;
	}

	public void setWidths(int[] widths) {
		this.widths = widths;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public void setOffsets(int[] offsets) {
		this.offsets = offsets;
	}
}
