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
	
	private float speed = 100;
	Image image = new Image("/test.png");//prueba1
	
	public Player(int posX, int posY) {
		this.tag = "player";
		this.posX = posX*16;
		this.posY = posY*16;
		this.width = 16;
		this.height = 16;
	}

	@Override
	public void update(GameContainer gc, float dt) {
		if(gc.getInput().isKey(KeyEvent.VK_W)) {
			posY -= dt*speed;
		}
		if(gc.getInput().isKey(KeyEvent.VK_S)) {
			posY += dt*speed;
		}
		if(gc.getInput().isKey(KeyEvent.VK_A)) {
			posX -= dt*speed;
		}
		if(gc.getInput().isKey(KeyEvent.VK_D)) {
			posX += dt*speed;
		}
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		
		renderer.drawImage(image, (int)posX, (int)posY);
	}

}
