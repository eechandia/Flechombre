/**
 * 
 */
package com.team.game.objects;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.gfx.ImageTile;
import com.team.game.GameManager;
import com.team.game.components.AABBComponent;

/**
 * @author pedro
 *
 */
public class Bat extends GameObject{
	private ImageTile batSprite = new ImageTile("/Objetos/Enemigos/Bat.png", 16, 16);
	private float animacion = 0;
	private int facing = 0;
	private int speed = 75;
	private boolean playerDetectado = false;
	private int tileX, tileY;
	private float offsetX, offsetY;
	private float posResetX, posResetY;
	
	private float posPlayerX, posPlayerY;
	
	public Bat(float posX, float posY) {
		this.tag = "bat";
		this.posX = posX*GameManager.TILE_SIZE;
		posResetX = posX*GameManager.TILE_SIZE;
		this.posY = posY*GameManager.TILE_SIZE;
		posResetY = posY*GameManager.TILE_SIZE;
		this.width = 16;
		this.height = 16;
		this.paddingLeft = 2;
		this.paddingRight = 3;
		this.paddingTop = 4;
		this.paddingBot = 1;
		this.animacion = 2;
		
		this.addComponent(new AABBComponent(this));
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		//Movimiento del bat
//		if(!gm.isPlayerAlive())
//			playerDetectado = false;
		if(gm.isPlayerAlive() && !playerDetectado) {
			posPlayerX = ((Player) gm.getObject("player")).getPosX();
			posPlayerY = ((Player) gm.getObject("player")).getPosY();
			tileX = (int) posX/GameManager.TILE_SIZE;
			offsetX = posX - tileX*GameManager.TILE_SIZE;
			tileY = (int) posY/GameManager.TILE_SIZE;
			offsetY = posY - tileY*GameManager.TILE_SIZE;
			if(Math.abs(posX-posPlayerX)<=100)
				if(Math.abs(posY-posPlayerY)<=100)
					playerDetectado = true;
		}
			
		
		if(gm.isPlayerAlive() && playerDetectado) {
			posPlayerX = ((Player) gm.getObject("player")).getPosX();
			posPlayerY = ((Player) gm.getObject("player")).getPosY();
			
			int plusX = (int)Math.signum((int)((offsetX>paddingRight || offsetX<-paddingLeft) ? offsetX : 0));
			if(plusX>0)
				plusX+=1;
			int plusY =  (int)Math.signum((int)offsetY);
			if(plusY>0)
				plusY+=1;
			
			if(posPlayerX > posX) {
				if(gm.getCollision(tileX+2, tileY) || gm.getCollision(tileX+2, tileY+1) || gm.getCollision(tileX+2, tileY + plusX)) {
					offsetX += dt*speed;
					if(offsetX > paddingRight)
						offsetX = paddingRight;
				}else {
					offsetX += dt*speed;
				}
				facing = 0;
			}
			if(posPlayerX < posX) {
				if(gm.getCollision(tileX-1, tileY) || gm.getCollision(tileX-1, tileY+1) || gm.getCollision(tileX-1, tileY + plusY)) {
					offsetX -= dt*speed;
					if(offsetX < -paddingLeft)
						offsetX -= -paddingLeft;
				}else {
					offsetX -= dt*speed;
				}
				facing = 1;
			}
			
			if(posPlayerY > posY) {
				offsetY += dt*(Math.abs(posPlayerY-posY)+10);
				if((gm.getCollision(tileX, tileY+2) || gm.getCollision(tileX+1, tileY+2) || gm.getCollision(tileX + plusX, tileY+2)) && offsetY > 0) {
					offsetY = 0;
				}
			}
			if(posPlayerY < posY) {
				offsetY -= dt*(Math.abs(posPlayerY-posY)+10);
				if((gm.getCollision(tileX, tileY-1) || gm.getCollision(tileX+1, tileY-1) || gm.getCollision(tileX + plusX, tileY-1)) && offsetY < -paddingTop) {
					offsetY = -paddingTop;
				}
			}
			
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
		}
		
		if(playerDetectado) {
			animacion += dt*7;
			if(animacion >= 2)
				animacion = 0;
		}
		
		this.updateComponents(gc, gm, dt);
	}
	
	public void reset() {
		posX = posResetX;
		posY = posResetY;
		playerDetectado = false;
		animacion = 2;
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawImageTile(batSprite, (int)posX, (int)posY, (int)animacion, facing);
		
		this.renderComponents(gc, renderer);
	}

	@Override
	public void collision(GameObject other) {
		// TODO Auto-generated method stub
		
	}

}
