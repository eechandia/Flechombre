package com.team.engine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.team.engine.gfx.Font;
import com.team.engine.gfx.Image;
import com.team.engine.gfx.ImageRequest;
import com.team.engine.gfx.ImageTile;
import com.team.engine.gfx.Light;
import com.team.engine.gfx.LightRequest;

public class Renderer {
	
	private Font font = Font.STANDARD;
	private ArrayList<ImageRequest> imageRequest = new ArrayList<ImageRequest>();
	private ArrayList<LightRequest> lightRequest = new ArrayList<LightRequest>();
	
	private int pixelW, pixelH;
	private int[] pixels;
	private int[] zBuffer;
	private int[] lightMap;
	private int[] lightBlock;
	
	private int zDepth = 0;
	private boolean processing = false;
	private int ambientColor = 0xff232323;
	private int camaraX, camaraY;

	public Renderer(GameContainer gc) {
		pixelW = gc.getWidth();
		pixelH = gc.getHeight();
		pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		zBuffer = new int[pixels.length];
		lightMap = new int[pixels.length];
		lightBlock = new int[pixels.length];
	}

	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
			zBuffer[i] = 0;
			lightMap[i] = ambientColor;
			lightBlock[i] = 0;		
		}
	}
	
	
	public void process() {
		processing = true;
		Collections.sort(imageRequest, new Comparator<ImageRequest>() {
			@Override
			public int compare(ImageRequest i1, ImageRequest i2) {
				if(i1.zDepth < i2.zDepth){
					return -1;
				}
				if(i1.zDepth > i2.zDepth){
					return 1;
				}	
				return 0;
			}
		});
		
		for(int i = 0; i < imageRequest.size(); i++) {
			
			ImageRequest ir = imageRequest.get(i);
			setzDepth(ir.zDepth);
			drawImage(ir.image, ir.offsetX, ir.offsetY);	
		}
		
		//dibujar ilumiaciom
		
		for(int i = 0; i < lightRequest.size(); i++) {
			LightRequest l = lightRequest.get(i);
			drawLightRequest(l.light, l.locX, l.locY);
			
		}
		
	
		//merge pixel map y light map
		for(int i = 0; i<pixels.length; i++) {
			float r = ((lightMap[i] >> 16) & 0xff)/255f;
			float g = ((lightMap[i] >> 8) & 0xff)/255f; 
			float b = (lightMap[i] & 0xff)/255f;
			pixels[i] = ((int)(((pixels[i]>>16) & 0xff)*r) << 16 | (int)(((pixels[i]>>8) & 0xff)*g)  << 8 | (int)((pixels[i]& 0xff)*b));
		}
		imageRequest.clear();
		lightRequest.clear();
		processing = false;
	}
	
	
	public void setPixel(int x, int y, int value) {
		int alpha = ((value >> 24) & 0xff);

		if((x<0 || x>=pixelW || y<0 || y >= pixelH)|| alpha == 0) { //esto es el color que no va a dibujar
			return;
		}
		
		int index = x+y*pixelW;
		if(zBuffer[index] > zDepth)
			return;
		
		zBuffer[index ] = zDepth;
		
		if(alpha == 255) {
			pixels[index ] = value;
		}else {
			int pixelColor = pixels[index ];
			
			int newRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
			int newGreen = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
			int newBlue = (pixelColor & 0xff) - (int)(((pixelColor & 0xff) - (value & 0xff)) * (alpha / 255f));
			
			pixels[index] = (newRed << 16 | newGreen << 8 | newBlue);	
		}
	}
	
	public void setLightMap(int x, int y, int value) {

		if(x<0 || x>=pixelW || y<0 || y >= pixelH) {
			return;
		}
		
		int baseColor = lightMap[x+y*pixelW];
		
		int maxRed = Math.max((baseColor >> 16) & 0xff, (value >> 16) & 0xff);
		int maxGreen  = Math.max((baseColor >> 8) & 0xff, (value >> 8) & 0xff);
		int maxBlue = Math.max(baseColor & 0xff, value & 0xff);
		
		
		lightMap[x+y*pixelW] = (maxRed << 16 | maxGreen << 8 | maxBlue);	
	}
	
	public void setLightBlock(int x, int y, int value) {

		if(x<0 || x>=pixelW || y<0 || y >= pixelH) {
			return;
		}
		
		if(zBuffer[x+y*pixelW] > zDepth)
			return;
		
		lightBlock[x+y*pixelW] = value;	
	}
	
	public void drawText(String text, int offsetX, int offsetY, int color) {
		offsetX -= camaraX;
		offsetY -= camaraY;

		int offset = 0;

		for(int i=0; i < text.length(); i++) {
			
			int unicode = text.codePointAt(i);

			for(int y=0; y < font.getFontImage().getHeight(); y++) {
				for(int x=0; x < font.getWidths()[unicode]; x++) {
					if(font.getFontImage().getPixel()[(x + font.getOffsets()[unicode]) + (y * font.getFontImage().getWidth())] == 0xffffffff) {
						setPixel(x + offsetX + offset, y + offsetY, color);
					}
				}
			}
			
//			offsetX = 0;
			offset += font.getWidths()[unicode];
			
		}
	}
	
	public void drawImage(Image image, int offsetX, int offsetY) {
		offsetX -= camaraX;
		offsetY -= camaraY;
		
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
				setPixel(x+offsetX, y+offsetY, image.getPixel()[x + y *image.getWidth()]);
				setLightBlock(x+offsetX, y+offsetY, image.getLightBlock());
			}
		}
	}
	
	public void drawImageTile(ImageTile image, int offsetX, int offsetY, int tileX, int tileY) {
		offsetX -= camaraX;
		offsetY -= camaraY;
		
		if(image.isAlpha() && !processing) {	
			imageRequest.add(new ImageRequest(image.getTileImage(tileX, tileY), zDepth, offsetX, offsetY));
			return;
		}

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
				setLightBlock(x+offsetX, y+offsetY, image.getLightBlock());
			}
		}
	}
	
	public void drawRect(int offsetX, int offsetY, int width, int height, int color) {
		offsetX -= camaraX;
		offsetY -= camaraY;
		
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
		offsetX -= camaraX;
		offsetY -= camaraY;
		
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
		
		for(int y = newY; y < newHeight; y++) {
			for(int x = newX; x < newWidth; x++) {
				setPixel(x + offsetX, y + offsetY, color);
			}
		}
	}
	
	public void drawLight(Light l, int offsetX, int offsetY) {
		
		lightRequest.add(new LightRequest(l,offsetX,offsetY));
		
	}
	
	private void drawLightRequest(Light light, int offsetX, int offsetY) {
		offsetX -= camaraX;
		offsetY -= camaraY;
		
		for(int i=0; i <= light.getDiameter(); i++) {
			drawLightLine(light, light.getRadius(), light.getRadius(), i, 0, offsetX, offsetY);
			drawLightLine(light, light.getRadius(), light.getRadius(), i, light.getDiameter(), offsetX, offsetY);
			drawLightLine(light, light.getRadius(), light.getRadius(), 0, i, offsetX, offsetY);
			drawLightLine(light, light.getRadius(), light.getRadius(), light.getDiameter(), i, offsetX, offsetY);
		}
	}
	
	//Bresenham's line algorithm
	
	private void drawLightLine(Light light, int startX, int startY, int endX, int endY, int offsetX, int offsetY) {
		
		int dx = Math.abs(endX - startX);
		int dy = Math.abs(endY - startY);
		
		int sx = startX < endX ? 1 : -1;
		int sy = startY < endY ? 1 : -1;
		
		int err = dx - dy;
		int err2;
		
		while(true) {
			
			int screenX = startX - light.getRadius() + offsetX;
			int screenY =  startY - light.getRadius() + offsetY;
			
			if(screenX<0 || screenX >= pixelW || screenY<0 || screenY>=pixelH)
				return;
			
			int lightColor = light.getLightValue(startX, startY);
			if(lightColor == 0)
				return;
			
			if(lightBlock[screenX+screenY*pixelW] == Light.FULL) 
				return; 
			
			setLightMap(screenX, screenY, lightColor); 
			
			if(startX == endX && startY == endY)
				break;
			err2 = 2 * err;
			if(err2 > -1*dy) {
				err -= dy;
				startX += sx;
			}
			
			if(err2 < dx) {
				err += dx;
				startY +=sy;
			}
		}
	}

	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}


	public int getAmbientColor() {
		return ambientColor;
	}


	public void setAmbientColor(int ambientColor) {
		this.ambientColor = ambientColor;
	}


	public int getCamaraX() {
		return camaraX;
	}


	public void setCamaraX(int camaraX) {
		this.camaraX = camaraX;
	}


	public int getCamaraY() {
		return camaraY;
	}


	public void setCamaraY(int camaraY) {
		this.camaraY = camaraY;
	}
}

	
