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
	
	private ImageTile skeletonImage = new ImageTile("/Objetos/Enemigos/Skeleton.png", 16, 16);
	
	private int direccion = 0;
	private int animacion = 0;
	
	private int playerMatados;
	private int desdeX = 0, hastaX = 0;
	private int offsetX, offsetY;
	
	private float speed = 75;
	private int direction;
	private float animation;
	
	public Skeleton(int posX, int posY,int desdeX, int hastaX) {
		this.tag = "Skeleton";
		this.posX = posX*GameManager.TILE_SIZE;
		this.posY = posY*GameManager.TILE_SIZE;
		this.width = 16;
		this.height = 16;
		this.paddingRight = 5;
		this.paddingLeft = 9;
		this.paddingTop = 3;
		this.paddingBot = 0;
		this.desdeX = desdeX;
		this.hastaX=hastaX;
		this.offsetX=0;
		this.offsetY=0;
		
		animacion = 0;
		
		playerMatados = 0;
		
		this.addComponent(new AABBComponent(this));
	}
	

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		// TODO Auto-generated method stub
		
			
			if(posX<hastaX) {
				offsetX += dt*speed;
				if(offsetX > paddingRight)
					offsetX = paddingRight;
					direction = 0;
					animation += dt*10;
			}else {
				offsetX += dt*speed;
			}
		
			if(posX<desdeX) {
				offsetX -= dt*speed;
				if(offsetX < -paddingLeft)
					offsetX = -paddingLeft;
				direction = 1;
				animation += dt*10;
			}else {
				offsetX -= dt*speed;
			}

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
