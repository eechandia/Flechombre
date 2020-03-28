package com.team.engine;

import java.awt.image.DataBufferInt;

import com.team.engine.gfx.Font;
import com.team.engine.gfx.Image;
import com.team.engine.gfx.ImageTile;

public class Renderer {

	private int pixelW, pixelH; // esto no lo usa en el tercer video, solo lo hace
	private int[] pixels;
	private Font font = Font.STANDARD;

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
		if((x<0 || x>=pixelW || y<0 || y >= pixelH)|| value == 0xffff00ff) { //esto es el color que no va a dibujar
			return;
		}
		pixels[x + y * pixelW]=value;
	}
	
	
	public void drawText(String text, int offsetX, int offsetY, int color) {
		

		text = text.toUpperCase(); // si tenemos solo mayusculas hechas
		int offset = 0;

		for(int i=0; i < text.length(); i++) {
			
			int unicode = text.codePointAt(i) - 32;

			for(int y=0; y < font.getFontImage().getHeight(); y++) {
				for(int x=0; x < font.getWidths()[unicode]; x++) {
					if(font.getFontImage().getPixel()[(x + font.getOffsets()[unicode]) + (y * font.getFontImage().getWidth())] == 0xffffffff) {
						setPixel(x + offsetX + offset, y + offsetY, color);
					}
				}
			}
			
			offsetX = 0;
			offset += font.getWidths()[unicode];
			
		}
	}
	
	public void drawImage(Image image, int offsetX, int offsetY) {
		
		
		//no renderiza 
		if(offsetX < -image.getWidth()) return;
		if(offsetY < -image.getHeight()) return; 
		
		if(offsetX >= pixelW)return;
		if(offsetY >= pixelH)return;
		
		int newX = 0;
		int newY = 0;
		int newWidth = image.getWidth();
		int newHeight = image.getHeight();
		
		//renderiza solo lo que no esta clipeado
		
		if(offsetX < 0) newX -= offsetX;
		if(offsetY < 0) newY -= offsetY;
			
		if(newWidth + offsetX > pixelW) newWidth -= newWidth + offsetX - pixelW; 		
		if(newHeight + offsetY > pixelH) newHeight -= newHeight + offsetY - pixelH; 
		
		
		
		for(int y= newY; y<newHeight; y++) {
			for(int x = newX; x<newWidth;x++) {
				setPixel( x+offsetX, y+offsetY, image.getPixel()[x + y *image.getWidth() ]);
			}
		}
	}
	
	public void drawImageTile(ImageTile image, int offsetX, int offsetY, int tileX, int tileY) {

		//no renderiza 
		if(offsetX < -image.getTileW()) return;
		if(offsetY < -image.getTileH()) return; 
		
		if(offsetX >= pixelW)return;
		if(offsetY >= pixelH)return;
		
		int newX = 0;
		int newY = 0;
		int newWidth = image.getTileW();
		int newHeight = image.getTileH();
		
		//renderiza solo lo que no esta clipeado
		
		if(offsetX < 0) newX -= offsetX;
		if(offsetY < 0) newY -= offsetY;
			
		if(newWidth + offsetX > pixelW) newWidth -= newWidth + offsetX - pixelW; 		
		if(newHeight + offsetY > pixelH) newHeight -= newHeight + offsetY - pixelH; 
		
		
		
		for(int y= newY; y<newHeight; y++) {
			for(int x = newX; x<newWidth;x++) {
				setPixel( x+offsetX, y+offsetY, image.getPixel()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) *image.getWidth() ]);
			}
		}
		
		
	}
}

	
