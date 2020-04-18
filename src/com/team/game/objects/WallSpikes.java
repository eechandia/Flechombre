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
public class WallSpikes extends GameObject{
	private ImageTile spikesSprite = new ImageTile("/Objetos/spikesPared.png", 8, 8);
	private int posFinalY;
	private int pared;
	private int asesino;
	private static int playerMatados;
	
	public WallSpikes(int posX, int fromY, int toY, boolean rightWall) { // toY > fromY // true dibuja a la derecha, false dibuja a la izquierda
		this.tag = "spikes";
		this.posY = fromY*GameManager.TILE_SIZE;
		this.posFinalY = toY*GameManager.TILE_SIZE;
		this.posX = posX*GameManager.TILE_SIZE;
		this.width = GameManager.TILE_SIZE;
		this.height = GameManager.TILE_SIZE*(toY-fromY+1);
		this.dead = false;
		this.paddingRight = 0;
		this.paddingLeft = 0;
		this.paddingTop = 0;
		this.paddingBot = 0;
		this.asesino = 0;
		this.playerMatados=0;
		
		if(rightWall)
			pared = 1;
		else
			pared = 0;
	
		this.addComponent(new AABBComponent(this));
	}

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		this.updateComponents(gc, gm, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		for(int i=(int) posY; i<=posFinalY; i+=GameManager.TILE_SIZE) {
			renderer.drawImageTile(spikesSprite, (int)posX, i, pared, asesino);
			this.renderComponents(gc, renderer);
		}
	}

	@Override
	public void collision(GameObject other) {
		if(other.getTag().equalsIgnoreCase("player")){
			asesino = 1;
			playerMatados += 1;
		}
	}

	public static int getPlayerMatados() {
		return playerMatados;
	}
	
	

}
