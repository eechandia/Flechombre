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
 * @author Pedro
 *
 */
public class Saw extends GameObject{
	
	private ImageTile sawSprite = new ImageTile("/miniSaw.png", 32, 32);
	private float animacion;
	private int asesino;
	private int playerMatados;

	public Saw(int posX, int posY) {
		this.tag = "saw";
		this.posX = posX*GameManager.TILE_SIZE;
		this.posY = posY*GameManager.TILE_SIZE;
		this.width = 32;
		this.height = 32;
		this.paddingRight = 5;
		this.paddingLeft = 4;
		this.paddingTop = 5;
		this.paddingBot = 4;
		animacion = 0;
		asesino = 0;
		playerMatados = 0;
		
		this.addComponent(new AABBComponent(this));
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		animacion += dt*3;
		if(animacion >= 4)
			animacion = 0;
		
		this.updateComponents(gc, gm, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawImageTile(sawSprite, (int)posX, (int)posY, (int)animacion, asesino);
		
		this.renderComponents(gc, renderer);
	}

	@Override
	public void collision(GameObject other) {
		if(other.getTag().equalsIgnoreCase("player")) {
			asesino = 1;
			playerMatados+=1;
		}
	}
	
}
