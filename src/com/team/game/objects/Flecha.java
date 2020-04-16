/**
 * 
 */
package com.team.game.objects;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.audio.SoundClip;
import com.team.game.GameManager;

/**
 * @author Esteban
 *
 */
public class Flecha extends GameObject{
	
	private int tileX, tileY;
	private float offsetX, offsetY;

	
	private float speed = 200;
	private int direction;
	
	public Flecha(int tileX, int tileY, float offsetX, float offsetY,  int direction) {
		this.direction = direction;
		this.tileX = tileX;
		this.tileY = tileY;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.paddingLeft = 0;
		this.paddingTop = 0;
		this.paddingTop = 0;
		this.width = 4;
		this.height = 4;
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		
		// TODO Auto-generated method stub
		
		switch(direction) {
		case 0: offsetY -= speed * dt; break;
		case 1: offsetX += speed * dt; break;
		case 2: offsetY += speed * dt; break;
		case 3: offsetX -= speed * dt; break;
		
		}
		
		posX = tileX*GameManager.TILE_SIZE + offsetX;
		posY = tileY*GameManager.TILE_SIZE + offsetY;

		//Final Position
		if(offsetY > GameManager.TILE_SIZE) {
			tileY++;
			offsetY -= GameManager.TILE_SIZE;
		}

		if(offsetY < 0) {
			tileY--;
			offsetY += GameManager.TILE_SIZE;
		}
		if(offsetX > GameManager.TILE_SIZE) {
			tileX++;
			offsetX -= GameManager.TILE_SIZE;
		}

		if(offsetX < 0) {
			tileX--;
			offsetX += GameManager.TILE_SIZE;
		}
		
		if(gm.getCollision(tileX, tileY)) {
			this.dead=true;
		}
		
	
	}

	
	
	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawFillRect((int)posX, (int)posY, width, height, 0xffff0000);
	}

	@Override
	public void collision(GameObject other) {
	}
	
	

}
