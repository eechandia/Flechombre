package com.team.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import com.team.engine.gfx.Font;
import com.team.engine.gfx.Image;
import com.team.engine.gfx.ImageRequest;
import com.team.engine.gfx.ImageTile;

public class Renderer {
	
	private Font font = Font.STANDARD;
	private ArrayList<ImageRequest> imageRequest = new ArrayList<ImageRequest>();

	private int pixelW, pixelH;
	private int[] pixels;
	private int[] zBuffer;
	
	private int zDepth = 0;
	private boolean processing = false;

	public Renderer(GameContainer gc) {
		
		pixelW = gc.getWidth();
		pixelH = gc.getHeight();
		pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		zBuffer = new int[pixels.length];
	}

	public void clear() {

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xff000000;
			zBuffer[i] = 0;
		}
	}
	
	public void process() {
		processing = true;
		
		for(int i = 0; i < imageRequest.size(); i++) {
			
			ImageRequest ir = imageRequest.get(i);
			setzDepth(ir.zDepth);
			drawImage(ir.image, ir.offsetX, ir.offsetY);	
		}
		
		imageRequest.clear();
		processing = false;
	}
	
	public void setPixel(int x, int y, int value) {
		int alpha = ((value >> 24) & 0xff);
		
		if((x<0 || x>=pixelW || y<0 || y >= pixelH)|| alpha == 0) { //esto es el color que no va a dibujar
			return;
		}
		
		if(zBuffer[x + y * pixelW] > zDepth)
			return;
		
		if(alpha == 255) {
			pixels[x + y * pixelW] = value;
		}else {
			int pixelColor = pixels[x + y * pixelW];
			
			int newRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
			int newGreen = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
			int newBlue = (pixelColor & 0xff) - (int)(((pixelColor & 0xff) - (value & 0xff)) * (alpha / 255f));
			
			pixels[x + y * pixelW] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);	
		}
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
		
		if(image.isAlpha() && !processing) {
			
			imageRequest.add(new ImageRequest(image, zDepth, offsetX, offsetY));
			return;
			
		}
		
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
	
	public void drawRect(int offsetX, int offsetY, int width, int height, int color) {
		
		for(int y = 0; y <= height; y++) {
			setPixel(offsetX, y + offsetY, color);
			setPixel(offsetX + width, y + offsetY, color);
		}
		
		for(int x = 0; x <= width; x++) {
			setPixel(x + offsetX, offsetY, color);
			setPixel(x + offsetX, offsetY + height, color);	
		}
	}
	
	public void drawFillRect(int offsetX, int offsetY, int width, int height, int color) {
		//no renderiza 
		if(offsetX < -width) return;
		if(offsetY < -height) return; 
		
		if(offsetX >= pixelW)return;
		if(offsetY >= pixelH)return;
				
		int newX = 0;
		int newY = 0;
		int newWidth = width;
		int newHeight = height;
				
		//renderiza solo lo que no esta clipeado
				
		if(offsetX < 0) newX -= offsetX;
		if(offsetY < 0) newY -= offsetY;
					
		if(newWidth + offsetX > pixelW) newWidth -= newWidth + offsetX - pixelW; 		
		if(newHeight + offsetY > pixelH) newHeight -= newHeight + offsetY - pixelH; 
		
		for(int y = newY; y <= newWidth; y++) {
			for(int x = newX; x <= newHeight; x++) {
				setPixel(x + offsetX, y + offsetY, color);
			}
		}
	}

	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}
}

	
