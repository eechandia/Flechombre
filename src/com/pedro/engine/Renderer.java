package com.pedro.engine;

import java.awt.image.DataBufferInt;

public class Renderer {
	 
	private int pixelW, pixelH; //esto no lo usa en el tercer video, solo lo hace
	private int[] pixels;
	
	public Renderer(GameContainer gc) {
		pixelW = gc.getWidth();
		pixelH = gc.getHeight();
		pixels = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData(); 
	}
	
	public void clear() {
		
		for(int i=0; i<pixels.length; i++) {
			pixels[i] = 0xff000000;
		}
	}
}
