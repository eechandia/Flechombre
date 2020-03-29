/**
 * 
 */
package com.team.engine.gfx;

/**
 * @author Esteban
 *
 */
public class ImageTile extends Image{
	
	private int tileW, tileH;

	
	public ImageTile(String path, int tileW, int tileH) {
		super(path);
		this.tileW = tileW;
		this.tileH=tileH;
	}

	public Image getTileImage(int tileX, int tileY) {
		int[] pixel = new int[tileW*tileH];
		for (int y = 0;y< tileH; y++) {
			for(int x = 0; x<tileW;x++) {
				pixel[x+y*tileW] = this.getPixel()[(x+tileX*tileW)+(y+tileY+tileH)*this.getTileW()];
				}			
			}	
		
		return new Image(pixel,tileW,tileH);
	}

	public int getTileW() {
		return tileW;
	}


	public void setTileW(int tileW) {
		this.tileW = tileW;
	}


	public int getTileH() {
		return tileH;
	}


	public void setTileH(int tileH) {
		this.tileH = tileH;
	}

}
