/**
 * 
 */
package com.team.engine.gfx;

/**
 * @author Pedro
 *
 */
public class ImageRequest {
	
	public Image image;
	public int zDepth;
	public int offsetX, offsetY;
	
	public ImageRequest(Image image, int zDepth, int offsetX, int offsetY) {
		
		this.image = image;
		this.zDepth = zDepth;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

}
