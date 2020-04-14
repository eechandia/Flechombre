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
public class Flag extends GameObject{
	private ImageTile flag = new ImageTile("/flag.png", 32, 32);
	
	private float animacion;
	private boolean animar;
	
	public Flag(int x, int y) {
		this.tag = "flag";
		this.posX = x*GameManager.TILE_SIZE;
		this.posY = y*GameManager.TILE_SIZE;
		this.height = 32;
		this.width = 32;
		this.paddingRight = 6;
		this.paddingLeft = 9;
		this.paddingBot = 0;
		this.paddingTop = 20;
		animacion = 0;
		animar = false;
		
		this.addComponent(new AABBComponent(this));
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		if(animar && animacion<3) {
			animacion += dt*5;
			paddingTop = 4;
		}
		
		this.updateComponents(gc, gm, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawImageTile(flag, (int)posX, (int)posY, (int)animacion, 0);
		this.renderComponents(gc, renderer);
	}

	@Override
	public void collision(GameObject other) {
		if(other.getTag().equalsIgnoreCase("player")) {
			animar = true;
		}
		
	}

}
