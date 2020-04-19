/**
 * 
 */
package com.team.game.objects;

import java.awt.event.KeyEvent;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.gfx.ImageTile;
import com.team.game.GameManager;
import com.team.game.components.AABBComponent;

/**
 * @author Esteban
 *
 */
public class Skeleton extends GameObject {
	
	private ImageTile skeletonImage = new ImageTile("/Objetos/Enemigos/Skeleton.png", 32, 32);
	
	private int direction = 0;
	private float animation = 0;
	
	private int playerMatados;
	private int desdeTileX;
	private int hastaTileX;
	
	private int offsetX, offsetY;
	private int tileX, tileY;
	
	private float speed = 65;
	
	private boolean reboto = false;
	
	public Skeleton(int posX, int posY,int desdeTileX, int hastaTileX) {
		this.tag = "skeleton";
		this.posX = posX*GameManager.TILE_SIZE;
		this.posY = posY*GameManager.TILE_SIZE;
		this.width = 32;
		this.height = 32;
		this.tileX = posX;
		this.tileY = posY;
		this.paddingRight = 8;
		this.paddingLeft = 8;
		this.paddingTop = 3;
		this.paddingBot = 0;
		this.desdeTileX = desdeTileX;
		this.hastaTileX = hastaTileX;
		this.offsetX=0;
		this.offsetY=0;
		
		playerMatados = 0;
		
		this.addComponent(new AABBComponent(this));
	}
	

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {

			if(!reboto) {
				if(gm.getCollision(tileX+2, tileY) || gm.getCollision(tileX+2, tileY+1)) {
					offsetX += dt*speed;
					if(offsetX > paddingRight) {
						offsetX = paddingRight;
					reboto = true;
					}
				}else {
					offsetX += dt*speed;
				}
				direction = 0;
				animation += dt*3;
				if(animation>=2)
					animation = 0;
				if(tileX > hastaTileX)	reboto = true;
			}
			
			if(reboto) {
				if(gm.getCollision(tileX-1, tileY) || gm.getCollision(tileX-1, tileY+1)) {
					offsetX -= dt*speed;
					if(offsetX < -paddingLeft)
						offsetX = -paddingLeft;
					reboto = false;
				}else {
					offsetX -= dt*speed;
				}
				direction = 1;
				animation += dt*10;
				if(animation>=2)
					animation = 0;
				if(tileX < desdeTileX)	reboto = false;
			}
			
			if(offsetX > GameManager.TILE_SIZE/2) {
				tileX++;
				offsetX -= GameManager.TILE_SIZE;
			}

			if(offsetX < -GameManager.TILE_SIZE/2) {
				tileX--;
				offsetX += GameManager.TILE_SIZE;
			}
		
			posX = tileX*GameManager.TILE_SIZE + offsetX;
			posY = tileY*GameManager.TILE_SIZE + offsetY;
			
			
			this.updateComponents(gc, gm, dt);

	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawImageTile(skeletonImage, (int)posX, (int)posY, (int)animation, direction);
		
		this.renderComponents(gc, renderer);
	}


	@Override
	public void collision(GameObject other) {
		if(other.getTag().equalsIgnoreCase("player")) {
			playerMatados+=1;
		}
	}
	

}
