package com.team.engine;

import java.awt.image.DataBufferInt;

import com.team.engine.gfx.Image;

public class Renderer {

	private int pixelW, pixelH; // esto no lo usa en el tercer video, solo lo hace
	private int[] pixels;

	public Renderer(GameContainer gc) {
		pixelW = gc.getWidth();
		pixelH = gc.getHeight();
		pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
	}

	public void clear() {

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xff000000;
		}
	}
	
	public void setPixel(int x, int y, int value) {
		if((x<0 || x>=pixelW || y<0 || y >= pixelH)|| value == 0xffffff) { //esto es el color que no va a dibujar
			return;
		}
		pixels[x + y * pixelW]=value;
	}
	
	public void drawImage(Image image, int offsetX, int offsetY) {
		for(int y=0; y<image.getHeight(); y++) {
			for(int x = 0; x<image.getWidth();x++) {
				setPixel( x+offsetX, y+offsetY, image.getPixel()[x + y *image.getWidth() ]);
			}
		}
	}
}
