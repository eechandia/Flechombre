/**
 * 
 */
package com.team.game.objects;

import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.State;
import com.team.engine.audio.SoundClip;
import com.team.engine.gfx.ImageTile;
import com.team.game.GameManager;
import com.team.game.components.AABBComponent;

/**
 * @author Esteban
 *
 */
public class Diana extends GameObject{
	private ImageTile checkpoint;
	
	private float animacion;
	private boolean animar;
	
	private SoundClip sonidoDiana = new SoundClip("/Audio/Mario-coin-sound.wav");;
	
	
	public Diana(int x, int y, int facing) {
		this.tag = "diana";
		this.posX = x*GameManager.TILE_SIZE;
		this.posY = y*GameManager.TILE_SIZE;
		this.height = 32;
		this.width = 32;
		this.paddingRight = 6;
		this.paddingLeft = 8;
		this.paddingBot = 0;
		this.paddingTop = 7;
		animacion = 0;
		animar = false;
		sonidoDiana.setVolume(-30);
		
		switch(facing) {
		case 0:
			this.checkpoint = new ImageTile("/Objetos/dianaFacingLeft.png", 32, 32);
			break;
		case 1:
			this.checkpoint = new ImageTile("/Objetos/dianaFacingRight.png", 32, 32);
			break;
	}
		
		this.addComponent(new AABBComponent(this));
	}
	

	@Override
	public void update(GameContainer gc, GameManager gm, float dt) {
		if(animacion>3 && !gm.isLevelTerminado()) {
			try {
				sonidoDiana.start();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			gm.setLevelTerminado(true);
			gc.setLevelSeleccionado(gc.getLevelSeleccionado()+1);
		}
			
		if(animar && animacion<3) {
			animacion += dt*5;
		}
		
		this.updateComponents(gc, gm, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer renderer) {
		renderer.drawImageTile(checkpoint, (int)posX, (int)posY, (int)animacion, 0);
		this.renderComponents(gc, renderer);
	}

	@Override
	public void collision(GameObject other) {
		if(other.getTag().equalsIgnoreCase("player")) {
			animar = true;
			
		}	
	
	}

}
