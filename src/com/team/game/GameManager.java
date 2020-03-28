/**
 * 
 */
package com.team.game;

import com.team.engine.AbstractGame;
import com.team.engine.GameContainer;
import com.team.engine.Renderer;
import com.team.engine.gfx.Image;

/**
 * @author Pedro
 *
 */
public class GameManager extends AbstractGame {
	
	private Image image;
	

	public GameManager() {
		
		image = new Image ("/test.png");
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		//aca iria el codigo del juego
		
		

	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawImage(image, gc.getInput().getMouseX()-40, gc.getInput().getMouseY()-40);

	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}
}
