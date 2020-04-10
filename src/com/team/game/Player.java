/**
 * 
 */
package com.team.game;

import java.awt.event.KeyEvent;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.gfx.Image;

/**
 * @author Pedro
 *
 */
public class Player extends GameObject{
	
	private int tileX, tileY;
	private float offsetX, offsetY;
	
	private float speed = 100;
	private float fallSpeed = 5;
	private float jump = -3.5f;
	private boolean ground = false;
	//Image image = new Image("/test.png");//prueba1
	
	private float fallDistance = 0;
	
	public Player(int posX, int posY) {
		this.tag = "player";
		this.tileX = posX;
		this.tileY = posY;
		this.offsetX = 0;
		this.offsetY = 0;
		this.posX = posX*GameManager.TILE_SIZE;
		this.posY = posY*GameManager.TILE_SIZE;
		this.width = GameManager.TILE_SIZE;
		this.height = GameManager.TILE_SIZE;
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		//Left and Right
		if(gc.getInput().isKey(KeyEvent.VK_D)) {
			if(gm.getCollision(tileX+1, tileY) || gm.getCollision(tileX+1, tileY + (int)Math.signum((int)offsetY))) {
				if(offsetX < 0) {
					offsetX += dt*speed;
					if(offsetX > 0)
						offsetX = 0;
				}else {
					offsetX = 0;
				}
			}else {
				offsetX += dt*speed;
			}
		}
		if(gc.getInput().isKey(KeyEvent.VK_A)) {
			if(gm.getCollision(tileX-1, tileY) || gm.getCollision(tileX-1, tileY + (int)Math.signum((int)offsetY))) {
				if(offsetX > 0) {
					offsetX -= dt*speed;
					if(offsetX < 0)
						offsetX = 0;
				}else {
					offsetX = 0;
				}
			}else {
				offsetX -= dt*speed;
			}
		}
		
		//End of Left and Right
		
		//Beginning of Jump and Gravity
		fallDistance += dt*fallSpeed-0.005;
		
		if(gc.getInput().isKeyDown(KeyEvent.VK_W) && ground){
			fallDistance = jump;
			ground = false;
		}
		
		offsetY += fallDistance; 
		
		if(fallDistance < 0) {
			if((gm.getCollision(tileX, tileY-1) || gm.getCollision(tileX + (int)Math.signum((int)offsetX), tileY-1)) && offsetY < 0) {
				fallDistance = 0;
				offsetY = 0;
			}
		}
		
		if(fallDistance > 0) {
			if((gm.getCollision(tileX, tileY+1) || gm.getCollision(tileX + (int)Math.signum((int)offsetX), tileY+1)) && offsetY > 0) {
				fallDistance = 0;
				offsetY = 0;
				ground = true;
			}
		}
		//End of Jump and Gravity
		
		//Final Position
		if(offsetY > GameManager.TILE_SIZE/2) {
			tileY++;
			offsetY -= GameManager.TILE_SIZE;
		}

		if(offsetY < -GameManager.TILE_SIZE/2) {
			tileY--;
			offsetY += GameManager.TILE_SIZE;
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
		
		//Disparando
		if(gc.getInput().isKeyDown(KeyEvent.VK_UP)) {
			gm.addObject(new Flecha(tileX, tileY, offsetX + width/2, offsetY + height/2, 0));
		}
		if(gc.getInput().isKeyDown(KeyEvent.VK_RIGHT)) {
			gm.addObject(new Flecha(tileX, tileY, offsetX + width/2, offsetY + height/2, 1));
		}
		if(gc.getInput().isKeyDown(KeyEvent.VK_DOWN)) {
			gm.addObject(new Flecha(tileX, tileY, offsetX+ width/2, offsetY + height/2, 2));
		}
		if(gc.getInput().isKeyDown(KeyEvent.VK_LEFT)) {
			gm.addObject(new Flecha(tileX, tileY, offsetX+ width/2, offsetY + height/2, 3));
		}

		
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		
		//renderer.drawImage(image, (int)posX, (int)posY);
		renderer.drawFillRect((int)posX, (int)posY, GameManager.TILE_SIZE, GameManager.TILE_SIZE, 0xff107a2a);
	}

}
