/**
 * 
 */
package com.team.game;

import java.awt.event.KeyEvent;

import com.team.engine.AbstractGame;
import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.audio.SoundClip;
import com.team.engine.gfx.ImageTile;

/**
 * @author Pedro
 *
 */
public class GameManager extends AbstractGame {
	
	private ImageTile image;
	private SoundClip clip;

	

	public GameManager() {
		
		image = new ImageTile("/test.png", 16,16);
		clip = new SoundClip("/Audio/Mario-coin-sound.wav");
		clip.setVolume(-20f);
	}

	@Override
	public void update(GameContainer gc, float dt) {
		//aca iria el codigo del juego
		
		if(gc.getInput().isKeyDown(KeyEvent.VK_A)) {
			clip.start();
		}
		
		temp += dt;
		if(temp > 3) temp = 0;
	}
	
	float temp = 0;
	
	
	@Override
	public void render(GameContainer gc, Renderer r) {
		
		r.drawImageTile(image, gc.getInput().getMouseX()-8, gc.getInput().getMouseY()-8, (int)temp , 0);

	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}
}
