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
	
	public Bat(float posX, float posY) {
		this.tag = "bat";
		this.posX = posX*GameManager.TILE_SIZE;
		this.posY = posY*GameManager.TILE_SIZE;
		this.width = 16;
		this.height = 16;
		this.paddingLeft = 2;
		this.paddingRight = 3;
		this.paddingTop = 4;
		this.paddingBot = 1;
		
		this.addComponent(new AABBComponent(this));
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		animacion += dt*7;
		if(animacion >= 2)
			animacion = 0;
		
		this.updateComponents(gc, gm, dt);
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
