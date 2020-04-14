/**
 * 
 */
package com.team.game.objects;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.game.GameManager;
import com.team.game.components.AABBComponent;

/**
 * @author Pedro
 *
 */
public class Platform extends GameObject{
	private int color = 0xff0000ff;
	
	public Platform(int x, int y) {
		this.tag = "Platform";
		this.width = 32;
		this.height = 16;
		this.paddingRight = 0;
		this.paddingLeft = 0;
		this.paddingTop = 0;
		this.paddingBot = 0;
		this.posX = x*GameManager.TILE_SIZE;
		this.posY = y*GameManager.TILE_SIZE;
		
		this.addComponent(new AABBComponent(this));
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		this.updateComponents(gc, gm, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawFillRect((int)posX, (int)posY, width, height, color);
		this.renderComponents(gc, renderer);
	}

	@Override
	public void collision(GameObject other) {
		color = 0xffff0000;
	}

}
