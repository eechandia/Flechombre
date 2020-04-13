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
	
	private ImageTile sawSprite = new ImageTile("/saw.png", 64, 64);
	private float animacion;
	private int asesino;

	public Saw(int posX, int posY) {
		this.tag = "saw";
		this.posX = posX;
		this.posY = posY;
		this.width = 64;
		this.height = 64;
		this.paddingRight = 0;
		this.paddingLeft = 0;
		this.paddingTop = 0;
		animacion = 0;
		asesino = 0;
		
		this.addComponent(new AABBComponent(this));
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		animacion += dt*4;
		if(animacion >= 2)
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
		}
	}
	
}
