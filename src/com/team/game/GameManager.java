/**
 * 
 */
package com.team.game;

import java.awt.event.KeyEvent;

import com.team.engine.AbstractGame;
import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.audio.SoundClip;
import com.team.engine.gfx.Image;
import com.team.engine.gfx.ImageTile;

/**
 * @author Pedro
 *
 */
public class GameManager extends AbstractGame {
	
	private Image image;
	private ImageTile image2;
	private SoundClip clip;

	

	public GameManager() {
		
		//image = new Image("/alphatest.png");
		//image2 = new ImageTile("/alphatest2.png",16,16);
		//image2.setAlpha(true);
		
		image = new Image("/testLight.png");
		image.setAlpha(true);
		image2= new ImageTile("/testLight2.png",16,16);
		image2.setAlpha(false);
		
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
		
		for (int x = 0; x<image.getWidth(); x++) {
			for(int y = 0; y<image.getHeight();y++) {
				r.setLightMap(x,  y, image.getPixel()[x+y*image.getWidth()]);
			}
		}
		
		r.setzDepth(1);
		r.drawImage(image2, gc.getInput().getMouseX(), gc.getInput().getMouseY());
		
		//r.setzDepth(0);
		//r.drawImage(image, 10, 10);
		//r.drawImageTile(image, gc.getInput().getMouseX()-8, gc.getInput().getMouseY()-8, (int)temp , 0);
		//r.drawFillRect(gc.getInput().getMouseX()-4, gc.getI	nput().getMouseY()-4, 8, 8, 0xffffccff);

	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		/*
		 * gc.setWidth(320);
		 * gc.setHeight(240);
		 * gc.setScale(1f); 
		 */
		gc.start();
	}
}
